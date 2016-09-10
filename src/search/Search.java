package search;


import config.Config;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by David on 9/9/2016.
 */
public class Search {

    public static final int MAX_BING_RESULTS = 3;
    private static final String bingUrlPattern = "https://api.cognitive.microsoft.com/bing/v5.0/search?q=%s&format=JSON&responseFilter=webpages&count=" + MAX_BING_RESULTS;
    private static final String charset        = "UTF-8";

    private Config config;

    public Search(Config config) {
        this.config = config;


    }

    public SearchResults bing(String query) {
        SearchResults results = new SearchResults();
        try {
            final String searchUrl = String.format(bingUrlPattern, URLEncoder.encode(query, charset));

            final URL           url        = new URL(searchUrl);
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", config.getBingApiKey());

            InputStream response = connection.getInputStream();


            try (Scanner scanner = new Scanner(response)) {

                String rawJson = scanner.useDelimiter("\\A").next();

                JSONObject json = new JSONObject(rawJson).getJSONObject("webPages");
                JSONArray jsonArr = json.getJSONArray("value");

                Pattern pattern = Pattern.compile("(?<=(r\\=))(.*?)(?=(\\&p))");

                for (int i = 0; i < jsonArr.length(); i++) {
                    String  title = jsonArr.getJSONObject(i).getString("name");
                    String  link = jsonArr.getJSONObject(i).getString("url");
                    String description = jsonArr.getJSONObject(i).getString("snippet");

                    Matcher matcher = pattern.matcher(link);

                    if (matcher.find()) {
                        link = URLDecoder.decode(matcher.group(), charset);
                    }
                    results.add(link, title, description);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results.size() > 0 ? results : null;
    }

    public SearchResults youtube(String query) {
        return null;
    }

    public SearchResults wowGuild(String query) {
        return null;
    }

    public SearchResults wowPlayer(String query) {
        return null;
    }

    public SearchResults reddit(String query) {
        return null;
    }
}
