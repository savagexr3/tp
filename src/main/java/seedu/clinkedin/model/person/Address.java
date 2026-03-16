package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final int MAX_LENGTH = 100;

    public static final String MESSAGE_CONSTRAINTS =
            "Address must be non-empty, at most 100 characters long, and use only single spaces between words.";

    public static final String MESSAGE_EMPTY =
            "Address cannot be empty.";

    public static final String MESSAGE_TOO_LONG =
            "Address cannot exceed 100 characters.";

    public static final String MESSAGE_MULTIPLE_SPACES =
            "Address can only contain single spaces between words.";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        String error = getAddressValidationError(address);
        checkArgument(error == null, error);
        value = address;
    }

    /**
     * Returns the error message if the address is invalid, otherwise null.
     */
    public static String getAddressValidationError(String test) {
        if (test.isEmpty() || test.trim().isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (test.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        if (test.contains("  ")) {
            return MESSAGE_MULTIPLE_SPACES;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        requireNonNull(test);
        return getAddressValidationError(test) == null;
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

        // instanceof handles nulls
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
