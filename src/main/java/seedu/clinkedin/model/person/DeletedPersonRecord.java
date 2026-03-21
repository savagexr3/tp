package seedu.clinkedin.model.person;

import java.time.LocalDateTime;

public class DeletedPersonRecord {
    private final Person person;
    private final LocalDateTime deletedDateTime;

    public DeletedPersonRecord(Person person) {
        this.person = person;
        deletedDateTime = LocalDateTime.now();
    }

    public DeletedPersonRecord(Person person, LocalDateTime deletedDateTime) {
        this.person = person;
        this.deletedDateTime = deletedDateTime;
    }

    public Person getPerson() {
        return this.person;
    }

    public LocalDateTime getDeletedDateTime() {
        return deletedDateTime;
    }
}
