package seedu.clinkedin.ui;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Utility class for tag-related operations
 */
public class TagUtil {

    /**
     * Returns a Label for tag styled with its color.
     */
    public static Label tagLabel(String tagName, String tagColor) {
        Label tag = new Label(tagName);
        String color = String.format("-fx-background-color: %s;", tagColorToHexString(tagColor));
        if (colorIsTooBright(tagColor)) {
            color += "-fx-text-fill: black;";
        }
        tag.setStyle(color);
        return tag;
    }

    //@@author crow
    //Reused from https://stackoverflow.com/a/56733608
    // with minor modifications
    // Helper method
    private static String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    /**
     * Converts a valid color to a hex string for css styling
     */
    public static String tagColorToHexString(String color) {
        Color value = Color.web(color);
        return "#" + (
                format(value.getRed())
                        + format(value.getGreen())
                        + format(value.getBlue())
                        + format(value.getOpacity()))
                .toUpperCase();
    }
    //@@author

    /**
     * Returns a brightness value
     */
    public static boolean colorIsTooBright(String color) {
        double brightness = Color.web(color).getBrightness();
        double threshold = 0.7;
        if (brightness >= threshold) {
            return true;
        }
        return false;
    }
}
