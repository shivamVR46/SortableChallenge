package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Challenge {

    public static void main(String[] args) {
        Configuration con = new Configuration();
        con.loadConfigFile();
        ProcessIO pio = new ProcessIO();
        pio.loadInputFile();
        outputGenerator(setResults(con.getSiteInfo(), con.getBidders(), pio.getAuctionInfo()));

    }

    public static JSONArray setResults(HashMap<String, Data> s_Data, HashMap<String, Float> Bidders, List<Auction> auction) {
        HashMap<String, BidInfo> RESULTS = new HashMap<String, BidInfo>();
        JSONArray bResult = null;
        for (int i = 0; i < auction.size(); i++) {
            Auction site = auction.get(i);
            if (s_Data.containsKey(site.getSITE())) {
                List<BidInfo> s_bids = site.getBIDS();
                Data siteInfo = s_Data.get(site.getSITE());
                List<String> units = site.getUNITS();
                Long floor = siteInfo.getFloor();

                for (int j = 0; j < s_bids.size(); j++) {
                    BidInfo c_bid = s_bids.get(j);
                    Boolean validBidder = siteInfo.getBidders().contains(c_bid.getBidder());
                    Boolean validUnit = units.contains(c_bid.getUnit());
                    Boolean validBid = false;
                    if ((c_bid.getBid() + c_bid.getBid() * Bidders.get(c_bid.getBidder())) >= floor) {
                        validBid = true;
                    }

                    if (validBidder && validUnit && validBid) {
                        if (RESULTS.isEmpty() || !RESULTS.containsKey(c_bid.getUnit())) {
                            RESULTS.put(c_bid.getUnit(), c_bid);
                        } else if (getHighestBidder(Bidders, RESULTS.get(c_bid.getUnit()), c_bid)) {
                            RESULTS.put(c_bid.getUnit(), c_bid);
                        }
                    }
                }

                JSONArray siteResults = new JSONArray();
                RESULTS.forEach((key, BidInfo) -> {
                    siteResults.add(getBidInfo(BidInfo));
                });
                System.out.println(siteResults);
                bResult = siteResults;
            }
        }
        return bResult;
    }

    public static JSONObject getBidInfo(BidInfo obj) {
        JSONObject json = new JSONObject();
        json.put("bidder", obj.getBidder());
        json.put("bid", obj.getBid());
        json.put("unit", obj.getUnit());
        return json;
    }

    public static boolean getHighestBidder(HashMap<String, Float> bidders, BidInfo bid1, BidInfo bid2) {
        if (!bid1.getBidder().equals(bid2.getBidder())) {
            float finalBid1 = bid1.getBid() + bid1.getBid() * bidders.get(bid1.getBidder());
            float finalBid2 = bid2.getBid() + bid2.getBid() * bidders.get(bid2.getBidder());
            return finalBid1 < finalBid2;
        }
        return false;

    }

    public static void outputGenerator(JSONArray bidResults) {
        try {
//            String dir = System.getProperty("user.dir");
            Writer fileWriter = new FileWriter("output.json");
//            System.out.println(dir);
            fileWriter.write(bidResults.toJSONString());
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
