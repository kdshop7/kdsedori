package app.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javalite.http.Get;
import org.javalite.http.Http;
import org.json.simple.parser.ParseException;
import org.sql2o.Connection;

import app.dto.AmazonItemDto;
import app.dto.YahooAuctionItem;
import app.entity.Bid;
import app.system.AbstractBaseService;

public class AmazonItemService extends AbstractBaseService {

	final static String YAHOO_AUC_ID = System.getenv("YAHOO_AUC_ID");
	
	public AmazonItemDto findOne(String asin) {
		String sql = "";
		sql += "select amazon_item.*,  sales_price, shipping_costs, bid.memo bid_memo ";
		sql += "from amazon_item left outer join bid on amazon_item.asin = bid.asin ";
		sql += "where amazon_item.asin = :asin ";		

		try (Connection con = sql2o.open()) {
			AmazonItemDto dto = con.createQuery(sql).addParameter("asin", asin).executeAndFetchFirst(AmazonItemDto.class);
			return dto;
		}
	}

	public List<AmazonItemDto> findAll() {
		String sql = "";
		sql += "select amazon_item.*,  sales_price, shipping_costs, bid.memo bid_memo ";
		sql += "from amazon_item left outer join bid on amazon_item.asin = bid.asin ";
		sql += "order by amazon_item.price / amazon_item.total_used ";

		try (Connection con = sql2o.open()) {
			List<AmazonItemDto> list = con.createQuery(sql).executeAndFetch(AmazonItemDto.class);
			for (AmazonItemDto dto : list) {
				dto.new_price2 = dto.newPrice2();
				dto.yahoo_auction_contract_price = Bid.calcYahooAuctionContractPrice(dto.sales_price,
						dto.shipping_costs);
				dto.profit = Bid.calcProfit(dto.sales_price);
				dto.roi = Bid.calcRoi(dto.sales_price, dto.shipping_costs);
				dto.purchase_price = Bid.calcPurchasePrice(dto.sales_price, dto.shipping_costs);
				dto.ng = dto.isNg();
			}
			return list;
		}
	}

	public void updateYahooAuctionHitCount(String asin, Integer yahoo_auction_hit_count) {
		String sql = "update amazon_item set yahoo_auction_hit_count = :yahoo_auction_hit_count, updated = now() where asin = :asin";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("asin", asin)
					.addParameter("yahoo_auction_hit_count", yahoo_auction_hit_count).executeUpdate();
		}
	}

	/**
	 * @see https://www.mlab.im.dendai.ac.jp/~yamada/ir/WebService/YahooAuctionsSearch/
	 */
	public List<YahooAuctionItem> requstYahooAuction(String keyword, Integer newPrice,
			Integer yahooAuctionContractPrice) throws UnsupportedEncodingException, ParseException {
		List<YahooAuctionItem> items = new ArrayList<YahooAuctionItem>();
		String BASE_URL = "http://auctions.yahooapis.jp/AuctionWebService/V2/search";

		// リクエストパラメータ
		String AUC_ID = YAHOO_AUC_ID;
		String aucQuery = URLEncoder.encode(keyword, "UTF-8");

		// リクエストURL
		String url = BASE_URL + "?appid=" + AUC_ID + "&query=" + aucQuery + "&store=0";

		Get get = Http.get(url);

		String body = get.text("UTF-8");

		Scanner scanner = new Scanner(body);

		// 各要素の内容抽出のためのパターン (Response用)
		Pattern patternTitle = Pattern.compile("<Title>(.+?)</Title>", Pattern.DOTALL);
		Pattern patternAuctionItemUrl = Pattern.compile("<AuctionItemUrl>(.+?)</AuctionItemUrl>", Pattern.DOTALL);
		Pattern patternPrice = Pattern.compile("<CurrentPrice>(.+?)</CurrentPrice>", Pattern.DOTALL);
		Pattern patternAuctionId = Pattern.compile("<AuctionID>(.+?)</AuctionID>", Pattern.DOTALL);
		Pattern patternBidOrBuy = Pattern.compile("<BidOrBuy>(.+?)</BidOrBuy>", Pattern.DOTALL);
		Pattern patternSeller = Pattern.compile("<Seller>(.+?)</Seller>", Pattern.DOTALL);
		Pattern patternId = Pattern.compile("<Id>(.+?)</Id>", Pattern.DOTALL);

		scanner.useDelimiter("<Item>"); // item要素の開始タグを区切りとする
		int i = 0;
		while (scanner.hasNext()) {
			String content = scanner.next();
			if (i == 0) { // 0個目は最初の<item>よりも前なので不要
				i++;
				continue;
			}

			YahooAuctionItem item = new YahooAuctionItem();
			// タイトルの抽出
			item.title = getValue(patternTitle, content);
			// リンクの抽出
			item.auctionItemUrl = getValue(patternAuctionItemUrl, content);
			// 価格の抽出
			item.currentPrice = getIntegerValue(patternPrice, content);
			item.bidOrBuy = getIntegerValue(patternBidOrBuy, content);
			item.auctionId = getValue(patternAuctionId, content);
			String sellerContent = getValue(patternSeller, content);
			item.sellerId = getValue(patternId, sellerContent);

			// 判定
			// if ((!empty($item->CurrentPrice) && $new_price >=
			// $item->CurrentPrice * 2)
			// || (!empty($item->BidOrBuy) && $new_price >= $item->BidOrBuy *
			// 2)) {
			if (item.judge(newPrice, yahooAuctionContractPrice)) {
				items.add(item);
			}

			i++;
		}
		return items;
	}

	protected String getValue(Pattern pattern, String content) {
		Matcher matcher = pattern.matcher(content);
		return (matcher.find()) ? matcher.group(1) : null;
	}

	protected Integer getIntegerValue(Pattern pattern, String content) {
		Matcher matcher = pattern.matcher(content);
		return (matcher.find()) ? (int) Double.parseDouble(matcher.group(1)) : null;
	}
}
