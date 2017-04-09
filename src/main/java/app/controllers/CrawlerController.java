package app.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.simple.parser.ParseException;

import app.dto.AmazonItemDto;
import app.dto.YahooAuctionItem;
import app.entity.Bid;
import app.services.AmazonCrawlerService;
import app.services.AmazonItemService;
import app.services.YahooAuctionService;
import app.system.AbstractBaseAppController;

public class CrawlerController extends AbstractBaseAppController{
	
	AmazonCrawlerService amazonCrawlerService = new AmazonCrawlerService();
	AmazonItemService amazonItemService = new AmazonItemService();
	YahooAuctionService yahooAuctionService = new YahooAuctionService();
	
	public void run() {
		String msg = amazonCrawlerService.execute();
		 respond(msg).contentType("text/plain").status(200);
	}

	public void updateYahooHits() throws UnsupportedEncodingException, ParseException {
		long startTime = System.currentTimeMillis();
		List<AmazonItemDto> items = amazonItemService.findAll();
		
		int records = 0;
		for (AmazonItemDto item : items) {
			Integer yahoo_auction_contract_price = Bid.calcYahooAuctionContractPrice(item.sales_price, item.shipping_costs);
			List<YahooAuctionItem> yahooItems = yahooAuctionService.requstYahooAuction(item.title, item.newPrice2(), yahoo_auction_contract_price);
			amazonItemService.updateYahooAuctionHitCount(item.asin, yahooItems.size());
			records++;
		}
		
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		Double perTime = (double) time / (double) records;
		String msg = "Yahoo hits updating done. [ " +  records + " records, " + time + " seconds, " + perTime + " seconds / records]";
		System.out.println(msg);		
		
		respond(msg).contentType("text/plain").status(200);
	}
}
