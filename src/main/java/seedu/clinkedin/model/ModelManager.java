package seedu.clinkedin.model;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.clinkedin.commons.core.GuiSettings;
import seedu.clinkedin.commons.core.LogsCenter;
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

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyCLinkedin cLinkedin, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(cLinkedin, userPrefs);

        logger.fine("Initializing with address book: " + cLinkedin + " and user prefs " + userPrefs);

        this.cLinkedin = new CLinkedin(cLinkedin);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.cLinkedin.getPersonList());
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

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
