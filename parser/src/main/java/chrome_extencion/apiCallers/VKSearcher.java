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

public class VKSearcher implements Searcher {
    @Override
    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException {

        ArrayList<SearchResult> searchResultArrayList = new ArrayList<>();
        String key="27ae80b827ae80b827ae80b86c27deb019227ae27ae80b879c9b26d410dec2eb322bd69";

        qry = QryTools.removeSymbols(qry);
        qry = QryTools.replaceSpaces(qry);

        URL url = new URL("https://api.vk.com/method/newsfeed.search?count=10&access_token="+
                key +
                "&q="+ qry + "&v=5.58");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        String output = convertInputToJSON(conn);

        JSONObject jsonObject = new JSONObject(output);

        JSONArray arr = jsonObject.getJSONObject("response").getJSONArray("items");

        int postCount = 0;
        int countObj = Integer.parseInt(jsonObject.getJSONObject("response").getString("count"));

        System.out.println(countObj + " найдено постов ВК");

        if (countObj > 10) {
            postCount = 10;
        }
        else postCount = countObj;

        for (int i = 0; i < postCount; i++) {
            String sourceTitle = arr.getJSONObject(i).getString("text");
            sourceTitle = sourceTitle.substring(0, Math.min(sourceTitle.length(), 60)) + "...";

            String sourceLink = "https://vk.com/wall" +
                    arr.getJSONObject(i).getString("owner_id")+
                    "_"+
                    arr.getJSONObject(i).getString("id");
            searchResultArrayList.add(new SearchResult(sourceTitle, sourceLink));
        }

        return searchResultArrayList;
    }
}
