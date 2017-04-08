package app.dto;

public class YahooAuctionItem {

	public String auctionId;
	public String title;
	public String auctionItemUrl;

	public Integer currentPrice;
	public Integer bidOrBuy;
	public String sellerId;

	public boolean ng;

	public boolean judge(Integer newPrice, Integer yahooAuctionContractPrice) {
		if ((currentPrice != null && bidOrBuy != null) && (newPrice < currentPrice * 2 && bidOrBuy < currentPrice * 2)) {
			setNg(true);
			return false;
		}
		if ((currentPrice != null && bidOrBuy == null) && (newPrice < currentPrice * 2)) {
			setNg(true);
			return false;
		}	
		if ((currentPrice == null && bidOrBuy != null) && (bidOrBuy < currentPrice * 2)) {
			setNg(true);
			return false;
		}		
		if ((currentPrice != null && yahooAuctionContractPrice != null) && currentPrice >= yahooAuctionContractPrice) {
			setNg(true);
			return false;
		}
		return true;
	}

	public boolean getNg() {
		return ng;
	}

	public void setNg(boolean ng) {
		this.ng = ng;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuctionItemUrl() {
		return auctionItemUrl;
	}

	public void setAuctionItemUrl(String auctionItemUrl) {
		this.auctionItemUrl = auctionItemUrl;
	}

	public Integer getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Integer currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Integer getBidOrBuy() {
		return bidOrBuy;
	}

	public void setBidOrBuy(Integer bidOrBuy) {
		this.bidOrBuy = bidOrBuy;
	}

}
