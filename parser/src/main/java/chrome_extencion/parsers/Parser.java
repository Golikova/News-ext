package chrome_extencion.parsers;

import chrome_extencion.BasicSearcher;
import chrome_extencion.models.SearchResult;
import chrome_extencion.models.WebPage;
import org.json.JSONException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public interface Parser extends BasicSearcher {

    public WebPage getPage(String qry) throws IOException;
    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException;

}
