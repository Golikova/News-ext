package chrome_extencion.parsers;

import chrome_extencion.models.SearchResult;
import chrome_extencion.models.WebPage;
import chrome_extencion.util.QryTools;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class TwitterParser implements Parser {


    WebPage webPage = new WebPage();
    ArrayList<SearchResult>  searchResultArrayList = new ArrayList();

    @Override
    public WebPage getPage(String qry) throws IOException {

        qry = QryTools.removeSymbols(qry);
        qry = QryTools.removeSpaces(qry);

        String url = "https://twitter.com/search?src=typed_query&f=live&q=" + qry;
        String html = Jsoup.connect(url).get().html();
        webPage.setCode(html);

        return  webPage;
    }


    @Override
    public ArrayList<SearchResult> getResults(String qry) throws IOException, JSONException {

        getPage(qry);
        Document document = null;
        try {
            document = webPage.getDocument();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Elements titles = document.getElementsByClass("css-1dbjc4n");
        Elements links = document.getElementsByClass("css6rbku5");

        int index = 0;
        for (Element title:
                titles) {
            String nextTitle = title.text();
            String nextLink = links.get(index).attr("href");
            searchResultArrayList.add(new SearchResult(nextTitle, nextLink));
        }

        return searchResultArrayList;
    }

}
