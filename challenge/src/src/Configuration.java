package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Configuration {

    /**
     * S_DATA stores the information of the site with key as site name and value
     * as it's attributes
     */
    private HashMap<String, Data> S_DATA = new HashMap<>();

    /**
     * BIDDERS stores the information of the bidders with key as bidder name and
     * value as adjustment factor
     */
    private HashMap<String, Float> BIDDERS = new HashMap<>();

    /**
     * Load config.json and parse relevant data
     */
    public void loadConfigFile() {
        JSONParser parser = new JSONParser();

        try {
            String dir = System.getProperty("user.dir");
            JSONObject obj = (JSONObject) parser.parse(new FileReader(dir + "//config.json"));
            JSONArray sites = (JSONArray) obj.get("sites");
            JSONArray bidders = (JSONArray) obj.get("bidders");

//          System.out.println(sites + " " + bidders);
            setS_Data(sites);
            setBidder(bidders);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * setS_Data fills the HashMap S_Data with key as name of the site and value
     * as bidders with adjustment factors respectively
     *
     * @param sites JSON Array of sites loaded from config.json file
     */
    private void setS_Data(JSONArray sites) {
//        System.out.println("Set S_Data");
        for (int i = 0; i < sites.size(); i++) {
            JSONObject site = (JSONObject) sites.get(i);
            JSONArray bidders = (JSONArray) site.get("bidders");
            String[] strBidders = Arrays.copyOf(bidders.toArray(), bidders.toArray().length, String[].class);
            Data siteConfig = new Data(site.get("name").toString(), strBidders, (long) site.get("floor"));
            S_DATA.put(siteConfig.getName(), siteConfig);
        }
    }

    /**
     * setBidder fills the HashMap setBidder with key as name of bidder and
     * value as their adjustment factor
     *
     * @param bidders JSON Array of bidders loaded from config.json file
     */
    private void setBidder(JSONArray bidders) {
        for (int i = 0; i < bidders.size(); i++) {
            JSONObject bidder = (JSONObject) bidders.get(i);
            BIDDERS.put(bidder.get("name").toString(), ((Number) bidder.get("adjustment")).floatValue());
        }
    }

    public HashMap<String, Data> getSiteInfo() {
        return S_DATA;
    }

    public HashMap<String, Float> getBidders() {
        return BIDDERS;
    }
}

class Data {

    private String NAME;
    private List<String> BIDDERS = new ArrayList<String>();
    private long FLOOR;

    public Data(String name, String[] bidders, long floor) {
        NAME = name;
        BIDDERS = Arrays.asList(bidders);
        FLOOR = floor;
    }

    public String getName() {
        return NAME;
    }

    public List<String> getBidders() {
        return BIDDERS;
    }

    public long getFloor() {
        return FLOOR;
    }

}
