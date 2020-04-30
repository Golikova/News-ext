package chrome_extencion.apiCallers;

import chrome_extencion.models.SearchResult;
import chrome_extencion.util.QryTools;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static chrome_extencion.util.QryTools.convertInputToJSON;

public class WikiSearcher implements Searcher {
    @Override
    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException {

        qry = QryTools.replaceSpacesPercent(qry);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ru.wikipedia.org/w/api.php?action=query&list=search&srsearch="+ qry +"&format=json")
                .method("GET", null)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        String output = response.body().string();

        JSONObject jsonObject = new JSONObject(output);
        jsonObject = jsonObject.getJSONObject("query");
        JSONArray arr = jsonObject.getJSONArray("search");
        ArrayList<SearchResult> searchResultArrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String sourceLink = "https://ru.wikipedia.org/wiki/" + QryTools.replaceSpacesUnderscore(arr.getJSONObject(i).getString("title"));
            String sourceTitle = arr.getJSONObject(i).getString("title");
            sourceTitle = sourceTitle.substring(0, Math.min(sourceTitle.length(), 100)) + "...";
            searchResultArrayList.add(new SearchResult(sourceTitle, sourceLink));
        }

        return searchResultArrayList;

    }
}
