package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; valid according to {@link #getRemarkValidationError(String)}.
 */
public class Remark {

    public static final int MAX_LENGTH = 200;

    public static final String MESSAGE_NULL =
            "Remark cannot be null.";

    public static final String MESSAGE_EMPTY =
            "Remark cannot be empty.";

    public static final String MESSAGE_TOO_LONG =
            "Remark cannot exceed 200 characters.";

    public static final String REMARK_CONTAINS_INVALID_CHARACTERS =
            "Remark contains invalid characters (/).";

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        String error = getRemarkValidationError(remark);
        checkArgument(error == null, error);
        value = remark;
    }

    /**
     * Returns the error message if the remark is invalid, otherwise null.
     */
    public static String getRemarkValidationError(String test) {
        if (test == null) {
            return MESSAGE_NULL;
        }

        if (test.isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (test.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        if (test.contains("/")) {
            return REMARK_CONTAINS_INVALID_CHARACTERS;
        }

        return null; // any characters allowed
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        requireNonNull(test);
        return getRemarkValidationError(test) == null;
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

        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
