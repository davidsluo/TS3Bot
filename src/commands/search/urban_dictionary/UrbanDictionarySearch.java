package commands.search.urban_dictionary;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import config.Config;
import org.json.JSONArray;
import org.json.JSONObject;
import commands.search.Search;
import commands.search.SearchResults;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by David on 9/10/2016.
 */
public class UrbanDictionarySearch extends Search {

    String urlPattern = "http://api.urbandictionary.com/v0/define?term=%s";

    public UrbanDictionarySearch(TextMessageEvent e) {
        super(e);
    }

    @Override
    public SearchResults search(int numResults, String query) {
        SearchResults results = new SearchResults();
        try {
            final String searchUrl = String.format(urlPattern, URLEncoder.encode(query, CHARSET));

            final URL           url        = new URL(searchUrl);
            final URLConnection connection = url.openConnection();

            InputStream response = connection.getInputStream();

            try (Scanner scanner = new Scanner(response)) {

                String rawJson = scanner.useDelimiter("\\A").next();

                JSONObject json    = new JSONObject(rawJson);
                JSONArray  jsonArr = json.getJSONArray("list");

                for (int i = 0; i < jsonArr.length() && i < numResults; i++) {
                    String title       = jsonArr.getJSONObject(i).getString("word");
                    String link        = jsonArr.getJSONObject(i).getString("permalink");
                    String description = jsonArr.getJSONObject(i).getString("definition");
                    String example     = jsonArr.getJSONObject(i).getString("example");

                    results.add(new UrbanDictionarySearchResult(link, title, description, example));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        results.setHeader("Displaying " + results.size() +
                (results.size() == 1 ? " result " : " results ") +
                "for [b]" + query + "[/b].\n");

        return results.size() > 0 ? results : null;}

    @Override
    public SearchResults search(String query) {
        return search(10, query);
    }
}
