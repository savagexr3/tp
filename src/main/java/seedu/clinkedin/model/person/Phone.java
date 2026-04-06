package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #getPhoneValidationError(String)}.
 */
public class Phone {

    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 15;

    public static final String MESSAGE_NULL = "Phone number cannot be null.";
    public static final String MESSAGE_CONSTRAINTS =
            "Phone number must contain digits only and be between 8 and 15 digits long.";
    public static final String MESSAGE_EMPTY = "Phone number cannot be empty.";
    public static final String MESSAGE_NON_DIGIT = "Phone number must contain digits only.";
    public static final String MESSAGE_TOO_SHORT = "Phone number must contain at least 8 digits.";
    public static final String MESSAGE_TOO_LONG = "Phone number cannot exceed 15 digits.";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     * @throws NullPointerException If {@code phone} is null.
     * @throws IllegalArgumentException If {@code phone} is invalid.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        String error = getPhoneValidationError(phone);
        checkArgument(error == null, error);
        value = phone;
    }

    /**
     * Returns the validation error message if the phone number is invalid, or {@code null} otherwise.
     */
    public static String getPhoneValidationError(String phone) {
        if (phone == null) {
            return MESSAGE_NULL;
        }

        if (phone.isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (!phone.matches("\\d+")) {
            return MESSAGE_NON_DIGIT;
        }

        if (phone.length() < MIN_LENGTH) {
            return MESSAGE_TOO_SHORT;
        }

        if (phone.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid phone number.
     *
     * @throws NullPointerException If {@code phone} is null.
     */
    public static boolean isValidPhone(String phone) {
        requireNonNull(phone);
        return getPhoneValidationError(phone) == null;
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

        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
