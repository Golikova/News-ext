package chrome_extencion.controller;

import chrome_extencion.BasicSearcher;
import chrome_extencion.models.SearchResult;
import chrome_extencion.apiCallers.Searcher;
import chrome_extencion.models.UserData;

import chrome_extencion.parsers.Parser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MappingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.POST,  consumes= MediaType.APPLICATION_JSON_VALUE)
    public String getPage(@RequestBody UserData userData) throws IOException, JSONException, ParserConfigurationException, SAXException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("conf.xml");

        JSONObject jsonObject = new JSONObject();
        JsonArray jsonArray = new JsonArray();
        for (String source:
             userData.getSources()) {
            BasicSearcher searcher = (BasicSearcher) applicationContext.getBean(source+"Searcher");
            ArrayList<SearchResult> searchResults = searcher.getResults(userData.getSelection());
            JsonObject jsonPropValue=new JsonObject();
            jsonPropValue.addProperty("name", source);
            jsonPropValue.addProperty("data", new Gson().toJson(searchResults));
            jsonArray.add(jsonPropValue);
        }
        jsonObject.put("items", jsonArray);

        return jsonObject.toString();
    }


}