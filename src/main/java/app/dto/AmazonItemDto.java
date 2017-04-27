package app.dto;

import app.entity.AmazonItem;

public class AmazonItemDto extends AmazonItem {

	/** bid */
	public Integer sales_price;
	public Integer shipping_costs;
	public String bid_memo;

	/** 導出項目 */
	public Boolean ng;
	public Integer new_price2;

	public Integer yahoo_auction_contract_price;
	public Integer profit;
	public Integer roi;
	public Integer purchase_price;

	public Integer getSales_price() {
		return sales_price;
	}

	public void setSales_price(Integer sales_price) {
		this.sales_price = sales_price;
	}

	public Integer getShipping_costs() {
		return shipping_costs;
	}

	public void setShipping_costs(Integer shipping_costs) {
		this.shipping_costs = shipping_costs;
	}

	public String getBid_memo() {
		return bid_memo;
	}

	public void setBid_memo(String bid_memo) {
		this.bid_memo = bid_memo;
	}

	public Boolean getNg() {
		return ng;
	}

	public void setNg(Boolean ng) {
		this.ng = ng;
	}

	public Integer getNew_price2() {
		return new_price2;
	}

	public void setNew_price2(Integer new_price2) {
		this.new_price2 = new_price2;
	}

	public Integer getYahoo_auction_contract_price() {
		return yahoo_auction_contract_price;
	}

	public void setYahoo_auction_contract_price(Integer yahoo_auction_contract_price) {
		this.yahoo_auction_contract_price = yahoo_auction_contract_price;
	}

	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public Integer getRoi() {
		return roi;
	}

	public void setRoi(Integer roi) {
		this.roi = roi;
	}

	public Integer getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(Integer purchase_price) {
		this.purchase_price = purchase_price;
	}

	

	public boolean isNg() {
		if (yahoo_auction_hit_count != null && yahoo_auction_hit_count == 0) {
			return true;
		}
		if (newPrice2() < 5000) {
			return true;
		}

		if (title.contains("レンタル落ち") || title.contains("VHS") || title.contains("コンパクトセレクション")) {
			return true;
		}
		if (total_used > 20) {
			return true;
		}
		if ((lowest_used_price != null && newPrice2() != null) && newPrice2() < lowest_used_price * 2) {
			return true;
		}
		if (sales_price != null && sales_price < 0) {
			return true;
		}
		if (sales_rank != null && sales_rank > 50000) {
			return true;
		}		
		if (is_preorder != null && is_preorder == 1) {
			return true;
		}
		if (is_deleted != null && is_deleted == 1) {
			return true;
		}
		return false;
	}

	public Integer newPrice2() {
		if (price == null && lowest_new_price == null) {
			return -1;
		}
		if (price == null) {
			return lowest_new_price;
		}
		if (lowest_new_price == null) {
			return price;
		}

		return price > lowest_new_price ? lowest_new_price : price;
	}

}
