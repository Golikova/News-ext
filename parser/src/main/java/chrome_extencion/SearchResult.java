package chrome_extencion;

public class SearchResult {

    private String title;
    private String link;

    public SearchResult(String title, String link) {
        this.title = title;
        this.link = link;
    }

    @Override
    public String toString() {
        return "Source{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
