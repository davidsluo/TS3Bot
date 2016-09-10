package search;

/**
 * Created by David on 9/9/2016.
 */
public class SearchResult {

    private final String link;
    private final String text;
    private final String description;

    public SearchResult (String link, String text, String description) {
        this.link = link;
        this.text = text;
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }
}
