package search;


import config.Config;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by David on 9/9/2016.
 */
public class Search {

    private static final String bingUrlPattern = "https://api.cognitive.microsoft.com/bing/v5.0/search?q=%s&format=JSON";
    private static final String charset        = "UTF-8";

    private Config config;

    public Search(Config config) {
        this.config = config;


    }

    public SearchResults bing(String query) {
        try {
            final String searchUrl = String.format(bingUrlPattern, URLEncoder.encode(query, charset));

            final URL           url        = new URL(searchUrl);
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", config.getBingApiKey());

            InputStream response = connection.getInputStream();


            try (Scanner scanner = new Scanner(response)) {

                System.out.println(scanner.useDelimiter("\\A").next());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public  SearchResults youtube(String query) {
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
