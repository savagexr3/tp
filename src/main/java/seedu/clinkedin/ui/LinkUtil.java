package seedu.clinkedin.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;

/**
 * Utility class for link-related operations.
 */
public class LinkUtil {

    private static final Logger logger = LogsCenter.getLogger(LinkUtil.class);

    /**
     * Opens the given URL in the system's default web browser.
     * Returns true if successful, false otherwise.
     */
    public static boolean openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                return true;
            } else {
                logger.warning("Desktop browsing is not supported on this system.");
                return false;
            }
        } catch (Exception e) {
            logger.warning("Failed to open link: " + url + " — " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns true if the given URL string is a valid URI.
     */
    public static boolean isValidUri(String url) {
        try {
            new URI(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
