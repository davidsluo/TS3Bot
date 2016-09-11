package ts3bot;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by David on 9/10/2016.
 */
public class Utils {
    public static String combineArray(String[] arr, int startIndex, int endIndex) {
        return Arrays.stream(Arrays.copyOfRange(arr, startIndex, endIndex)).collect(Collectors.joining(" "));

    }

    public static String combineArray(String[] arr, int startIndex) {
        return combineArray(arr, startIndex, arr.length);
    }

    public static String combineArray(String[] arr) {
        return combineArray(arr, 0);
    }

    // Get length of string after replacing characters with escape sequences according to teamspeak's escape sequence rules according to:
    // http://media.teamspeak.com/ts3_literature/TeamSpeak%203%20Server%20Query%20Manual.pdf
    public static int TSLength(String string) {
        return string.replace("\\", "\\\\").replace("/", "\\/").replace(" ", "\\s").replace("|", "\\|").length();
    }
}
