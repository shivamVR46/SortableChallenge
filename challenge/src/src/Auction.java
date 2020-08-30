package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Auction {

    private String SITE;
    private List<String> UNITS = new ArrayList<String>();
    private List<BidInfo> BIDINFO = new ArrayList<BidInfo>();

    Auction(String site, String[] units, JSONArray bids) {
        SITE = site;
        UNITS = Arrays.asList(units);
        setupBid(bids);
    }

    private void setupBid(JSONArray bids) {
        for (int i = 0; i < bids.size(); i++) {
            JSONObject bidInfo = (JSONObject) bids.get(i);
            BIDINFO.add(new BidInfo(bidInfo.get("bidder").toString(), bidInfo.get("unit").toString(), (Long) bidInfo.get("bid")));
        }
    }

    public String getSITE() {
        return SITE;
    }

    public List<String> getUNITS() {
        return UNITS;
    }

    public List<BidInfo> getBIDS() {
        return BIDINFO;
    }
}
