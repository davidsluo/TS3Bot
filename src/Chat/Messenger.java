package Chat;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

/**
 * Created by David on 9/10/2016.
 */
public class Messenger {

    public static final String NEW_MESSAGE_DELIMITER   = "!___!";
    public static final int    MESSAGE_CHARACTER_LIMIT = 1024;

    private TS3Api api;

    public Messenger(TS3Api api) {
        this.api = api;
    }

    public void sendMessage(TextMessageTargetMode mode, int targetId, String message) {
        String[] messages = message.split(NEW_MESSAGE_DELIMITER);
        for (String m : messages) {
            api.sendTextMessage(mode, targetId, m);
        }
    }

    public void sendMessage(TextMessageEvent e, String message) {
        sendMessage(e.getTargetMode(), e.getInvokerId(), message);
    }

    public void sendServerMessage(String message) {
        String[] messages = message.split(NEW_MESSAGE_DELIMITER);
        for (String m : messages) {
            api.sendServerMessage(m);
        }
    }
}
