package chrome_extencion;

import org.json.JSONException;
import twitter4j.TwitterException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public interface Searcher {

    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException, TwitterException;

}
