package app.entity;

import org.joda.time.DateTime;

public class AmazonItem {

	/** amazon_item */
	public Integer id;
	public String asin;
	public String title;
	public String browse_node;
	public Integer sales_rank;
	public Integer price;
	public Integer lowest_new_price;
	public Integer lowest_used_price;
	public String yahoo_auction_url;
	public Integer yahoo_auction_hit_count;
	public Integer total_used;
	public String memo;

	public String image_url;
	public Integer is_preorder = 0;
	public Integer is_deleted = 0;
	public Integer is_bid = 0;
	public Integer is_success_bid = 0;

	public String yahoo_auction_image_url;
	
	public DateTime last_crawled;
	public DateTime created;
	public DateTime updated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrowse_node() {
		return browse_node;
	}

	public void setBrowse_node(String browse_node) {
		this.browse_node = browse_node;
	}

	public Integer getSales_rank() {
		return sales_rank;
	}

	public void setSales_rank(Integer sales_rank) {
		this.sales_rank = sales_rank;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getLowest_new_price() {
		return lowest_new_price;
	}

	public void setLowest_new_price(Integer lowest_new_price) {
		this.lowest_new_price = lowest_new_price;
	}

	public Integer getLowest_used_price() {
		return lowest_used_price;
	}

	public void setLowest_used_price(Integer lowest_used_price) {
		this.lowest_used_price = lowest_used_price;
	}

	public String getYahoo_auction_url() {
		return yahoo_auction_url;
	}

	public void setYahoo_auction_url(String yahoo_auction_url) {
		this.yahoo_auction_url = yahoo_auction_url;
	}

	public Integer getYahoo_auction_hit_count() {
		return yahoo_auction_hit_count;
	}

	public void setYahoo_auction_hit_count(Integer yahoo_auction_hit_count) {
		this.yahoo_auction_hit_count = yahoo_auction_hit_count;
	}

	public Integer getTotal_used() {
		return total_used;
	}

	public void setTotal_used(Integer total_used) {
		this.total_used = total_used;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Integer getIs_preorder() {
		return is_preorder;
	}

	public void setIs_preorder(Integer is_preorder) {
		this.is_preorder = is_preorder;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Integer getIs_bid() {
		return is_bid;
	}

	public void setIs_bid(Integer is_bid) {
		this.is_bid = is_bid;
	}

	public Integer getIs_success_bid() {
		return is_success_bid;
	}

	public void setIs_success_bid(Integer is_success_bid) {
		this.is_success_bid = is_success_bid;
	}

	public DateTime getLast_crawled() {
		return last_crawled;
	}

	public void setLast_crawled(DateTime last_crawled) {
		this.last_crawled = last_crawled;
	}

	public String getYahoo_auction_image_url() {
		return yahoo_auction_image_url;
	}

	public void setYahoo_auction_image_url(String yahoo_auction_image_url) {
		this.yahoo_auction_image_url = yahoo_auction_image_url;
	}
}
