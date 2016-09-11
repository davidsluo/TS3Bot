package commands.search;

/**
 * Created by David on 9/9/2016.
 */
public abstract class SearchResult {

    private final String LINK;
    private final String TITLE;
    private final String DESCRIPTION;
    private final String FORMAT_PATTERN = "[url=%1$s]%2$s[/url] - %3$s";

    public SearchResult (String link, String title, String description) {
        this.LINK = link;
        this.TITLE = title;
        this.DESCRIPTION = description;
    }

    public String getLink() {
        return LINK;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public String toString() {
        return String.format(FORMAT_PATTERN, getLink(), getTitle(), getDescription());
    }
}
