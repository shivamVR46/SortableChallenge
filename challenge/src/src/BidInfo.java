package src;

import org.json.simple.JSONObject;

public class BidInfo {

    private String BIDDER;
    private String UNIT;
    private Long BID;

    BidInfo(String bidder, String unit, Long bid) {
        this.BIDDER = bidder;
        this.UNIT = unit;
        this.BID = bid;
    }

    public String getBidder() {
        return BIDDER;
    }

    public String getUnit() {
        return UNIT;
    }

    public Long getBid() {
        return BID;
    }

}
