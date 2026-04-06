package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.BENSON;
import static seedu.clinkedin.testutil.TypicalPersons.CARL;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinkedin.commons.core.GuiSettings;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.ReadOnlyUserPrefs;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.tag.Tag;

/**
 * Contains unit tests (with stubs) and integration tests for {@code FindComCommand}.
 */
public class FindComCommandTest {

    private static final String MESSAGE_COMPANIES_LISTED_OVERVIEW = "%d contacts listed working at \"%s\".";

    @Test
    public void execute_updatesModelUsingPredicate_success() {
        // Unit test with stub: verifies interaction with Model and message formatting only.
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Google"));
        ModelStubAcceptingPredicate modelStub = new ModelStubAcceptingPredicate(1);

        FindComCommand command = new FindComCommand(predicate);
        CommandResult result = command.execute(modelStub);

        assertEquals(predicate, modelStub.predicatePassedToUpdate);
        assertEquals(String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 1, "Google"), result.getFeedbackToUser());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Google"));
        FindComCommand command = new FindComCommand(predicate);

        // EP: required execute input is null
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals() {
        CompanyContainsKeywordsPredicate firstPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Google"));
        CompanyContainsKeywordsPredicate secondPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Shopee"));

        FindComCommand findFirstCommand = new FindComCommand(firstPredicate);
        FindComCommand findSecondCommand = new FindComCommand(secondPredicate);

        // Same object
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // Same values
        FindComCommand findFirstCommandCopy = new FindComCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // Different type
        assertFalse(findFirstCommand.equals(1));

        // Null comparison
        assertFalse(findFirstCommand.equals(null));

        // Different command
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void toStringMethod() {
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Arrays.asList("Google"));
        FindComCommand findComCommand = new FindComCommand(predicate);
        String expected = FindComCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findComCommand.toString());
    }

    @Test
    public void execute_noMatchingKeyword_noPersonFound() {
        // Integration test with real ModelManager.
        Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

        CompanyContainsKeywordsPredicate predicate = preparePredicate("TikTok");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 0, "TikTok");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_singlePersonFound() {
        // Integration test with real ModelManager.
        // EP: one valid keyword produces one match
        Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

        CompanyContainsKeywordsPredicate predicate = preparePredicate("Google");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 1, "Google");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        // Integration test with real ModelManager.
        // Heuristic for multiple inputs: each valid keyword category appears at least once in a positive test.
        Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

        CompanyContainsKeywordsPredicate predicate = preparePredicate("Google Shopee Grab");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 3, "Google, Shopee, Grab");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_caseInsensitiveKeyword_personFound() {
        // Integration test with real ModelManager.
        // EP: case-insensitive matching
        Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

        CompanyContainsKeywordsPredicate predicate = preparePredicate("gOoGlE");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 1, "gOoGlE");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code CompanyContainsKeywordsPredicate}.
     */
    private CompanyContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CompanyContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
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
        public void setTags(List<Tag> tags) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonListByCompany() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetFilteredPersonListSorting() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub that records the predicate passed into updateFilteredPersonList.
     */
    private class ModelStubAcceptingPredicate extends ModelStub {
        private final ObservableList<Person> filteredPersons;
        private Predicate<Person> predicatePassedToUpdate;

        ModelStubAcceptingPredicate(int resultSize) {
            List<Person> persons = new ArrayList<>();
            for (int i = 0; i < resultSize; i++) {
                persons.add(ALICE);
            }
            filteredPersons = FXCollections.observableArrayList(persons);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            requireNonNull(predicate);
            predicatePassedToUpdate = predicate;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }
    }
}
