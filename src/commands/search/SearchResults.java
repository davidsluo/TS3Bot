package commands.search;

import Chat.Messenger;
import ts3bot.Utils;

import java.util.ArrayList;

/**
 * Created by David on 9/9/2016.
 */
public class SearchResults extends ArrayList<SearchResult> {

    private String header = "";

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(header);

        for (SearchResult result : this) {
            // Skips result if longer than character limit
            if (Utils.TSLength(result.toString()) <= Messenger.MESSAGE_CHARACTER_LIMIT) {

                if (Utils.TSLength(result.toString()) >
                        (Messenger.MESSAGE_CHARACTER_LIMIT -
                                ((Utils.TSLength(builder.toString() + Messenger.NEW_MESSAGE_DELIMITER))
                                        % Messenger.MESSAGE_CHARACTER_LIMIT))) {
                    builder.append(Messenger.NEW_MESSAGE_DELIMITER);
                    builder.append("\n");
                }

                builder.append(result.toString());
                builder.append("\n");
            }
        }
        return builder.toString();
    }

}
