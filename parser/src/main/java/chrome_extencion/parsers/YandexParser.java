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

public class YandexParser implements  Parser {

    WebPage webPage = new WebPage();
    ArrayList<SearchResult>  searchResultArrayList = new ArrayList();

    @Override
    public WebPage getPage(String qry) throws IOException {

        qry = QryTools.removeSymbols(qry);
        qry = QryTools.removeSpaces(qry);

        String url = "https://yandex.ru/search/?text=" + qry;
        String html = Jsoup.connect(url).get().html();
        webPage.setCode(html);

        System.out.println(webPage.getCode());

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

        Elements titles = document.getElementsByClass("organic__url-text");
        Elements links = document.getElementsByClass("organic__url");

        int index = 0;
        for (Element title:
                titles) {
            String nextTitle = title.text();
            nextTitle = nextTitle.substring(0, Math.min(nextTitle.length(), 100)) + "...";
            String nextLink = links.get(index).attr("href");
            searchResultArrayList.add(new SearchResult(nextTitle, nextLink));
        }

        System.out.println(searchResultArrayList);

        return searchResultArrayList;
    }


}
