package eps.domain;

/** Small helper methods for checking text input. */
public class TextUtil {
    private TextUtil() {
    }

    /** True if the text is null, empty, or contains only whitespace characters. */
    public static boolean isBlank(String text) {
        if (text == null) {
            return true;
        }
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                return false;
            }
        }
        return true;
    }
}