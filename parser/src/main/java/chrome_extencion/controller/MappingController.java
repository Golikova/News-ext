package chrome_extencion.controller;

import chrome_extencion.SearchResult;
import chrome_extencion.Searcher;
import chrome_extencion.UserData;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MappingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.POST,  consumes= MediaType.APPLICATION_JSON_VALUE)
    public String getPage(@RequestBody UserData userData) throws IOException, JSONException, TwitterException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("conf.xml");

       /* Parser parser = (Parser) applicationContext.getBean("sourceWebSite");

        parser.getArticles(webPage);
        ArrayList<String> newsTitles = parser.getNewsTitles();
        ArrayList<Source> newsSources = new ArrayList<>();*/

        Searcher googleSearcher = (Searcher) applicationContext.getBean("googleSearcher");
        System.out.println(userData.getSelection());
        ArrayList<SearchResult> googleSearchResults = googleSearcher.getResults(userData.getSelection());

        Searcher twitterSearcher = (Searcher) applicationContext.getBean("twitterSearcher");

       /* ArrayList<SearchResult> twitterSearchResults = new ArrayList<>();
        try {
            twitterSearchResults = twitterSearcher.getResults("qry");
        } catch (TwitterException e) {
            e.printStackTrace();
        }*/

        System.out.println(googleSearchResults);
        String json = new Gson().toJson(googleSearchResults);

        System.out.println(json);
        return json;
    }


}