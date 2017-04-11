package app.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import app.dto.AmazonItemDto;
import app.dto.YahooAuctionItem;
import app.entity.AmazonItem;
import app.entity.Bid;
import app.entity.Offer;
import app.services.AmazonCrawlerService;
import app.services.AmazonItemService;
import app.services.BidService;
import app.services.OfferService;
import app.services.YahooAuctionService;
import app.system.AbstractBaseAppController;

public class CrawlerController extends AbstractBaseAppController {

	AmazonCrawlerService amazonCrawlerService = new AmazonCrawlerService();
	AmazonItemService amazonItemService = new AmazonItemService();
	YahooAuctionService yahooAuctionService = new YahooAuctionService();
	OfferService offerService = new OfferService();
	BidService bidService = new BidService();

	public void run() {
		String msg = amazonCrawlerService.execute();
		respond(msg).contentType("text/plain").status(200);
	}

	public void updateYahooHits() throws UnsupportedEncodingException, ParseException {
		long startTime = System.currentTimeMillis();
		List<AmazonItemDto> items = amazonItemService.findAll();

		int records = 0;
		for (AmazonItemDto item : items) {
			Integer yahoo_auction_contract_price = Bid.calcYahooAuctionContractPrice(item.sales_price,
					item.shipping_costs);
			List<YahooAuctionItem> yahooItems = yahooAuctionService.requstYahooAuction(item.title, item.newPrice2(),
					yahoo_auction_contract_price);
			amazonItemService.updateYahooAuctionHitCount(item.asin, yahooItems.size());
			records++;
		}

		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		Double perTime = (double) time / (double) records;
		String msg = "Yahoo hits updating done. [ " + records + " records, " + time + " seconds, " + perTime
				+ " seconds / records]";
		System.out.println(msg);

		respond(msg).contentType("text/plain").status(200);
	}

	public void offerListing() {
		Integer limit = 100;
		List<AmazonItemDto> items = amazonItemService.findByLastCrawled(limit);

		for (AmazonItemDto item : items) {
			try {
				List<Offer> offers = amazonCrawlerService.offerListing(item.asin);
				String asin = null;
				if (offers.size() > 0) {
					asin = offers.get(0).asin;
					offerService.delete(asin);
				}
				for (Offer offer : offers) {
					offer.last_crawled = new DateTime();
					offerService.insert(offer);
				}
				Integer sales_price = offerService.calcSalesPrice(asin);
				bidService.upsert(asin, sales_price, null, null);
				
				item.last_crawled = new DateTime();
				item.updated = new DateTime();
				amazonItemService.update((AmazonItem)item);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String msg = "Done.";
		respond(msg).contentType("text/plain").status(200);
	}

}
