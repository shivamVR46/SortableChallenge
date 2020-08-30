package src;

import java.io.File;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessIO {

    // Load input.json and parse relevant data
    private List<Auction> A_DATA = new ArrayList<Auction>();

    public void loadInputFile() {
        JSONParser parser = new JSONParser();

        try {

            String dir = System.getProperty("user.dir");
            // Get input.json file
            Object input = parser.parse(new FileReader(dir + "//input.json"));
            JSONArray jsInput = (JSONArray) input;

            for (int i = 0; i < jsInput.size(); i++) {
                JSONObject jsonAuctionInput = (JSONObject) jsInput.get(i);

                String site = jsonAuctionInput.get("site").toString();
                JSONArray jsonUnits = (JSONArray) jsonAuctionInput.get("units");
                String[] units = Arrays.copyOf(jsonUnits.toArray(), jsonUnits.toArray().length, String[].class);
                JSONArray bids = (JSONArray) jsonAuctionInput.get("bids");
                A_DATA.add(new Auction(site, units, bids));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Auction> getAuctionInfo() {
        return A_DATA;
    }

}
