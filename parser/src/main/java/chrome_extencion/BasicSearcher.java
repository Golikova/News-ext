package chrome_extencion;

import chrome_extencion.models.SearchResult;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface BasicSearcher {

    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException;
}
