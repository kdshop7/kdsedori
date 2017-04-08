/*
Copyright 2009-2010 Igor Polevoy 

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/

package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;


@Table(value = "bid")
public class Bid extends Model {
	// sales_price : 販売金額(amazon)
	// shipping_costs : 送料

	static {
		validatePresenceOf("asin");
	}

	/**
	 * ヤフオク落札価格を計算する
	 */
	public Integer calcYahooAuctionContractPrice() {
		return (int) (getInteger("sales_price") * 0.62) - 400 - calcShippingCosts();
	}

	/**
	 * 利益を計算する
	 */
	public Integer calcProfit() {
		return (int) (getInteger("sales_price") * 0.23);
	}

	/**
	 * ROI
	 */
	public Integer calcRoi() {
		return Integer.valueOf("" + Math.round(100D * (double)calcProfit() / (double)calcPurchasePrice()));
	}

	/**
	 * 仕入額合計
	 */
	public Integer calcPurchasePrice() {
		return calcYahooAuctionContractPrice() + calcShippingCosts();
	}

	/**
	 * 送料
	 */
	public Integer calcShippingCosts() {
		return getInteger("shipping_costs") == null ? 0 : getInteger("shipping_costs");
	}
}
