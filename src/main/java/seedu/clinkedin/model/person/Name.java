package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; valid according to {@link #getNameValidationError(String)}.
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Name must be 1-100 characters long, contain only letters, spaces, apostrophes (') and hyphens (-), "
                    + "and use only single spaces between words.";

    /**
     * Name must have a maximum length of 100 characters,
     * use only letters, spaces, apostrophes (') and hyphens (-),
     * and contain only single spaces between words.
     */
    public static final int MAX_LENGTH = 100;
    public static final String MESSAGE_NULL = "Name cannot be null";
    public static final String MESSAGE_EMPTY =
            "Name cannot be empty.";

    public static final String MESSAGE_TOO_LONG =
            "Name cannot exceed 100 characters.";

    public static final String MESSAGE_INVALID_CHARACTERS =
            "Name can only contain letters, spaces, apostrophes (') and hyphens (-).";

    public static final String MESSAGE_MULTIPLE_SPACES =
            "Name can only contain single spaces between words.";

    private static final String VALID_CHAR_REGEX = "[A-Za-z'\\- ]+";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String error = getNameValidationError(name);
        checkArgument(error == null, error);
        fullName = name;
    }

    /**
     * Returns the error message if the name is invalid, otherwise null.
     */
    public static String getNameValidationError(String test) {
        if (test == null) {
            return MESSAGE_NULL;
        }
        if (test.isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (test.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        if (test.startsWith(" ") || test.endsWith(" ") || test.contains("  ")) {
            return MESSAGE_MULTIPLE_SPACES;
        }

        if (!test.matches(VALID_CHAR_REGEX)) {
            return MESSAGE_INVALID_CHARACTERS;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        return getNameValidationError(test) == null;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
