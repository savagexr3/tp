package seedu.clinkedin.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;

/**
 * Jackson-friendly version of {@link DeletedPersonRecord}.
 */
public class JsonAdaptedDeletedPersonRecord {
    private final JsonAdaptedPerson person;
    private final String deletedDateTime;

    /**
     * Constructs a {@code JsonAdaptedDeletedPersonRecord} with the given deleted person record details.
     */
    @JsonCreator
    public JsonAdaptedDeletedPersonRecord(
            @JsonProperty("person") JsonAdaptedPerson person,
            @JsonProperty("deletedDateTime") String deletedDateTime) {
        this.person = person;
        this.deletedDateTime = deletedDateTime;
    }

    /**
     * Converts a given {@code DeletedPersonRecord} into this class for Jackson use.
     */
    public JsonAdaptedDeletedPersonRecord(DeletedPersonRecord source) {
        person = new JsonAdaptedPerson(source.getPerson());
        deletedDateTime = source.getDeletedDateTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted deleted person record object into the model's
     * {@code DeletedPersonRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DeletedPersonRecord toModelType() throws IllegalValueException {
        Person modelPerson = person.toModelType();
        LocalDateTime modelDateTime = LocalDateTime.parse(deletedDateTime);
        return new DeletedPersonRecord(modelPerson, modelDateTime);
    }
}
