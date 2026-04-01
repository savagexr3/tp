package seedu.clinkedin.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.person.UniquePersonList;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class CLinkedin implements ReadOnlyCLinkedin {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private ObservableList<DeletedPersonRecord> deletedPersonRecords;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        deletedPersonRecords = FXCollections.observableArrayList();
        tags = new UniqueTagList();
    }

    public CLinkedin() {
    }

    /**
     * Creates a CLinkedin address book using the Persons in the {@code toBeCopied}
     */
    public CLinkedin(ReadOnlyCLinkedin toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code CLinkedin} with {@code newData}.
     */
    public void resetData(ReadOnlyCLinkedin newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        deletedPersonRecords.setAll(newData.getDeletedPersonRecords());
        setTags(newData.getTagList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same phone number exists in the address book.
     */
    public boolean hasPhoneNumber(Phone phone) {
        requireNonNull(phone);
        return persons.containsPhoneNumber(phone);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook} and records its deletion.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        requireNonNull(key);
        pruneExpiredDeletedPersonRecords();
        persons.remove(key);
        deletedPersonRecords.add(new DeletedPersonRecord(key));
    }

    /**
     * Restores a deleted person record back into the address book.
     * The associated person is re-added to the main person list after
     * filtering out any tags that no longer exist in the address book.
     * The corresponding deleted record is then removed from the deleted list.
     *
     * @param record The deleted person record to restore. Must not be null.
     * @return The restored {@code Person} instance with only existing tags retained.
     */
    public Person restorePerson(DeletedPersonRecord record) {
        requireNonNull(record);

        Person originalPerson = record.getPerson();

        Set<Tag> updatedTags = originalPerson.getTags().stream()
                .map(tag -> tags.findByName(tag.getTagName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Person cleanedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                Optional.ofNullable(originalPerson.getCompany()),
                originalPerson.getAddress(),
                Optional.ofNullable(originalPerson.getRemark()),
                Optional.ofNullable(originalPerson.getLink()),
                originalPerson.getDateAdded(),
                updatedTags
        );

        persons.add(cleanedPerson);
        deletedPersonRecords.remove(record);

        return cleanedPerson;
    }

    //// deleted person operations

    /**
     * Adds a deleted person record to the deleted records list.
     * This method is primarily used when loading data from storage.
     *
     * @param record The deleted person record to be added.
     */
    public void addDeletedPersonRecord(DeletedPersonRecord record) {
        requireNonNull(record);
        deletedPersonRecords.add(record);
    }

    //// tag-level operations

    /**
     * Returns true if the given {@code tag} already exists in the address book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }

    /**
     * Adds a tag to the address book.
     * The tag must not already exist in the address book.
     *
     * @param t The tag to be added.
     */
    public void addTag(Tag t) {
        tags.add(t);
    }

    /**
     * Replaces the contents of the tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     *
     * @param tags The new list of tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Removes the specified tag from the address book.
     * Also removes the tag from all persons who currently have it.
     *
     * @param t The tag to be removed.
     */
    public void removeTag(Tag t) {
        tags.remove(t);
        List<Person> currentPersons = new ArrayList<>(getPersonList());
        for (Person person : currentPersons) {
            if (person.getTags().contains(t)) {
                Person updatedPerson = person.removeTag(t);
                persons.setPerson(person, updatedPerson);
            }
        }
    }

    /// / util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("tags", tags)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<DeletedPersonRecord> getDeletedPersonRecords() {
        return FXCollections.unmodifiableObservableList(deletedPersonRecords);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CLinkedin)) {
            return false;
        }

        CLinkedin otherCLinkedin = (CLinkedin) other;
        return persons.equals(otherCLinkedin.persons)
                && deletedPersonRecords.equals(otherCLinkedin.deletedPersonRecords)
                && tags.equals(otherCLinkedin.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, deletedPersonRecords, tags);
    }

    /**
     * Removes all deleted person records that are older than 7 days
     * from the current date and time.
     */
    public void pruneExpiredDeletedPersonRecords() {
        deletedPersonRecords.removeIf(record ->
                record.getDeletedDateTime().isBefore(LocalDateTime.now().minusDays(7)));
    }

}
