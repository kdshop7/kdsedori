package app.entity;

import org.joda.time.DateTime;

public class Bid {

	/** bid */
	public Integer id;
	public String asin;
	public Integer sales_price;
	public Integer shipping_costs;
	public String memo;
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

	/**
	 * ヤフオク落札価格を計算する
	 */
	public static Integer calcYahooAuctionContractPrice(Integer sales_price, Integer shipping_costs) {
		Integer price = 0;
		if (sales_price != null) {
			price = (int) (sales_price * 0.62) - 400;
		}
		if (shipping_costs == null) {
			shipping_costs = 0;
		}

		Integer result = price - calcShippingCosts(shipping_costs);
		return result > 0 ? result : null;
	}

	/**
	 * 利益を計算する
	 */
	public static Integer calcProfit(Integer sales_price) {
		if (sales_price == null) {
			return null;
		}
		return (int) (sales_price * 0.23);
	}

	/**
	 * ROI
	 */
	public static Integer calcRoi(Integer sales_price, Integer shipping_costs) {
		if (sales_price == null) {
			return null;
		}

		if (calcProfit(sales_price) == null) {
			return null;
		}

		if (calcPurchasePrice(sales_price, shipping_costs) == null) {
			return null;
		}
		return Integer.valueOf("" + Math.round(
				100D * (double) calcProfit(sales_price) / (double) calcPurchasePrice(sales_price, shipping_costs)));
	}

	/**
	 * 仕入額合計
	 */
	public static Integer calcPurchasePrice(Integer sales_price, Integer shipping_costs) {
		if (sales_price == null && shipping_costs == null) {
			return 0;
		}
		if (sales_price != null && shipping_costs == null) {
			shipping_costs = 0;
		}
		if (sales_price == null && shipping_costs != null) {
			sales_price = 0;
		}

		Integer yahooAuctionContractPrice = calcYahooAuctionContractPrice(sales_price, shipping_costs);
		yahooAuctionContractPrice = (yahooAuctionContractPrice == null) ? 0 : yahooAuctionContractPrice;
		Integer shippingCosts = calcShippingCosts(shipping_costs);
		shippingCosts = (shippingCosts == null) ? 0 : shippingCosts;
		Integer result = yahooAuctionContractPrice + shippingCosts;
		return (result == 0) ? null : result;
	}

	/**
	 * 送料
	 */
	public static Integer calcShippingCosts(Integer shipping_costs) {
		if (shipping_costs == null) {
			return null;
		}
		return shipping_costs == null ? 0 : shipping_costs;
	}
}
