import chat.Command;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by David on 9/7/2016.
 */
public class TS3Bot implements TS3Listener {

    public static final String CONFIG_FILEPATH = "config/config.properties";

    final int      AFK_CHANNEL_ID;
    final TS3Query query;
    final TS3Api   api;

    Config config;

    public TS3Bot() {

        config = new Config(CONFIG_FILEPATH);

        final TS3Config SERVER_CONFIG = new TS3Config();
        SERVER_CONFIG.setHost(config.getHostname());
        SERVER_CONFIG.setQueryPort(config.getQueryPort());
        SERVER_CONFIG.setDebugLevel(Level.ALL);

        this.query = new TS3Query(SERVER_CONFIG);
        query.connect();

        // TODO: 9/9/2016 make this asynchronous?
        this.api = query.getApi();
        api.login(config.getQueryUsername(), config.getQueryPassword());
        api.selectVirtualServerById(config.getVirtServId());
        api.setNickname(config.getBotName());

        AFK_CHANNEL_ID = getAFKChannelID();
    }

    private int getAFKChannelID() throws Error {
        final List<Channel> channels = api.getChannelsByName(config.getAfkChannelName());

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
        final List<Client> clients    = api.getClients();
        List<Client>       afkClients = new ArrayList<>();

        for (Client client : clients) {
            if (client.getIdleTime() > config.getAfkTime() && client.getChannelId() != AFK_CHANNEL_ID) {
                afkClients.add(client);
                api.sendPrivateMessage(client.getId(), config.getAfkMessage());
                api.moveClient(client.getId(), AFK_CHANNEL_ID);
            }
        }

        return afkClients;
    }

    public void shutdown() {
        query.exit();
    }

    public static void main(String[] args) {
        TS3Bot bot = new TS3Bot();

        bot.purgeAFK();

//        bot.shutdown();
    }

    @Override
    public void onTextMessage(TextMessageEvent e) {
        String message = e.getMessage();
        if (message.charAt(0) == config.getCommandOperator()) {
            String[] splitMsg = message.split("\\s+");
            String   command  = splitMsg[0].substring(1);
            String[] args     = Arrays.copyOfRange(splitMsg, 1, splitMsg.length);

            // TODO: 9/9/2016 add logging of commands
            switch (command) {
                case "help":
                    // TODO: 9/9/2016 send help message (maybe have help message auto-compiled)
                    break;
                case "youtube":
                case "yt":
                    // TODO: 9/9/2016 search youtube, get links to top 3 videos
                    // list top few results if not unique. can request additional pages whispered to them.
                    break;
                case "google":
                case "goog":
                case "g":
                    // TODO: 9/9/2016 search google, get top 3 results
                    // list top few results if not unique. can request additional pages whispered to them.
                    break;
                case "guildlookup":
                case "glookup":
                    // TODO: 9/9/2016 get link to requested guild if only one result.
                    // list top few results if not unique. can request additional pages whispered to them.
                    break;
                case "playerlookup":
                case "plookup":
                    // TODO: 9/9/2016 get link to requested player's profile
                    // list top few results if not unique. can request additional pages whispered to them.
                    break;
                case "raid":
                    // TODO: 9/9/2016 move predefined clients to predefined channel
                    // implement group system?
                    // use teamspeak groups?
                    break;
                default:
                    // TODO: 9/9/2016 state invalid command
                    break;
            }
        } else if (message.substring(0,2).equals("/r/") ||
                message.substring(0,1).equals("r/")) {
            // TODO: 9/9/2016 add linking to subreddits
        } else if (message.substring(0,2).equals("/u/") ||
                message.substring(0,1).equals("u/")) {
            // TODO: 9/9/2016 add linking to reddit users
        }
    }

    @Override
    public void onClientJoin(ClientJoinEvent e) {
    }

    @Override
    public void onClientLeave(ClientLeaveEvent e) {
    }

    @Override
    public void onServerEdit(ServerEditedEvent e) {
    }

    @Override
    public void onChannelEdit(ChannelEditedEvent e) {
    }

    @Override
    public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
    }

    @Override
    public void onClientMoved(ClientMovedEvent clientMovedEvent) {
    }

    @Override
    public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {
    }

    @Override
    public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {
    }

    @Override
    public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {
    }

    @Override
    public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {
    }

    @Override
    public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {
    }
}
