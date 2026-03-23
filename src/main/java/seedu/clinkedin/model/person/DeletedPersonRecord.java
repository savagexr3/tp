package seedu.clinkedin.model.person;

import java.time.LocalDateTime;

/**
 * Represents a record of a deleted {@code Person}, together with the time of deletion.
 * Guarantees: person and deletion time are present and not null.
 */
public class DeletedPersonRecord {
    private final Person person;
    private final LocalDateTime deletedDateTime;

    /**
     * Creates a {@code DeletedPersonRecord} for the specified person and records
     * the current date and time as the deletion time.
     *
     * @param person The deleted person.
     */
    public DeletedPersonRecord(Person person) {
        this.person = person;
        deletedDateTime = LocalDateTime.now();
    }

    /**
     * Creates a {@code DeletedPersonRecord} for the specified person and deletion time.
     *
     * @param person The deleted person.
     * @param deletedDateTime The date and time the person was deleted.
     */
    public DeletedPersonRecord(Person person, LocalDateTime deletedDateTime) {
        this.person = person;
        this.deletedDateTime = deletedDateTime;
    }

    /**
     * Returns the deleted person.
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Returns the date and time the person was deleted.
     */
    public LocalDateTime getDeletedDateTime() {
        return deletedDateTime;
    }
}
