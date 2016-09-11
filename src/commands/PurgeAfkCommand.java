package commands;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import ts3bot.TS3Bot;

import java.util.*;

/**
 * Created by David on 9/10/2016.
 */
public class PurgeAfkCommand extends Command {

    private final int AFK_CHANNEL_ID;
    private static final String[] aliases = {
            "purgeafk",
            "afk"
    };

    public PurgeAfkCommand(TextMessageEvent e) {
        super(e);
        AFK_CHANNEL_ID = getAFKChannelID();
    }

    public PurgeAfkCommand() {
        this(null);
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    @Override
    public void execute(String... params) {
        List<Client> afkers = purgeAFK();

        if (e == null) {
            TS3Bot.messenger.sendServerMessage(
                    "Moved " + afkers.size() + " AFK client"
                            + (afkers.size() == 1 ? "." : "s."));
        } else {
        TS3Bot.messenger.sendMessage(e, "Moved " + afkers.size() + " AFK client"
                + (afkers.size() == 1 ? "." : "s."));
        }
    }

    /**
     * @return the ID of the afk channel
     * @throws Error
     * @pre api is defined
     */
    private int getAFKChannelID() throws Error {
        final List<Channel> channels = TS3Bot.api.getChannelsByName(TS3Bot.config.getAfkChannelName());

        if (channels.size() > 1) {
            throw new Error("AFK Channel name not unique!");
        } else {
            return channels.get(0).getId();
        }
    }

    /**
     * Moves all AFK clients to AFK channel.
     *
     * @return The clients that were moved.
     */
    public List<Client> purgeAFK() {
        final List<Client> clients    = TS3Bot.api.getClients();
        List<Client>       afkClients = new ArrayList<>();

        for (Client client : clients) {
            if (client.getIdleTime() > TS3Bot.config.getAfkTime() && client.getChannelId() != AFK_CHANNEL_ID) {
                afkClients.add(client);
                TS3Bot.api.sendPrivateMessage(client.getId(), TS3Bot.config.getAfkMessage());
                TS3Bot.api.moveClient(client.getId(), AFK_CHANNEL_ID);
            }
        }

        return afkClients;
    }

}
