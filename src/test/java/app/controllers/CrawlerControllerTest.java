package app.controllers;

import app.controllers.CrawlerController.*;

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
import junit.framework.TestCase;

public class CrawlerControllerTest extends TestCase {

	public void test_type() throws Exception {
		assertNotNull(CrawlerController.class);
	}

	public void test_instantiation() throws Exception {
		CrawlerController target = new CrawlerController();
		assertNotNull(target);
	}

	public void test_offerListing_A$() throws Exception {
		CrawlerController target = new CrawlerController();
		target.offerListing();
	}

}
