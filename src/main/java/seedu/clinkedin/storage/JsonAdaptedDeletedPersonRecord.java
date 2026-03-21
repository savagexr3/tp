package seedu.clinkedin.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;

public class JsonAdaptedDeletedPersonRecord {
    private final JsonAdaptedPerson person;
    private final String deletedDateTime;

    @JsonCreator
    public JsonAdaptedDeletedPersonRecord(
            @JsonProperty("person") JsonAdaptedPerson person,
            @JsonProperty("deletedDateTime") String deletedDateTime) {
        this.person = person;
        this.deletedDateTime = deletedDateTime;
    }

    public JsonAdaptedDeletedPersonRecord(DeletedPersonRecord source) {
        person = new JsonAdaptedPerson(source.getPerson());
        deletedDateTime = source.getDeletedDateTime().toString();
    }

    public DeletedPersonRecord toModelType() throws IllegalValueException {
        Person modelPerson = person.toModelType();
        LocalDateTime modelDateTime = LocalDateTime.parse(deletedDateTime);
        return new DeletedPersonRecord(modelPerson, modelDateTime);
    }
}
