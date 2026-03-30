package seedu.clinkedin.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

import javafx.scene.paint.Color;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; valid according to {@link #getTagNameValidationError(String)}.
 */
public class Tag {

    public static final int MAX_LENGTH = 20;

    public static final String MESSAGE_NULL = "Tag name cannot be null";

    public static final String MESSAGE_CONSTRAINTS =
            "Tag must contain only letters and numbers, and cannot exceed 20 characters.";

    public static final String MESSAGE_EMPTY =
            "Tag cannot be empty.";

    public static final String MESSAGE_TOO_LONG =
            "Tag cannot exceed 20 characters.";

    public static final String MESSAGE_INVALID_CHARACTERS =
            "Tag must contain only letters and numbers.";

    public static final String MESSAGE_INVALID_COLOR_NAME =
            "Invalid color name. Valid formats are in plain name, hexadecimal, or rgb() values.\n"
                    + "Example: #ff6688, orange, rgb(255,102,136)";

    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public static final String DEFAULT_COLOR = "#3e7b91";

    public final String tagName;
    public final String tagColor;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        this(tagName, DEFAULT_COLOR);
    }

    /**
     * Constructs a {@code Tag} with a specified color
     *
     * @param tagName A valid tag name.
     * @param tagColor A valid color.
     */
    public Tag(String tagName, String tagColor) {
        requireNonNull(tagName);
        requireNonNull(tagColor);
        String tagNameError = getTagNameValidationError(tagName);
        String tagColorError = getColorNameValidationError(tagColor);
        checkArgument(tagNameError == null, tagNameError);
        checkArgument(tagColorError == null, tagColorError);
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    /**
     * Returns the error message if the tag name is invalid, otherwise null.
     */
    public static String getTagNameValidationError(String test) {
        if (test == null) {
            return MESSAGE_NULL;
        }

        if (test.isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (test.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        if (!test.matches(VALIDATION_REGEX)) {
            return MESSAGE_INVALID_CHARACTERS;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        requireNonNull(test);
        return getTagNameValidationError(test) == null;
    }

    public String getTagName() {
        return tagName;
    }

    /**
     * Returns error message if the color is invalid, otherwise null.
     */
    public static String getColorNameValidationError(String color) {
        try {
            Color.web(color);
            checkIfValidColorFormat(color);
        } catch (IllegalArgumentException e) {
            return MESSAGE_INVALID_COLOR_NAME;
        }
        return null;
    }

    private static void checkIfValidColorFormat(String color) throws IllegalArgumentException {
        if (!color.startsWith("#") && !color.matches("^[a-zA-Z]*$")) {
            throw new IllegalArgumentException(MESSAGE_INVALID_COLOR_NAME);
        }
    }

    /**
     * Returns true if a given string is a valid color.
     */
    public static boolean isValidColorName(String color) {
        requireNonNull(color);
        return getColorNameValidationError(color) == null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }
}
