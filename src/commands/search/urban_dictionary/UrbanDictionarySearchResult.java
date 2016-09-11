package commands.search.urban_dictionary;

import commands.search.SearchResult;

/**
 * Created by David on 9/10/2016.
 */
public class UrbanDictionarySearchResult extends SearchResult {

    private final String FORMAT_PATTERN = "[url=%1$s]%2$s[/url] - %3$s\n[i]%4$s[/i]\n";

    private final String EXAMPLE;

    public UrbanDictionarySearchResult(String link, String title, String description) {
        this(link, title, description, "");
    }

    public UrbanDictionarySearchResult(String link, String title, String definition, String example) {
        super(link, title, definition);
        this.EXAMPLE = example;
    }

    public String getExample() {
        return EXAMPLE;
    }

    @Override
    public String toString() {
        return String.format(FORMAT_PATTERN, getLink(), getTitle(), getDescription(), getExample());
    }
}
