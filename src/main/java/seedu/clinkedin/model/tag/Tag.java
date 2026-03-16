package seedu.clinkedin.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; valid according to {@link #getTagNameValidationError(String)}.
 */
public class Tag {

    public static final int MAX_LENGTH = 20;

    public static final String MESSAGE_NULL = "Phone number cannot be null";

    public static final String MESSAGE_CONSTRAINTS =
            "Tag must contain only letters and numbers, and cannot exceed 20 characters.";

    public static final String MESSAGE_EMPTY =
            "Tag cannot be empty.";

    public static final String MESSAGE_TOO_LONG =
            "Tag cannot exceed 20 characters.";

    public static final String MESSAGE_INVALID_CHARACTERS =
            "Tag must contain only letters and numbers.";

    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        String error = getTagNameValidationError(tagName);
        checkArgument(error == null, error);
        this.tagName = tagName;
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
