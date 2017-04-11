package app.entity;

import org.joda.time.DateTime;

public class Offer implements Comparable<Offer>{

	public Integer id;
	public String asin;
	public String cond;
	public Integer price;
	public Integer shipping_costs;
	public Integer is_fba;
	public Integer no;
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
	public String getCond() {
		return cond;
	}
	public void setCond(String cond) {
		this.cond = cond;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getShipping_costs() {
		return shipping_costs;
	}
	public void setShipping_costs(Integer shipping_costs) {
		this.shipping_costs = shipping_costs;
	}
	public Integer getIs_fba() {
		return is_fba;
	}
	public void setIs_fba(Integer is_fba) {
		this.is_fba = is_fba;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public DateTime getLast_crawled() {
		return last_crawled;
	}
	public void setLast_crawled(DateTime last_crawled) {
		this.last_crawled = last_crawled;
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


	@Override
	public int compareTo(Offer o) {
		return price.compareTo(o.price);
	}

}
