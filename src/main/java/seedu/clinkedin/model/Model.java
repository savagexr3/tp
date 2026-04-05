package seedu.clinkedin.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.clinkedin.commons.core.GuiSettings;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluates to true for deleted person records.
     */
    Predicate<DeletedPersonRecord> PREDICATE_SHOW_ALL_DELETED_PERSON_RECORDS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyCLinkedin addressBook);

    /** Returns the AddressBook */
    ReadOnlyCLinkedin getCLinkedin();

    /**
     * Returns true if a person with the same phone number exists in the address book.
     */
    boolean hasPhoneNumber(Phone phone);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    //@@author rxlee04
    /**
     * Restores the given deleted person record.
     * The person in the record is re-added to the address book with only
     * tags that currently exist retained, and the corresponding deleted
     * person record is removed from the deleted list.
     *
     * @param deletedPersonRecord The deleted person record to restore. Must not be null.
     * @return The restored {@code Person} with non-existent tags removed.
     */
    Person restorePerson(DeletedPersonRecord deletedPersonRecord);

    //@@author
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //@@author rxlee04
    /**
     * Returns an unmodifiable view of the filtered deleted person record list.
     */
    ObservableList<DeletedPersonRecord> getFilteredDeletedPersonRecordList();

    /**
     * Updates the filter of the filtered deleted person record list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeletedPersonRecordList(Predicate<DeletedPersonRecord> predicate);

    /**
     * Checks if the given tag exists in the system.
     *
     * @param tag The tag to check.
     * @return {@code true} if the tag exists, {@code false} otherwise.
     * @throws NullPointerException if {@code tag} is null.
     */
    boolean hasTag(Tag tag);

    /**
     * Adds a new tag to the system.
     *
     * @param tag The tag to add.
     * @throws NullPointerException if {@code tag} is null.
     */
    void addTag(Tag tag);

    /**
     * Deletes the given tag from the system.
     *
     * @param tag The tag to delete.
     * @throws NullPointerException if {@code tag} is null.
     */
    void deleteTag(Tag tag);

    //@@author
    void setTags(List<Tag> tags);

    /**
     * Sorts the filtered person list by company name alphabetically, case-insensitively.
     */
    void sortFilteredPersonListByCompany();
    void resetFilteredPersonListSorting();
}
