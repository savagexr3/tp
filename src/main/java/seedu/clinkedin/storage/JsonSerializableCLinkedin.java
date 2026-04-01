package seedu.clinkedin.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * An Immutable Clinkedin that is serializable to JSON format.
 */
@JsonRootName(value = "Clinkedin")
class JsonSerializableCLinkedin {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TAG = "Tags list contains duplicate tag(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedDeletedPersonRecord> deletedPersonRecords = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableClinkedin} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCLinkedin(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                     @JsonProperty("deletedPersonRecords")
                                     List<JsonAdaptedDeletedPersonRecord> deletedPersonRecords,
                                     @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        if (persons != null) {
            this.persons.addAll(persons);
        }

        if (deletedPersonRecords != null) {
            this.deletedPersonRecords.addAll(deletedPersonRecords);
        }

        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCLinkedin}.
     */
    public JsonSerializableCLinkedin(ReadOnlyCLinkedin source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        deletedPersonRecords.addAll(source.getDeletedPersonRecords().stream()
                .map(JsonAdaptedDeletedPersonRecord::new).collect(Collectors.toList()));
        tags.addAll(source.getTagList().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Clinkedin into the model's {@code Clinkedin} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CLinkedin toModelType() throws IllegalValueException {
        CLinkedin cLinkedin = new CLinkedin();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (cLinkedin.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            cLinkedin.addPerson(person);
        }

        for (JsonAdaptedDeletedPersonRecord jsonRecord : deletedPersonRecords) {
            cLinkedin.addDeletedPersonRecord(jsonRecord.toModelType());
        }

        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (cLinkedin.hasTag(tag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            cLinkedin.addTag(tag);
        }

        cLinkedin.pruneExpiredDeletedPersonRecords();
        return cLinkedin;
    }

}
