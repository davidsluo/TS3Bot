package search;

import java.util.ArrayList;

/**
 * Created by David on 9/9/2016.
 */
public class SearchResults extends ArrayList<SearchResult> {

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (SearchResult result : this) {
            if (result.getText().equals("")) {
                builder.append(String.format("[url]%s[/url] - %s", result.getLink(), result.getDescription()));
            } else {
                builder.append(String.format("[url=%s]%s[/url] - %s", result.getLink(), result.getText(), result.getDescription()));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public boolean add(String link, String text, String description) {
        super.add(new SearchResult(link, text, description));
        return true;
    }

}
