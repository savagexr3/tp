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

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "DeletedPersonRecord's %s field is missing!";

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
        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "person"));
        }

        if (deletedDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "deletedDateTime"));
        }

        Person modelPerson = person.toModelType();
        LocalDateTime modelDeletedDateTime = LocalDateTime.parse(deletedDateTime);
        return new DeletedPersonRecord(modelPerson, modelDeletedDateTime);
    }
}
