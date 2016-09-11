package ts3bot;

import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import commands.PurgeAfk;
import commands.RequestBot;
import commands.search.bing.BingSearch;
import commands.search.urban_dictionary.UrbanDictionarySearch;

import java.util.Arrays;

/**
 * Created by David on 9/10/2016.
 */
public class CommandDetector extends TS3EventAdapter {

    @Override
    public void onTextMessage(TextMessageEvent e) {

        final String message = e.getMessage();
        if (message.charAt(0) == TS3Bot.config.getCommandOperator()) {
            final String[] splitMsg = message.split("\\s+");
            final String   command  = splitMsg[0].substring(1);
            final String[] args     = Arrays.copyOfRange(splitMsg, 1, splitMsg.length);

            // TODO: 9/9/2016 add logging of commands
            switch (command.toLowerCase()) {
                case "help":
                    // TODO: 9/9/2016 send help message (maybe have help message auto-compiled)
                    TS3Bot.messenger.sendMessage(e, "This command has not been implemented yet.");
                    break;
                case "youtube":
                case "yt":
                    // TODO: 9/9/2016 search youtube, get links to top 3 videos
                    // list top few results if not unique. can request additional pages whispered to them.
                    TS3Bot.messenger.sendMessage(e, "This command has not been implemented yet.");
                    break;
                case "google":
                case "goog":
                case "g":
                case "bing":
                case "b":
                case "search":
                    // TODO: can request additional pages whispered to them.

                    new BingSearch(e).execute(args);

//                    String bingSearchQuery = combineArray(args);
//
//                    String[] bingResponse =
//                            new BingSearch(TS3Bot.config)
//                                    .search(bingSearchQuery)
//                                    .toString()
//                                    .split("!___!");
//
//                    for (String r : bingResponse) {
//                        TS3Bot.messenger.sendMessage(e, r);
//                    }
                    break;
                case "urbandictionary":
                case "ud":

                    new UrbanDictionarySearch(e).execute(args);

                    break;
                case "guildlookup":
                case "glookup":
                    // TODO: 9/9/2016 get link to requested guild if only one result.
                    // list top few results if not unique. can request additional pages whispered to them.
                    TS3Bot.messenger.sendMessage(e, "This command has not been implemented yet.");
                    break;
                case "playerlookup":
                case "plookup":
                    // TODO: 9/9/2016 get link to requested player's profile
                    // list top few results if not unique. can request additional pages whispered to them.
                    TS3Bot.messenger.sendMessage(e, "This command has not been implemented yet.");
                    break;
                case "raid":
                    // TODO: 9/9/2016 move predefined clients to predefined channel
                    // implement group system?
                    // use teamspeak groups?
                    TS3Bot.messenger.sendMessage(e, "This command has not been implemented yet.");
                    break;
                case "purgeafk":
                case "afk":
                    new PurgeAfk(e).execute(args);
                    break;
                case "requestbot":
                    new RequestBot(e).execute(args);
                    break;
                default:
                    // TODO: 9/9/2016 state invalid command
                    TS3Bot.messenger.sendMessage(e, "Invalid command. Try !help for a list of available commands.");
                    break;
            }

        } else if (message.substring(0, 2).equals("/r/") || message.substring(0, 1).equals("r/"))

        {
            // TODO: 9/9/2016 add linking to subreddits
        } else if (message.substring(0, 2).equals("/u/") || message.substring(0, 1).equals("u/"))

        {
            // TODO: 9/9/2016 add linking to reddit users
        }

    }

}
