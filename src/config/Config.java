package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by David on 9/8/2016.
 */
public class Config {

    private String hostname       = "";
    private int    queryPort      = -1;
    private String queryUsername  = "";
    private String queryPassword  = "";
    private int    virtServId     = -1;
    private String botName        = "";
    private String afkChannelName = "";
    private String afkMessage     = "";
    private long   afkTime        = -1;

    private final String CONFIG_FILEPATH;

    InputStream inputStream;

    public Config(String filename) {
        CONFIG_FILEPATH = filename;

        try {
            loadConfig();
        } catch (FileNotFoundException e) {
            createConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Config file loader, mostly from Crunchify's <a href="http://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/">article on config files.</a>
     *
     * @return A HashMap of the config values.
     * @throws IOException Thrown if the config file does not exist.
     */
    public void loadConfig() throws IOException {

        try {
            Properties props = new Properties();

            inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILEPATH);

            if (inputStream != null) {
                props.load(inputStream);
            } else {
                throw new FileNotFoundException("Config file does not exist.");
            }

            hostname       = props.getProperty("HOSTNAME");
            queryPort      = Integer.valueOf(props.getProperty("QUERY_PORT"));
            queryUsername  = props.getProperty("QUERY_USERNAME");
            queryPassword  = props.getProperty("QUERY_PASSWORD");
            virtServId     = Integer.valueOf(props.getProperty("VIRT_SERV_ID"));
            botName        = props.getProperty("BOT_NAME");
            afkChannelName = props.getProperty("AFK_CHANNEL_NAME");
            afkMessage     = props.getProperty("AFK_MESSAGE");
            afkTime        = Long.valueOf(props.getProperty("AFK_TIME"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void createConfig() {
//        TODO: make this method
    }


    public String getHostname() {
        return hostname;
    }

    public int getQueryPort() {
        return queryPort;
    }

    public String getQueryUsername() {
        return queryUsername;
    }

    public String getQueryPassword() {
        return queryPassword;
    }

    public int getVirtServId() {
        return virtServId;
    }

    public String getBotName() {
        return botName;
    }

    public String getAfkChannelName() {
        return afkChannelName;
    }

    public String getAfkMessage() {
        return afkMessage;
    }

    public long getAfkTime() {
        return afkTime;
    }
}
