package chrome_extencion.apiCallers;

import chrome_extencion.BasicSearcher;
import chrome_extencion.models.SearchResult;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface Searcher extends BasicSearcher {

    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException;

}
