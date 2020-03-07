package chrome_extencion;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class GoogleNewsParser implements Parser {
    ArrayList<String> newsTitles= new ArrayList<>();

    public ArrayList<String> getNewsTitles() {
        return newsTitles;
    }

    @Override
    public ArrayList<String> getArticles(WebPage page) throws ParserConfigurationException, SAXException, IOException {

        Document document = page.getDocument();

        Elements newsSet = document.getElementsByClass("g");
        Elements titlesSet = document.getElementsByClass("l");

        for (Element title:
             titlesSet) {
            String nextTitle = title.getElementsByTag("a").text();
            newsTitles.add(nextTitle);
            System.out.println(nextTitle);
        }


        return null;
    }



    @Override
    public WebPage appendIntoHtml(ArrayList<SearchResult> newsSearchResults, WebPage webPage) throws ParserConfigurationException, SAXException, IOException {

        Document document = webPage.getDocument();
        Elements newsSet = document.getElementsByClass("g");

        int i = 0;
        for (Element article:
             newsSet) {

            article.prepend(  "<p> Источник: </p>" + "<a href=\""+ newsSearchResults.get(i).getLink() +"\">" + newsSearchResults.get(i).getTitle() + " </a>");
            i++;
        }

        webPage.setCode(document.body().html());

        System.out.println(webPage.toString());
        return webPage;
    }
}
