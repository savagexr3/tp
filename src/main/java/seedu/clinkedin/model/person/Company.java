package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; valid according to {@link #getCompanyNameValidationError(String)}.
 */
public class Company {

    public static final String MESSAGE_CONSTRAINTS =
            "Company name must be 1-50 characters long, contain only letters, spaces, "
                    + "apostrophes (') and hyphens (-), and use only single spaces between words.";

    /**
     * Company name must have a maximum length of 50 characters,
     * use only letters, numbers, spaces, period (.), comma (,), ampersand (&) and hyphens (-),
     * and contain only single spaces between words.
     */
    public static final int MAX_LENGTH = 50;
    public static final String MESSAGE_TOO_LONG =
            "Company name cannot exceed 50 characters.";

    public static final String MESSAGE_INVALID_CHARACTERS =
            "Company name can only contain letters, numbers, spaces, period (.), "
                    + "comma (,), ampersand (&) and hyphens (-).";

    public static final String MESSAGE_MULTIPLE_SPACES =
            "Company name can only contain single spaces between words.";

    private static final String VALIDATION_REGEX = "[\\p{Alnum} .,&-]+";

    public final String companyName;

    /**
     * Constructs a {@code Company name}.
     *
     * @param name A valid name.
     */
    public Company(String name) {
        requireNonNull(name);
        String error = getCompanyNameValidationError(name);
        checkArgument(error == null, error);
        companyName = name;
    }

    /**
     * Returns the error message if the company name is invalid, otherwise null.
     */
    public static String getCompanyNameValidationError(String test) {
        if (test == null) {
            return null; // OPTIONAL → null is allowed
        }

        if (test.isEmpty()) {
            return null; // OPTIONAL → empty is allowed
        }

        if (test.length() > MAX_LENGTH) {
            return MESSAGE_TOO_LONG;
        }

        if (test.startsWith(" ") || test.endsWith(" ") || test.contains("  ")) {
            return MESSAGE_MULTIPLE_SPACES;
        }

        if (!test.matches(VALIDATION_REGEX)) {
            return MESSAGE_INVALID_CHARACTERS;
        }

        return null;
    }

    /**
     * Returns true if a given string is a valid company name.
     */
    public static boolean isValidCompanyName(String test) {
        requireNonNull(test);
        return getCompanyNameValidationError(test) == null;
    }

    @Override
    public String toString() {
        return companyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherName = (Company) other;
        return companyName.equals(otherName.companyName);
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }

}

