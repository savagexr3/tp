package seedu.clinkedin.model;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.clinkedin.commons.core.GuiSettings;
import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CLinkedin cLinkedin;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<DeletedPersonRecord> filteredDeletedPersonRecords;
    private final SortedList<Person> sortedPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyCLinkedin cLinkedin, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(cLinkedin, userPrefs);

        logger.fine("Initializing with address book: " + cLinkedin + " and user prefs " + userPrefs);

        this.cLinkedin = new CLinkedin(cLinkedin);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.cLinkedin.getPersonList());
        filteredDeletedPersonRecords = new FilteredList<>(this.cLinkedin.getDeletedPersonRecords());
        sortedPersons = new SortedList<>(filteredPersons);
    }

    public ModelManager() {
        this(new CLinkedin(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyCLinkedin cLinkedin) {
        this.cLinkedin.resetData(cLinkedin);
    }

    @Override
    public ReadOnlyCLinkedin getCLinkedin() {
        return cLinkedin;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return cLinkedin.hasPerson(person);
    }

    @Override
    public boolean hasPhoneNumber(Phone phone) {
        requireNonNull(phone);
        return cLinkedin.hasPhoneNumber(phone);
    }

    @Override
    public void deletePerson(Person target) {
        cLinkedin.removePerson(target);
    }

    //@@author rxlee04
    @Override
    public Person restorePerson(DeletedPersonRecord deletedPersonRecord) {
        requireNonNull(deletedPersonRecord);
        Person cleanedPerson = cLinkedin.restorePerson(deletedPersonRecord);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredDeletedPersonRecordList(PREDICATE_SHOW_ALL_DELETED_PERSON_RECORDS);
        return cleanedPerson;
    }

    //@@author
    @Override
    public void addPerson(Person person) {
        cLinkedin.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        cLinkedin.setPerson(target, editedPerson);
    }

    //@@author rxlee04
    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return cLinkedin.hasTag(tag);
    }

    @Override
    public void addTag(Tag tag) {
        requireNonNull(tag);
        cLinkedin.addTag(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        requireNonNull(tag);
        cLinkedin.removeTag(tag);
    }

    //@@author
    @Override
    public void setTags(List<Tag> tags) {
        requireNonNull(tags);
        cLinkedin.setTags(tags);
    }
    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Deleted Person List Accessors =============================================================

    //@@author rxlee04
    @Override
    public ObservableList<DeletedPersonRecord> getFilteredDeletedPersonRecordList() {
        return filteredDeletedPersonRecords;
    }

    @Override
    public void updateFilteredDeletedPersonRecordList(Predicate<DeletedPersonRecord> predicate) {
        requireNonNull(predicate);
        filteredDeletedPersonRecords.setPredicate(predicate);
    }

    //@@author savagexr3
    //=========== Filtered Person List Sorting =============================================================

    /**
     * Sorts the currently displayed (filtered) list of {@code Person} by company name (case-insensitive).
     *
     * <p>
     * Sorting is applied only to the filtered view (i.e., {@code sortedPersons}),
     * and does not modify the underlying {@code CLinkedin} person list.
     * </p>
     *
     * <p>
     * Persons without a company are treated as having an empty string (""),
     * and will appear before persons with non-empty company names.
     * </p>
     */
    @Override
    public void sortFilteredPersonListByCompany() {
        sortedPersons.setComparator((p1, p2) -> {
            String company1 = p1.getCompany() != null ? p1.getCompany().value : "";
            String company2 = p2.getCompany() != null ? p2.getCompany().value : "";
            return company1.compareToIgnoreCase(company2);
        });
    }

    /**
     * Resets any sorting applied to the filtered person list.
     *
     * <p>
     * After calling this method, the filtered list will revert to its original
     * order as defined by the underlying {@code CLinkedin} data.
     * </p>
     */
    @Override
    public void resetFilteredPersonListSorting() {
        sortedPersons.setComparator(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return cLinkedin.equals(otherModelManager.cLinkedin)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && sortedPersons.equals(otherModelManager.sortedPersons)
                && filteredDeletedPersonRecords.equals(otherModelManager.filteredDeletedPersonRecords);
    }

}
