/*
Copyright 2009-2010 Igor Polevoy 

Licensed under the Apache License, Version 2.0 (the "License; 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/

package app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.sql2o.Connection;

import app.entity.Bid;
import app.entity.Offer;
import app.system.AbstractBaseService;

public class OfferService extends AbstractBaseService {

	public Integer insert(Offer offer) {
		String sql = "";
		sql += "INSERT INTO offer (asin, cond, is_fba, no, price, shipping_costs, last_crawled, created) ";
		sql += "VALUES (:asin, :cond, :is_fba, :no, :price, :shipping_costs, :last_crawled, :created)";

		try (Connection con = sql2o.open()) {
			return con.createQuery(sql).addParameter("asin", offer.asin).addParameter("cond", offer.cond)
					.addParameter("is_fba", offer.is_fba).addParameter("no", offer.no)
					.addParameter("price", offer.price).addParameter("shipping_costs", offer.shipping_costs)
					.addParameter("last_crawled", offer.last_crawled).addParameter("created", new DateTime())
					.executeUpdate().getKey(Integer.class);
		}
	}

	public void delete(String asin) {
		String sql = "delete from offer where asin = :asin";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("asin", asin).executeUpdate();
		}
	}

	public Bid findOne(String asin) {
		String sql = "select * from bid where asin = :asin ";
		try (Connection con = sql2o.open()) {
			Bid bid = con.createQuery(sql).addParameter("asin", asin).executeAndFetchFirst(Bid.class);
			return bid;
		}
	}

	public Integer calcSalesPrice(String asin) {
		String sql = "";
		sql += "select 1 id, min(price) sales_price from offer where asin = :asin and cond = 'usedVeryGood' group by cond ";
		sql += "union ";
		sql += "select 2 id, max(price) sales_price from offer where asin = :asin and cond = 'usedGood' group by cond ";
		sql += "union  ";
		sql += "select 3 id, max(price) sales_price from offer where asin = :asin and cond = 'usedAcceptable' group by cond ";
		sql += "order by id ";

		try (Connection con = sql2o.open()) {
			Bid bid = con.createQuery(sql).addParameter("asin", asin).addParameter("asin", asin)
					.addParameter("asin", asin).executeAndFetchFirst(Bid.class);
			return bid == null ? null : bid.sales_price;
		}
	}

	public Integer calcSalesPrice(List<Offer> offers) {
		List<Offer> usedLikeNews = new ArrayList<>();
		List<Offer> usedVeryGoods = new ArrayList<>();
		List<Offer> usedGoods = new ArrayList<>();
		List<Offer> usedAcceptables = new ArrayList<>();

		for (Offer offer : offers) {
			switch (offer.cond) {
			case "usedLikeNew":
				usedLikeNews.add(offer);
				break;
			case "usedVeryGood":
				usedVeryGoods.add(offer);
				break;
			case "usedGood":
				usedGoods.add(offer);
				break;
			case "usedAcceptable":
				usedAcceptables.add(offer);
				break;
			}
		}

		Offer usedVeryGoodOffer = Collections.min(usedVeryGoods);
		Offer usedGoodOffer = Collections.max(usedGoods);
		Offer usedAcceptableOffer = Collections.max(usedAcceptables);

		if (usedVeryGoodOffer != null) {
			return usedVeryGoodOffer.price;
		} else if (usedGoodOffer != null) {
			return usedGoodOffer.price;
		} else if (usedAcceptableOffer != null) {
			return usedAcceptableOffer.price;
		}
		return null;
	}

	public String convertCondtionString(String text) {
		if ("新品".equals(text)) {
			return "new";
		} else if ("中古品 - ほぼ新品".equals(text)) {
			return "usedLikeNew";
		} else if ("中古品 - 非常に良い".equals(text)) {
			return "usedVeryGood";
		} else if ("中古品 - 良い".equals(text)) {
			return "usedGood";
		} else if ("中古品 - 可".equals(text)) {
			return "usedAcceptable";
		}
		return null;
	}
}
