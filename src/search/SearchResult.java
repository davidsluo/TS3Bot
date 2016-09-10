package search;

/**
 * Created by David on 9/9/2016.
 */
public class SearchResult {

    private final String link;
    private final String text;

    public SearchResult (String link, String text) {
        this.link = link;
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }
}
