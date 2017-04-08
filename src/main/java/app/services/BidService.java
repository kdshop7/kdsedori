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

import org.sql2o.Connection;

import app.entity.Bid;
import app.system.AbstractBaseService;

public class BidService extends AbstractBaseService {

	public void upsert(String asin, Integer sales_price, Integer shipping_costs, String memo) {
		Bid bid = findOne(asin);
		if (bid == null) {
			insert(asin, sales_price, shipping_costs, memo);
		} else {
			update(asin, sales_price, shipping_costs, memo);
		}
	}
	
	public Integer insert(String asin, Integer sales_price, Integer shipping_costs, String memo) {
		String sql = "";
		sql += "INSERT INTO bid(asin, sales_price, shipping_costs, memo) ";
		sql += "VALUES (:asin, :sales_price, :shipping_costs, :memo)";

		try (Connection con = sql2o.open()) {
		    return con.createQuery(sql)
		        .addParameter("asin", asin)
		        .addParameter("sales_price", sales_price)
		        .addParameter("shipping_costs", shipping_costs)
		        .addParameter("memo", memo)
		        .executeUpdate()
		        .getKey(Integer.class);
		}
	}
	public void update(String asin, Integer sales_price, Integer shipping_costs, String memo) {
		String sql = "update bid set sales_price = :sales_price, shipping_costs = :shipping_costs, memo = :memo where asin = :asin";

		try (Connection con = sql2o.open()) {
		    con.createQuery(sql)
			    .addParameter("asin", asin)
			    .addParameter("sales_price", sales_price)
			    .addParameter("shipping_costs", shipping_costs)
			    .addParameter("memo", memo)
			    .executeUpdate();
		}
	}
	
	public Bid findOne(String asin) {
		String sql = "select * from bid where asin = :asin ";
		try (Connection con = sql2o.open()) {
			Bid bid = con.createQuery(sql).addParameter("asin", asin).executeAndFetchFirst(Bid.class);
			return bid;
		}
	}
}
