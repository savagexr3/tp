package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the date a Person was added to CLinkedin.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateAdded {

    // In case save file has invalid date format
    public static final String MESSAGE_CONSTRAINTS = "Date added should be in the format dd-MM-yyyy";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final String value;
    public final LocalDate date;

    /**
     * Constructs a {@code DateAdded} using a specific date string (Used by Storage to load old contacts).
     */
    public DateAdded(String dateString) {
        requireNonNull(dateString);
        checkArgument(isValidDate(dateString), MESSAGE_CONSTRAINTS);
        this.value = dateString;
        this.date = LocalDate.parse(dateString, FORMATTER);
    }

    /**
     * Constructs a {@code DateAdded} using the current system date
     */
    public DateAdded() {
        this.date = LocalDate.now();
        this.value = this.date.format(FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date in the format dd-MM-yyyy.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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
        if (!(other instanceof DateAdded)) {
            return false;
        }
        DateAdded otherDate = (DateAdded) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
