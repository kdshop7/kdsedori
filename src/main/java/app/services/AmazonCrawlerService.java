package app.services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.net.URLCodec;
import org.joda.time.DateTime;

import am.ik.aws.apa.jaxws.Item;
import am.ik.aws.apa.jaxws.ItemLookupRequest;
import am.ik.aws.apa.jaxws.ItemLookupResponse;
import am.ik.aws.apa.jaxws.ItemSearchRequest;
import am.ik.aws.apa.jaxws.ItemSearchResponse;
import am.ik.aws.apa.jaxws.Items;
import am.ik.aws.apa.jaxws.Offer;
import am.ik.aws.apa.jaxws.OfferListing;
import am.ik.aws.apa.jaxws.Price;
import app.entity.AmazonItem;
import app.system.AbstractBaseService;

public class AmazonCrawlerService extends AbstractBaseService {

	/**
	 *       
	 *       16286831:日本のTVドラマ
	 *       16295821:アメリカのTVドラマ
	 *       16295831:イギリスのTVドラマ
	 *       16295841:韓国のTVドラマ
	 *       16295851:台湾のTVドラマ
	 *       16295861:中国のTVドラマ
	 */
	AmazonItemService amazonItemService = new AmazonItemService();
	final Integer PRICE_RANGE_COUNT = 20;

	URLCodec codec = new URLCodec("UTF-8");
	
	public String execute() {
		long startTime = System.currentTimeMillis();

		List<String> browseNodes = Arrays.asList("16286831", "16295821","16295831", "16295841", "16295851", "16295861");
		int random = (int)(Math.random() * browseNodes.size());
		
		String searchIndex = "DVD";
		String browseNode = browseNodes.get(random);
		Integer minimumPrice = 5000;
		Integer priceRange = 2000;

		int records = 0;
		
		for (int i = 0; i <= PRICE_RANGE_COUNT; i++) {
			for (int itemPage = 1; itemPage <= 10; itemPage++) {
				searchItemAndUpsert(searchIndex, browseNode, itemPage, minimumPrice + (priceRange * i));
				records++;
			}
		}

		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		Double perTime = (double) time / (double) records;
		String msg = "Crawling done. [browseNode : " + browseNode +", " +  records + " records, " + time + " seconds, " + perTime + " seconds / records]";
		System.out.println(msg);
		return msg;
	}

	private void searchItemAndUpsert(String searchIndex, String browseNode, Integer itemPage, Integer minimumPrice) {
		List<AmazonItem> items = searchItem(searchIndex, browseNode, itemPage, minimumPrice);
		for (AmazonItem item : items) {
			amazonItemService.upsert(item);
		}
	}

	/**
	 * Amazon API のItemSearchでXML文字列を取得する。
	 * 
	 * @param searchIndex
	 *            "DVD"を想定
	 * @param browseNode
	 *            Amazonのカテゴリコード ex)"16295841" : 韓国のTVドラマ, "16286831" : 日本のTVドラマ
	 * @param itemPage
	 *            ページ番号 1から10まで
	 * @param minimumPrice
	 *            最小金額(5000円を想定)
	 * @return xml文字列
	 */
	public List<AmazonItem> searchItem(String searchIndex, String browseNode, Integer itemPage, Integer minimumPrice) {
		// AwsApaRequester requester = new AwsApaRequesterImpl();
		ItemSearchRequest request = new ItemSearchRequest();
		request.setSearchIndex(searchIndex);
		request.setBrowseNode(browseNode);
		request.setItemPage(new BigInteger(itemPage.toString()));
		request.setMinimumPrice(new BigInteger(minimumPrice.toString()));
		request.getResponseGroup().add("Medium");
		request.getResponseGroup().add("Offers");
		request.setSort("pricerank");
		ItemSearchResponse response = requester.itemSearch(request);

		List<AmazonItem> amazonItems = new ArrayList<>();
		for (Items items : response.getItems()) {
			for (Item item : items.getItem()) {
				AmazonItem entity = convert(browseNode, item);
				amazonItems.add(entity);
			}
		}
		return amazonItems;
	}

	private AmazonItem convert(String browseNode, Item item) {
		AmazonItem entity = new AmazonItem();
		entity.asin = item.getASIN();
		entity.browse_node = browseNode;
		entity.created = new DateTime();
		Price lowest_new_price = item.getOfferSummary().getLowestNewPrice();
		entity.lowest_new_price = lowest_new_price == null ? null : lowest_new_price.getAmount().intValue();
		Price lowest_used_price = item.getOfferSummary().getLowestUsedPrice();
		entity.lowest_used_price = lowest_used_price == null ? null : lowest_used_price.getAmount().intValue();
		Price price = item.getItemAttributes().getListPrice();
		entity.price = price == null ? null : price.getAmount().intValue();
		entity.sales_rank = item.getSalesRank() == null ? null : Integer.valueOf(item.getSalesRank());
		entity.title = item.getItemAttributes().getTitle();
		entity.total_used = Integer.valueOf(item.getOfferSummary().getTotalUsed());
		try {
			entity.yahoo_auction_url = "http://auctions.search.yahoo.co.jp/search?p=" + codec.encode(entity.title, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (item.getOffers().getOffer().size() > 0 && item.getOffers().getOffer().get(0).getOfferListing().size() > 0) {
			entity.is_preorder =item.getOffers().getOffer().get(0).getOfferListing().get(0).getAvailabilityAttributes().isIsPreorder() == null ? 0 : 1;
		}
		entity.image_url = item.getMediumImage() == null ? null : item.getMediumImage().getURL();
		
		return entity;
	}
	
	public List<AmazonItem> lookupItem() {
		// AwsApaRequester requester = new AwsApaRequesterImpl();
		ItemLookupRequest request = new ItemLookupRequest();
		request.setCondition("Used");
		request.setMerchantId("ALL");
		request.getResponseGroup().add("OfferFull");
		request.getResponseGroup().add("OfferSummary");
		request.getItemId().add("B0057J4Z96");
		ItemLookupResponse response = requester.itemLookup(request);

		List<AmazonItem> amazonItems = new ArrayList<>();
		for (Items items : response.getItems()) {
			for (Item item : items.getItem()) {
				for (Offer offer : item.getOffers().getOffer()){
					System.out.print(offer.getMerchant().getName());
					
				}
			}
		}
		return amazonItems;
	}

}
