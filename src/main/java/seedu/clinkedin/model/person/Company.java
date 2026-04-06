package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's company in the address book.
 * Guarantees: immutable; valid according to {@link #getCompanyNameValidationError(String)}.
 */
public class Company {

    public static final int MAX_LENGTH = 50;

    public static final String MESSAGE_CONSTRAINTS =
            "Company name must be 1-50 characters long, contain only letters, numbers, spaces, "
                    + "period (.), comma (,), ampersand (&) and hyphens (-), and use only single spaces between words.";

    public static final String MESSAGE_NULL =
            "Company cannot be null.";

    public static final String MESSAGE_EMPTY =
            "Company cannot be empty.";

    public static final String MESSAGE_TOO_LONG =
            "Company name cannot exceed 50 characters.";

    public static final String MESSAGE_INVALID_CHARACTERS =
            "Company name can only contain letters, numbers, spaces, period (.), comma (,),"
                    + " ampersand (&) and hyphens (-).";

    public static final String MESSAGE_INVALID_SPACING =
            "Company name can only contain single spaces between words and cannot start or end with spaces.";

    private static final String VALIDATION_REGEX = "[\\p{Alnum} .,&-]+";

    public final String value;

    /**
     * Constructs a {@code Company}.
     *
     * @param companyName A valid company name.
     * @throws NullPointerException If {@code companyName} is null.
     * @throws IllegalArgumentException If {@code companyName} is invalid.
     */
    public Company(String companyName) {
        requireNonNull(companyName);
        String error = getCompanyNameValidationError(companyName);
        checkArgument(error == null, error);
        value = companyName;
    }

    /**
     * Returns the validation error message if the company name is invalid, or {@code null} otherwise.
     */
    public static String getCompanyNameValidationError(String companyName) {
        if (companyName == null) {
            return MESSAGE_NULL;
        }

        if (companyName.isEmpty() || companyName.trim().isEmpty()) {
            return MESSAGE_EMPTY;
        }

        if (companyName.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        if (companyName.startsWith(" ") || companyName.endsWith(" ") || companyName.contains("  ")) {
            return MESSAGE_INVALID_SPACING;
        }

        if (!companyName.matches(VALIDATION_REGEX)) {
            return MESSAGE_INVALID_CHARACTERS;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid company name.
     *
     * @throws NullPointerException If {@code companyName} is null.
     */
    public static boolean isValidCompanyName(String companyName) {
        requireNonNull(companyName);
        return getCompanyNameValidationError(companyName) == null;
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

        if (!(other instanceof Company)) {
            return false;
        }

        Company otherName = (Company) other;
        return value.equals(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
