package commands.search;

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
                builder.append("[url]" + result.getLink() + "[/url]");
            } else {
                builder.append("[url=" + result.getLink() + "]" + result.getText() + "[/url]");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
