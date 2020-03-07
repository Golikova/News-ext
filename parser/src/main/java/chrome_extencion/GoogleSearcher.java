package chrome_extencion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GoogleSearcher implements Searcher{

    public SearchResult getSource(String qry) throws IOException, JSONException {

        String key="AIzaSyAh4kZtXYIC0DBL1KKlYaYcZ6vIfqutJSI";

        qry = removeSymbols(qry);
        qry = removeSpaces(qry);

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
        qry = removeSymbols(qry);
        qry = removeSpaces(qry);

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
            searchResultArrayList.add(new SearchResult(sourceTitle, sourceLink));
        }

        return searchResultArrayList;
    }

    private String convertInputToJSON(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        StringBuilder sb = new StringBuilder();

        String line = null;

        while ((line = br.readLine()) != null) {
             sb.append(line + "\n");
        }

        return sb.toString();
    }

    private String removeSymbols(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (Character.isAlphabetic(c) || Character.isDigit(c) || Character.isSpaceChar(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    private String removeSpaces(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isSpaceChar(c)) {
                result.append('+');
            }
            else result.append(c);
        }
        return result.toString();
    }

}
