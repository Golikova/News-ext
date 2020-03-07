package chrome_extencion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPage {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Document getDocument() throws IOException, SAXException, ParserConfigurationException {

        Document doc = Jsoup.parseBodyFragment(code);
        return doc;
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "webPage='" + code + '\'' +
                '}';
    }


}

