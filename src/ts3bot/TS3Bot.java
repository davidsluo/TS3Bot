package ts3bot;

import Chat.Messenger;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import commands.PurgeAfkCommand;
import config.Config;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by David on 9/7/2016.
 */
public class TS3Bot {

    public static final String CONFIG_FILEPATH = "config/config.properties";

    public static final Config config = new Config(CONFIG_FILEPATH);

    final TS3Query query;
    public static TS3Api   api;

    public static Messenger messenger;

    public TS3Bot() {

        final TS3Config SERVER_CONFIG = new TS3Config();
        SERVER_CONFIG.setHost(config.getHostname());
        SERVER_CONFIG.setQueryPort(config.getQueryPort());
        SERVER_CONFIG.setDebugLevel(Level.ALL);

        this.query = new TS3Query(SERVER_CONFIG);
        query.connect();

        // TODO: 9/9/2016 make this asynchronous?
        api = query.getApi();
        api.login(config.getQueryUsername(), config.getQueryPassword());
        api.selectVirtualServerById(config.getVirtServId());
        api.setNickname(config.getBotName());


        messenger = new Messenger(api);

        // TODO: 9/9/2016 Multithread to run one instance of bot in each channel and place one bot in each channel to listen to every channel
//        api.registerEvent(TS3EventType.CHANNEL);
//        api.registerEvent(TS3EventType.TEXT_PRIVATE);
        api.registerAllEvents();
        api.addTS3Listeners(new CommandDetector());

    }

    public void shutdown() {
        query.exit();
    }

    public void moveToMostPopulatedChannel() {
        int mostPopulatedChannelId = api.getChannelByNameExact(config.getAfkChannelName(), true).getId();
        int mostPopulatedChannelPopulation = 0;

        List<Channel> channels = api.getChannels();

        for  (Channel channel : channels) {
            if (channel.getTotalClients() > mostPopulatedChannelPopulation) {
                mostPopulatedChannelId = channel.getId();
                mostPopulatedChannelPopulation = channel.getTotalClients();
            }
        }

        api.moveClient(api.whoAmI().getId(), mostPopulatedChannelId);
    }

    public static void main(String[] args) {
        TS3Bot bot = new TS3Bot();

        new Thread(() -> {
            while (true) {
                new PurgeAfkCommand().execute();
                bot.moveToMostPopulatedChannel();

                try {
                    Thread.sleep(5 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        while (true) {
//            bot.purgeAFK();
//        }

//        bot.shutdown();
    }
}
