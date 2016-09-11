package commands.search.urban_dictionary;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import commands.search.SearchCommand;
import commands.search.SearchResults;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by David on 9/10/2016.
 */
public class UrbanDictionarySearchCommand extends SearchCommand {

    private static final String   urlPattern = "http://api.urbandictionary.com/v0/define?term=%s";
    private static final String[] aliases    = {
            "urbandictionary",
            "urbandict",
            "ud"
    };


    public UrbanDictionarySearchCommand(TextMessageEvent e) {
        super(e);
    }

    @Override
    public String[] getAliases() {
        return aliases;
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

        return results;
    }

    @Override
    public SearchResults search(String query) {
        return search(10, query);
    }
}
