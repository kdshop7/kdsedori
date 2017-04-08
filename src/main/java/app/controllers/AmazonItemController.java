package app.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.mysql.jdbc.StringUtils;

import app.dto.AmazonItemDto;
import app.dto.YahooAuctionItem;
import app.entity.Bid;
import app.services.AmazonItemService;
import app.services.BidService;
import app.system.AbstractBaseAppController;

public class AmazonItemController extends AbstractBaseAppController {

	AmazonItemService amazonItemService = new AmazonItemService();
	BidService bidService = new BidService();

	public void index() {
		redirect(AmazonItemController.class, "list");
	}

	public void bids() {

	}

	public void updateYahooHits() throws UnsupportedEncodingException, ParseException {
		List<AmazonItemDto> items = amazonItemService.findAll();
		for (AmazonItemDto item : items) {
			Integer yahoo_auction_contract_price = Bid.calcYahooAuctionContractPrice(item.sales_price, item.shipping_costs);
			List<YahooAuctionItem> yahooItems = amazonItemService.requstYahooAuction(item.title, item.newPrice2(), yahoo_auction_contract_price);
			amazonItemService.updateYahooAuctionHitCount(item.asin, yahooItems.size());
		}
		
		respond("OK").contentType("text/plain").status(200);
	}
	
	public void upsert() throws UnsupportedEncodingException, ParseException {
		String asin = $("asin");
		if (asin != null || !"".equals(asin)) {
			Integer sales_price = to_i($("sales_price").trim().replace(",", ""));
			Integer shipping_costs = to_i($("shipping_costs").trim().replace(",", ""));

			AmazonItemDto amazonItem = amazonItemService.findOne(asin);
			Integer yahoo_auction_contract_price = Bid.calcYahooAuctionContractPrice(amazonItem.sales_price, amazonItem.shipping_costs);
			List<YahooAuctionItem> yahooItems = amazonItemService.requstYahooAuction(amazonItem.title, amazonItem.newPrice2(), yahoo_auction_contract_price);
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

		List<YahooAuctionItem> items = amazonItemService.requstYahooAuction($("keyword"), $i("new_price"), $i("yahoo_auction_contract_price"));
		view("items", items);
	}

}
