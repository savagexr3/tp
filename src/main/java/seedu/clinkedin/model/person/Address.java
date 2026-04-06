package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #getAddressValidationError(String)}.
 */
public class Address {

    public static final int MAX_LENGTH = 100;

    public static final String MESSAGE_CONSTRAINTS =
            "Address must be non-empty, at most 100 characters long, contain no '/' or '@', "
                    + "and use only single spaces between words.";

    public static final String MESSAGE_NULL =
            "Address cannot be null.";

    public static final String MESSAGE_EMPTY =
            "Address cannot be empty.";

    public static final String MESSAGE_TOO_LONG =
            "Address cannot exceed 100 characters.";

    public static final String MESSAGE_INVALID_SPACING =
            "Address can only contain single spaces between words and cannot start or end with spaces.";

    public static final String MESSAGE_INVALID_CHARACTERS =
            "Address contains invalid characters (/ @).";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     * @throws NullPointerException If {@code address} is null.
     * @throws IllegalArgumentException If {@code address} is invalid.
     */
    public Address(String address) {
        requireNonNull(address);
        String error = getAddressValidationError(address);
        checkArgument(error == null, error);
        value = address;
    }

    /**
     * Returns the validation error message if the address is invalid, or {@code null} otherwise.
     */
    public static String getAddressValidationError(String address) {
        if (address == null) {
            return MESSAGE_NULL;
        }

        if (address.isEmpty() || address.trim().isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (address.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        if (address.startsWith(" ") || address.endsWith(" ") || address.contains("  ")) {
            return MESSAGE_INVALID_SPACING;
        }

        if (address.contains("/") || address.contains("@")) {
            return MESSAGE_INVALID_CHARACTERS;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid address.
     *
     * @throws NullPointerException If {@code address} is null.
     */
    public static boolean isValidAddress(String address) {
        requireNonNull(address);
        return getAddressValidationError(address) == null;
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

        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
