package commands.search;


import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import commands.Command;
import ts3bot.TS3Bot;
import ts3bot.Utils;

/**
 * Created by David on 9/9/2016.
 */
public abstract class Search extends Command {

    protected static final String CHARSET = "UTF-8";

    public Search(TextMessageEvent e) {
        super(e);
    }

    public abstract SearchResults search(int numResults, String query);

    public abstract SearchResults search(String query);

    @Override
    public void execute(String... args) {
        SearchResults results;
        int           numResults;
        String        query;

        try {
            numResults = Integer.valueOf(args[0]);
            query = Utils.combineArray(args, 1);
            results = search(numResults, query);
        } catch (Exception ignored) {
            numResults = 1;
            query = Utils.combineArray(args);
            results = search(numResults, query);
            results.size();
        }



        TS3Bot.messenger.sendMessage(e, results.toString());
    }
}
