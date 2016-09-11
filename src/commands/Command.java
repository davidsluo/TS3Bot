package commands;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

/**
 * Created by David on 9/9/2016.
 */
public abstract class Command implements Alias{

    // TODO: 9/9/2016 figure out the structure of this class/interface/whatever.
    protected TextMessageEvent e;

    public Command(TextMessageEvent e) {
        this.e = e;
    }

    public abstract void execute(String... params);


}
