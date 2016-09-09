import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by David on 9/7/2016.
 */
public class TS3Bot {

    public static final String CONFIG_FILENAME = "config.properties";

    // TODO: 9/9/2016 add this to the config file
    public static final String AFK_MESSAGE = "You were moved to the AFK channel after being away for 30 minutes";

    final int      AFK_CHANNEL_ID;
    final TS3Query query;
    final TS3Api   api;

    Config config;

    public TS3Bot() {

        config = new Config(CONFIG_FILENAME);

        final TS3Config SERVER_CONFIG = new TS3Config();
        SERVER_CONFIG.setHost(config.getHostname());
        SERVER_CONFIG.setQueryPort(config.getQueryPort());
        SERVER_CONFIG.setDebugLevel(Level.ALL);

        this.query = new TS3Query(SERVER_CONFIG);
        query.connect();

        this.api = query.getApi();
        api.login(config.getQueryUsername(), config.getQueryPassword());
        api.selectVirtualServerById(config.getVirtServId());
        api.setNickname(config.getBotName());

        AFK_CHANNEL_ID = getAFKChannelID();
    }

    public List<Client> purgeAFK() {
        final List<Client>  clients    = api.getClients();
        List<Client>        afkClients = new ArrayList<>();

        for (Client client : clients) {
            if (client.getIdleTime() > 1_800_000 && client.getChannelId() != AFK_CHANNEL_ID) {
                afkClients.add(client);
                // TODO: 9/9/2016 make this load afk message from config
                api.sendPrivateMessage(client.getId(), AFK_MESSAGE);
                api.moveClient(client.getId(), AFK_CHANNEL_ID);
            }
        }

        return afkClients;
    }

    // TODO: 9/9/2016 make this throw exception on un-unique channel name or make it use afk channel ID by default
    private int getAFKChannelID() {
        final List<Channel> channels = api.getChannelsByName(config.getAfkChannelName());

        if (channels.size() > 1) {
            return -1;
        } else {
            return channels.get(0).getId();
        }
    }

    public static void main(String[] args) {
        TS3Bot bot = new TS3Bot();

        bot.purgeAFK();
    }
}
