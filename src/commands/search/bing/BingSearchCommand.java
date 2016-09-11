package commands.search.bing;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import commands.search.SearchCommand;
import org.json.JSONArray;
import org.json.JSONObject;
import commands.search.SearchResults;
import ts3bot.TS3Bot;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by David on 9/10/2016.
 */
public class BingSearchCommand extends SearchCommand {

    private static final String urlPattern = "https://api.cognitive.microsoft.com/bing/v5.0/search?q=%s&format=JSON&responseFilter=webpages";
    private static final String[] aliases = {
            "bing",
            "b",
            "google",
            "goog",
            "g",
            "search"
    };

    public BingSearchCommand(TextMessageEvent e) {
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
                    final String searchUrl = String.format(urlPattern + "&count=" + numResults, URLEncoder.encode(query, CHARSET));

                    final URL           url        = new URL(searchUrl);
                    final URLConnection connection = url.openConnection();
                    connection.setRequestProperty("Ocp-Apim-Subscription-Key", TS3Bot.config.getBingApiKey());

                    InputStream response = connection.getInputStream();


                    try (Scanner scanner = new Scanner(response)) {

                String rawJson = scanner.useDelimiter("\\A").next();

                JSONObject json    = new JSONObject(rawJson).getJSONObject("webPages");
                JSONArray  jsonArr = json.getJSONArray("value");

                Pattern pattern = Pattern.compile("(?<=(r\\=))(.*?)(?=(\\&p))");

                for (int i = 0; i < jsonArr.length(); i++) {
                    String title       = jsonArr.getJSONObject(i).getString("name");
                    String link        = jsonArr.getJSONObject(i).getString("url");
                    String description = jsonArr.getJSONObject(i).getString("snippet");

                    Matcher matcher = pattern.matcher(link);

                    if (matcher.find()) {
                        link = URLDecoder.decode(matcher.group(), CHARSET);
                    }
                    results.add(new BingSearchResult(link, title, description));
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
        return search(3, query);
    }
}
