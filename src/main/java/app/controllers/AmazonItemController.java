package app.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.mysql.jdbc.StringUtils;

import app.dto.AmazonItemDto;
import app.dto.YahooAuctionItem;
import app.entity.Bid;
import app.entity.Offer;
import app.services.AmazonCrawlerService;
import app.services.AmazonItemService;
import app.services.BidService;
import app.services.OfferService;
import app.services.YahooAuctionService;
import app.system.AbstractBaseAppController;

public class AmazonItemController extends AbstractBaseAppController {

	AmazonItemService amazonItemService = new AmazonItemService();
	BidService bidService = new BidService();
	YahooAuctionService yahooAuctionService = new YahooAuctionService();
	OfferService offerService = new OfferService();
	AmazonCrawlerService amazonCrawlerService = new AmazonCrawlerService();
	
	public void index() {
		redirect(AmazonItemController.class, "list");
	}

	public void bids() {

	}

	public void upsert() throws ParseException, IOException {
		String asin = $("asin");
		if (asin != null || !"".equals(asin)) {
			Integer sales_price = to_i($("sales_price").trim().replace(",", ""));
			if (sales_price == null) {
				List<Offer> offers = amazonCrawlerService.offerListing(asin);
				offerService.batchDeleteAndInsert(asin, offers);
				sales_price = offerService.calcSalesPrice(asin);
			}
			Integer shipping_costs = to_i($("shipping_costs").trim().replace(",", ""));

			AmazonItemDto amazonItem = amazonItemService.findAmazonItemDto(asin);
			Integer yahoo_auction_contract_price = Bid.calcYahooAuctionContractPrice(amazonItem.sales_price, amazonItem.shipping_costs);
			List<YahooAuctionItem> yahooItems = yahooAuctionService.requstYahooAuction(amazonItem.title, amazonItem.newPrice2(), yahoo_auction_contract_price);
			amazonItemService.updateYahooAuctionHitCount(amazonItem.asin, yahooItems.size());
			
			bidService.upsert(asin, sales_price, shipping_costs, $("bid_memo"));
		}
		redirect(AmazonItemController.class, "list#" + $("asin"));
	}

	public void list() {
		Boolean ignore_ng_filter = StringUtils.isNullOrEmpty($("ignore_ng_filter")) ? false : Boolean.valueOf($("ignore_ng_filter"));
		view("ignore_ng_filter", ignore_ng_filter);
		
		List<AmazonItemDto> items = amazonItemService.findAll();
		view("items", items);
	}

	public void yahooAuctionHits() throws UnsupportedEncodingException, ParseException {
		view("keyword", $("keyword"));
		view("new_price", $("new_price"));

		List<YahooAuctionItem> items = yahooAuctionService.requstYahooAuction($("keyword"), $i("new_price"), $i("yahoo_auction_contract_price"));
		view("items", items);
	}

}
