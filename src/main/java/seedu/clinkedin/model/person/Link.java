package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's link in the address book.
 * Guarantees: immutable; valid according to {@link #getLinkValidationError(String)}.
 */
public class Link {

    public static final String MESSAGE_CONSTRAINTS = "Links should be a valid URL and adhere to the following constraints:\n"
            + "1. The link must start with 'http://' or 'https://'.\n"
            + "2. This is followed by a domain name made up of domain labels separated by periods.\n"
            + "The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.\n"
            + "3. The link may optionally include a path, query string, or fragment.";

    public static final String MESSAGE_NULL =
            "Link cannot be null.";
    public static final String MESSAGE_EMPTY =
            "Link cannot be empty.";
    public static final String MESSAGE_SPACE_NOT_ALLOWED =
            "Link cannot contain spaces.";
    public static final String MESSAGE_INVALID_SCHEME =
            "Link must start with 'http://' or 'https://'.";
    public static final String MESSAGE_INVALID_DOMAIN =
            "The domain name in the link is invalid.";
    public static final String MESSAGE_CONSECUTIVE_DOT =
            "Link cannot contain consecutive \".\".";

    public final String value;

    /**
     * Constructs a {@code Link}.
     *
     * @param link A valid link.
     */
    public Link(String link) {
        requireNonNull(link);
        String error = getLinkValidationError(link);
        checkArgument(error == null, error);
        value = link;
    }

    /**
     * Returns the error message if the link is invalid, otherwise returns null.
     */
    public static String getLinkValidationError(String test) {
        if (test == null) {
            return MESSAGE_NULL;
        }

        if (test.isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (test.contains(" ")) {
            return MESSAGE_SPACE_NOT_ALLOWED;
        }

        if (test.contains("..")) {
            return MESSAGE_CONSECUTIVE_DOT;
        }

        // Check scheme
        String rest;
        if (test.startsWith("https://")) {
            rest = test.substring("https://".length());
        } else if (test.startsWith("http://")) {
            rest = test.substring("http://".length());
        } else {
            return MESSAGE_INVALID_SCHEME;
        }

        if (rest.isEmpty()) {
            return MESSAGE_INVALID_DOMAIN;
        }

        // Separate domain from path/query/fragment
        int slashIndex = rest.indexOf('/');
        String domainPart = slashIndex == -1 ? rest : rest.substring(0, slashIndex);

        if (domainPart.isEmpty()) {
            return MESSAGE_INVALID_DOMAIN;
        }

        if (!domainPart.contains(".")) {
            return MESSAGE_INVALID_DOMAIN;
        }

        String[] labels = domainPart.split("\\.", -1);

        for (String label : labels) {
            if (label.isEmpty()) {
                return MESSAGE_INVALID_DOMAIN;
            }

            if (!Character.isLetterOrDigit(label.charAt(0))
                    || !Character.isLetterOrDigit(label.charAt(label.length() - 1))) {
                return MESSAGE_INVALID_DOMAIN;
            }

            if (!label.matches("[A-Za-z0-9-]+")) {
                return MESSAGE_INVALID_DOMAIN;
            }
        }

        String lastLabel = labels[labels.length - 1];
        if (lastLabel.length() < 2) {
            return MESSAGE_INVALID_DOMAIN;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidLink(String test) {
        requireNonNull(test);
        return getLinkValidationError(test) == null;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Link)) {
            return false;
        }

        Link otherLink = (Link) other;
        return value.equals(otherLink.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}