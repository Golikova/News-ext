package chrome_extencion;

import org.json.JSONException;
import twitter4j.*;

import java.io.IOException;
import java.util.ArrayList;

public class TwitterSearcher implements  Searcher {

    @Override
    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException, TwitterException {
        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("экология россии");
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
        return null;
    }
}
