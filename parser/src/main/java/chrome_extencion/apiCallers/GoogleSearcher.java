package chrome_extencion.apiCallers;

import chrome_extencion.models.SearchResult;
import chrome_extencion.util.QryTools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static chrome_extencion.util.QryTools.convertInputToJSON;


public class GoogleSearcher implements Searcher {

    public SearchResult getSource(String qry) throws IOException, JSONException {

        String key="AIzaSyAh4kZtXYIC0DBL1KKlYaYcZ6vIfqutJSI";

        qry = QryTools.removeSymbols(qry);
        qry = QryTools.replaceSpaces(qry);

        URL url = new URL("https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json&sort=date:a:s");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        String output = convertInputToJSON(conn);

        JSONObject jsonObject = new JSONObject(output);
        JSONArray arr = jsonObject.getJSONArray("items");

        String sourceLink = arr.getJSONObject(0).getString("link");
        String sourceTitle = arr.getJSONObject(0).getString("title");

        return new SearchResult(sourceTitle, sourceLink);
    }

    @Override
    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException {

        ArrayList<SearchResult> searchResultArrayList = new ArrayList<>();
        String key="AIzaSyAh4kZtXYIC0DBL1KKlYaYcZ6vIfqutJSI";

        qry = QryTools.removeSymbols(qry);
        qry = QryTools.replaceSpaces(qry);

        URL url = new URL("https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json&sort=date:a:s");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        String output = convertInputToJSON(conn);

        JSONObject jsonObject = new JSONObject(output);

        JSONArray arr = jsonObject.getJSONArray("items");

        for (int i = 0; i < 10; i++) {
            String sourceLink = arr.getJSONObject(i).getString("link");
            String sourceTitle = arr.getJSONObject(i).getString("title");
            sourceTitle = sourceTitle.substring(0, Math.min(sourceTitle.length(), 100)) + "...";
            searchResultArrayList.add(new SearchResult(sourceTitle, sourceLink));
        }

        return searchResultArrayList;
    }



}
