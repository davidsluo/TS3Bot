package commands;

/**
 * Created by David on 9/9/2016.
 */
public abstract class Command {

    // TODO: 9/9/2016 figure out the structure of this class/interface/whatever.

    private String[] aliases;

    public void execute(String... params) {
    }

    public String[] getAliases() {
        return aliases;
    }


}
