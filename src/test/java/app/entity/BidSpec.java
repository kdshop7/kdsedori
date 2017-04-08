package app.entity;

import org.javalite.activeweb.DBSpec;
import org.junit.Before;
import org.junit.Test;

public class BidSpec extends DBSpec {

	Bid bid;
	Bid bid2;

	@Before
	public void before() {
		bid = new Bid();
		bid2 = new Bid();
	}



	@Test
	public void 落札金額() {
		a(bid.calcYahooAuctionContractPrice(null,null)).shouldBeNull();
		a(bid.calcYahooAuctionContractPrice(0,0)).shouldBeNull();
		a(bid.calcYahooAuctionContractPrice(null,10000)).shouldBeNull();
		a(bid.calcYahooAuctionContractPrice(0,10000)).shouldBeNull();
		a(bid.calcYahooAuctionContractPrice(9800,800)).shouldBeEqual(Integer.valueOf(4876));
		a(bid2.calcYahooAuctionContractPrice(9800, 0)).shouldBeEqual(Integer.valueOf(5676));
		a(bid2.calcYahooAuctionContractPrice(9800, null)).shouldBeEqual(Integer.valueOf(5676));
	}

	@Test
	public void 利益() {
		a(bid.calcProfit(9800)).shouldBeEqual(Integer.valueOf(2254));
		a(bid2.calcProfit(9800)).shouldBeEqual(Integer.valueOf(2254));
	}

	@Test
	public void ROI() {
		a(bid.calcRoi(9800, 800)).shouldBeEqual(40D);
		a(bid2.calcRoi(9800, 0)).shouldBeEqual(40D);
		a(bid2.calcRoi(9800, null)).shouldBeEqual(40D);
		a(bid.calcRoi(100, 0)).shouldBeNull();
	}

	@Test
	public void 仕入額() {
		a(bid.calcPurchasePrice(9800, 800)).shouldBeEqual(Integer.valueOf(5676));
		a(bid2.calcPurchasePrice(9800, 0)).shouldBeEqual(Integer.valueOf(5676));
		a(bid2.calcPurchasePrice(9800, null)).shouldBeEqual(Integer.valueOf(5676));
	}

}
