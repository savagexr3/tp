package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; valid according to {@link #getEmailValidationError(String)}.
 */
public class Email {

    public static final String SPECIAL_CHARACTERS = "+_.-";

    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). The local-part may not start or end with any special "
            + "characters.\n"
            + "2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels "
            + "separated by periods.\n"
            + "The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.";

    public static final String MESSAGE_NULL =
            "Email cannot be null.";
    public static final String MESSAGE_EMPTY =
            "Email cannot be empty.";
    public static final String MESSAGE_SPACE_NOT_ALLOWED =
            "Email cannot contain spaces.";
    public static final String MESSAGE_INVALID_AT =
            "Email must contain exactly one '@' symbol.";
    public static final String MESSAGE_INVALID_LOCAL_PART =
            "The local-part of the email is invalid.";
    public static final String MESSAGE_INVALID_DOMAIN =
            "The domain name is invalid.";
    public static final String MESSAGE_CONSECUTIVE_DOT = "Email cannot contain consecutive \".\"";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        String error = getEmailValidationError(email);
        checkArgument(error == null, error);
        value = email;
    }

    /**
     * Returns the error message if the email is invalid, otherwise returns null.
     */
    public static String getEmailValidationError(String test) {
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

        long atCount = test.chars().filter(ch -> ch == '@').count();
        if (atCount != 1) {
            return MESSAGE_INVALID_AT;
        }

        int atIndex = test.indexOf('@');
        String localPart = test.substring(0, atIndex);
        String domainPart = test.substring(atIndex + 1);

        if (localPart.isEmpty()) {
            return MESSAGE_INVALID_LOCAL_PART;
        }

        if (!localPart.matches("[A-Za-z0-9+_.-]+")) {
            return MESSAGE_INVALID_LOCAL_PART;
        }

        if (!Character.isLetterOrDigit(localPart.charAt(0))
                || !Character.isLetterOrDigit(localPart.charAt(localPart.length() - 1))) {
            return MESSAGE_INVALID_LOCAL_PART;
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
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        requireNonNull(test);
        return getEmailValidationError(test) == null;
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
