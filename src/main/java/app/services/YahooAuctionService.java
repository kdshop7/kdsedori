package app.services;

import java.io.IOException;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import app.dto.YahooAuctionItem;
import app.system.AbstractBaseService;

public class YahooAuctionService extends AbstractBaseService{

	/**
	 * @see https://www.mlab.im.dendai.ac.jp/~yamada/ir/WebService/YahooAuctionsSearch/
	 */
	public List<YahooAuctionItem> requstYahooAuction(String keyword, Integer newPrice,
			Integer yahooAuctionContractPrice) throws UnsupportedEncodingException, ParseException {
		List<YahooAuctionItem> items = new ArrayList<YahooAuctionItem>();
		String BASE_URL = "http://auctions.yahooapis.jp/AuctionWebService/V2/search";

		// リクエストパラメータ
		String AUC_ID = YAHOO_AUC_ID;
		
		List<String> sentences = extractKeyphrase(keyword);
		String text = "";
		for (String sentence : sentences) {
			text += sentence + " ";
		}
		String aucQuery = URLEncoder.encode(text.trim(), "UTF-8");

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

	public List<String> extractKeyphrase(String text) {
		List<String> keyphrases = new ArrayList<>();

		try {
			// String sentence = URLEncoder.encode(text, "UTF-8");

			String url = "https://jlp.yahooapis.jp/KeyphraseService/V1/extract?appid=" + YAHOO_AUC_ID + "&sentence="
					+ text;
			Document doc;

			doc = Jsoup.connect(url).get();
			Elements rows = doc.select("Result");
			for (Element row : rows) {
				keyphrases.add(row.select("Keyphrase").text());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return keyphrases;
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
