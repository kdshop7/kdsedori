package app.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.codec.net.URLCodec;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import am.ik.aws.apa.jaxws.Item;
import am.ik.aws.apa.jaxws.ItemLookupRequest;
import am.ik.aws.apa.jaxws.ItemLookupResponse;
import am.ik.aws.apa.jaxws.ItemSearchRequest;
import am.ik.aws.apa.jaxws.ItemSearchResponse;
import am.ik.aws.apa.jaxws.Items;
import am.ik.aws.apa.jaxws.OfferListing;
import am.ik.aws.apa.jaxws.Price;
import app.system.AbstractBaseService;
import app.services.AmazonCrawlerService.*;
import junit.framework.TestCase;

import java.util.List;

import org.javalite.activeweb.DBSpec;
import org.junit.Test;

import app.entity.AmazonItem;
import app.entity.Offer;

public class AmazonCrawlerServiceTest extends DBSpec {

	@Test
	public void test_type() throws Exception {
		a(AmazonCrawlerService.class).shouldNotBeNull();
	}

	@Test
	public void test_instantiation() throws Exception {
		AmazonCrawlerService target = new AmazonCrawlerService();
		a(target).shouldNotBeNull();
	}


	@Test
	public void test_searchItem_A$String$String$Integer$Integer() throws Exception {
		// Arrange
		AmazonCrawlerService service = new AmazonCrawlerService();
		String searchIndex = "DVD";
		String browseNode = "16295841";
		Integer itemPage = 1;
		Integer minimumPrice = 5000;
		
		// Act
		List<AmazonItem> items = service.searchItem(searchIndex, browseNode, itemPage, minimumPrice);
		
		// Assert
		a(items.size()).shouldBeEqual(10);
	}

	@Test
	public void test_execute_A$() throws Exception {
		AmazonCrawlerService target = new AmazonCrawlerService();
		target.execute();
	}


	@Test
	public void test_offerListing_A$() throws Exception {
		AmazonCrawlerService target = new AmazonCrawlerService();
		String asin = "4844335804";
		List<Offer> offers = target.offerListing(asin);

	}





}
