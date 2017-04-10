package app.services;

import java.util.List;

import org.joda.time.DateTime;
import org.sql2o.Connection;

import app.dto.AmazonItemDto;
import app.entity.AmazonItem;
import app.entity.Bid;
import app.system.AbstractBaseService;

public class AmazonItemService extends AbstractBaseService {

	public AmazonItemDto findAmazonItemDto(String asin) {
		String sql = "";
		sql += "select amazon_item.*,  sales_price, shipping_costs, bid.memo bid_memo ";
		sql += "from amazon_item left outer join bid on amazon_item.asin = bid.asin ";
		sql += "where amazon_item.asin = :asin ";

		try (Connection con = sql2o.open()) {
			AmazonItemDto dto = con.createQuery(sql).addParameter("asin", asin)
					.executeAndFetchFirst(AmazonItemDto.class);
			return dto;
		}
	}

	public AmazonItem findAmazonOne(String asin) {
		String sql = "";
		sql += "select * ";
		sql += "from amazon_item ";
		sql += "where asin = :asin ";

		try (Connection con = sql2o.open()) {
			AmazonItem entity = con.createQuery(sql).addParameter("asin", asin).executeAndFetchFirst(AmazonItem.class);
			return entity;
		}
	}

	public void upsert(AmazonItem item) {
		AmazonItem entity = findAmazonOne(item.asin);
		if (entity == null) {
			insert(item);
		} else {
			update(item);
		}
	}

	public Integer insert(AmazonItem item) {
		String sql = "";
		sql += "INSERT INTO amazon_item (asin, title, browse_node, sales_rank, price, lowest_new_price, lowest_used_price, yahoo_auction_url, yahoo_auction_hit_count, total_used, memo, created, image_url, is_preorder, is_deleted, is_bid, is_success_bid) ";
		sql += "VALUES (:asin, :title, :browse_node, :sales_rank, :price, :lowest_new_price, :lowest_used_price, :yahoo_auction_url, :yahoo_auction_hit_count, :total_used, :memo, :created, :image_url, :is_preorder, :is_deleted, :is_bid, :is_success_bid) ";

		try (Connection con = sql2o.open()) {
			return con.createQuery(sql)
					.addParameter("asin", item.asin)
					.addParameter("title", item.title)
					.addParameter("browse_node", item.browse_node)
					.addParameter("sales_rank", item.sales_rank)
					.addParameter("price", item.price)
					.addParameter("lowest_new_price", item.lowest_new_price)
					.addParameter("lowest_used_price", item.lowest_used_price)
					.addParameter("yahoo_auction_url", item.yahoo_auction_url)
					.addParameter("yahoo_auction_hit_count", item.yahoo_auction_hit_count)
					.addParameter("total_used", item.total_used)
					.addParameter("memo", item.memo)
					.addParameter("created", new DateTime())
					.addParameter("image_url", item.image_url)
					.addParameter("is_preorder", item.is_preorder)
					.addParameter("is_deleted", item.is_deleted)
					.addParameter("is_bid", item.is_bid)
					.addParameter("is_success_bid", item.is_success_bid)
					.executeUpdate()
					.getKey(Integer.class);
		}
	}

	public void update(AmazonItem item) {
		String sql = "";
		sql += "update amazon_item ";
		sql += "set title = :title, browse_node = :browse_node, sales_rank = :sales_rank, price = :price, lowest_new_price = :lowest_new_price, lowest_used_price = :lowest_used_price, yahoo_auction_url = :yahoo_auction_url, yahoo_auction_hit_count = :yahoo_auction_hit_count, total_used = :total_used, memo = :memo, updated = :updated, image_url = :image_url, is_preorder = :is_preorder, is_deleted = :is_deleted, is_bid = :is_bid, is_success_bid = :is_success_bid ";
		sql += "where asin = :asin";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql)
					.addParameter("title", item.title)
					.addParameter("browse_node", item.browse_node)
					.addParameter("sales_rank", item.sales_rank)
					.addParameter("price", item.price)
					.addParameter("lowest_new_price", item.lowest_new_price)
					.addParameter("lowest_used_price", item.lowest_used_price)
					.addParameter("yahoo_auction_url", item.yahoo_auction_url)
					.addParameter("yahoo_auction_hit_count", item.yahoo_auction_hit_count)
					.addParameter("total_used", item.total_used)
					.addParameter("memo", item.memo)
					.addParameter("updated", new DateTime())
					.addParameter("image_url", item.image_url)
					.addParameter("is_preorder", item.is_preorder)
					.addParameter("is_deleted", item.is_deleted)
					.addParameter("is_bid", item.is_bid)
					.addParameter("is_success_bid", item.is_success_bid)					
					.addParameter("asin", item.asin)
					.executeUpdate();
		}
	}

	public List<AmazonItemDto> findAll() {
		String sql = "";
		sql += "select amazon_item.*,  sales_price, shipping_costs, bid.memo bid_memo ";
		sql += "from amazon_item left outer join bid on amazon_item.asin = bid.asin ";
		sql += "order by (case when total_used = 0 then 1000000 - sales_rank else price / total_used end) desc ";

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
}
