package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinkedin.commons.core.GuiSettings;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.ReadOnlyUserPrefs;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.testutil.PersonBuilder;

/**
 * Contains unit tests (with stubs) for {@code SortComCommand}.
 * Integration tests are placed in {@code SortComCommandIntegrationTest}.
 */
public class SortComCommandTest {

    @Test
    public void execute_emptyList_success() {
        // EP: no displayed contacts
        ModelStubSortedByCompany modelStub = new ModelStubSortedByCompany();
        SortComCommand sortComCommand = new SortComCommand();

        CommandResult commandResult = sortComCommand.execute(modelStub);

        assertEquals(SortComCommand.MESSAGE_EMPTY, commandResult.getFeedbackToUser());
        assertTrue(modelStub.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_sortByCompany_success() {
        // EP: multiple displayed contacts with different company names
        ModelStubSortedByCompany modelStub = new ModelStubSortedByCompany();

        Person googlePerson = new PersonBuilder().withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("NUS")
                .withCompany("Google")
                .build();

        Person amazonPerson = new PersonBuilder().withName("Charlie")
                .withPhone("93456789")
                .withEmail("charlie@example.com")
                .withAddress("SMU")
                .withCompany("amazon")
                .build();

        Person grabPerson = new PersonBuilder().withName("Bob")
                .withPhone("92345678")
                .withEmail("bob@example.com")
                .withAddress("NTU")
                .withCompany("grab")
                .build();

        modelStub.addPerson(googlePerson);
        modelStub.addPerson(amazonPerson);
        modelStub.addPerson(grabPerson);

        SortComCommand sortComCommand = new SortComCommand();
        CommandResult commandResult = sortComCommand.execute(modelStub);

        assertEquals(SortComCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertEquals(amazonPerson, modelStub.getFilteredPersonList().get(0));
        assertEquals(googlePerson, modelStub.getFilteredPersonList().get(1));
        assertEquals(grabPerson, modelStub.getFilteredPersonList().get(2));
    }

    @Test
    public void execute_sortByCompany_caseInsensitive() {
        // EP: sorting is case-insensitive
        ModelStubSortedByCompany modelStub = new ModelStubSortedByCompany();

        Person lowerCaseGoogle = new PersonBuilder().withName("A")
                .withPhone("90000001")
                .withEmail("a@example.com")
                .withAddress("A Street")
                .withCompany("google")
                .build();

        Person applePerson = new PersonBuilder().withName("B")
                .withPhone("90000002")
                .withEmail("b@example.com")
                .withAddress("B Street")
                .withCompany("Apple")
                .build();

        Person amazonPerson = new PersonBuilder().withName("C")
                .withPhone("90000003")
                .withEmail("c@example.com")
                .withAddress("C Street")
                .withCompany("amazon")
                .build();

        modelStub.addPerson(lowerCaseGoogle);
        modelStub.addPerson(applePerson);
        modelStub.addPerson(amazonPerson);

        SortComCommand sortComCommand = new SortComCommand();
        sortComCommand.execute(modelStub);

        assertEquals(amazonPerson, modelStub.getFilteredPersonList().get(0));
        assertEquals(applePerson, modelStub.getFilteredPersonList().get(1));
        assertEquals(lowerCaseGoogle, modelStub.getFilteredPersonList().get(2));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        // EP: required execute input is null
        SortComCommand sortComCommand = new SortComCommand();
        assertThrows(NullPointerException.class, () -> sortComCommand.execute(null));
    }

    @Test
    public void equals() {
        SortComCommand firstCommand = new SortComCommand();
        SortComCommand secondCommand = new SortComCommand();

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
    }

    @Test
    public void toStringMethod() {
        SortComCommand sortComCommand = new SortComCommand();
        String expected = SortComCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, sortComCommand.toString());
    }

    /**
     * A default model stub that has all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyCLinkedin newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCLinkedin getCLinkedin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPhoneNumber(Phone phone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person restorePerson(DeletedPersonRecord deletedPersonRecord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DeletedPersonRecord> getFilteredDeletedPersonRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeletedPersonRecordList(Predicate<DeletedPersonRecord> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetFilteredPersonListSorting() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonListByCompany() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTags(List<Tag> tags) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub that supports adding persons and sorting them by company.
     */
    private class ModelStubSortedByCompany extends ModelStub {
        private final ArrayList<Person> persons = new ArrayList<>();

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            persons.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public void sortFilteredPersonListByCompany() {
            persons.sort(Comparator.comparing(
                    person -> person.getCompany() == null ? "" : person.getCompany().value,
                    String.CASE_INSENSITIVE_ORDER));
        }

        @Override
        public ReadOnlyCLinkedin getCLinkedin() {
            return new CLinkedin();
        }
    }
}
