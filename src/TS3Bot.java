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
        final List<Channel> channels   = api.getChannels();
        List<Client>        afkClients = new ArrayList<>();

        for (Client client : clients) {
            if (client.getIdleTime() > 1_800_000 && client.getChannelId() != AFK_CHANNEL_ID) {
//                System.out.println(client.getNickname() + "\t" + client.getIdleTime());
                afkClients.add(client);
                api.moveClient(client.getId(), AFK_CHANNEL_ID);
            }
        }

        return afkClients;
    }

    private int getAFKChannelID() {
        final List<Channel> channels = api.getChannelsByName(config.getAfkChannelName());

        if (channels.size() > 1) {
            return -1;
        } else {
            return channels.get(0).getId();
        }
    }

    public static long convertTimeToMillis(int hours, int minutes, int seconds, int milliseconds) {
        return hours * 3600000 + minutes * 60000 + seconds * 1000 + milliseconds;
    }

    public static void main(String[] args) {
        TS3Bot bot = new TS3Bot();

        bot.purgeAFK();
    }
}
