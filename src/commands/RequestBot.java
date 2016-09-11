package commands;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import ts3bot.TS3Bot;

/**
 * Created by David on 9/10/2016.
 */
public class RequestBot extends Command {

    private static final String[] aliases = {
            "requestbot"
    };

    public RequestBot(TextMessageEvent e) {
        super(e);
    }

    @Override
    public void execute(String... params) {
        TS3Bot.api.moveClient(
                TS3Bot.api.whoAmI().getId(),
                TS3Bot.api.getClientInfo(e.getInvokerId()).getChannelId()
        );
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }
}
