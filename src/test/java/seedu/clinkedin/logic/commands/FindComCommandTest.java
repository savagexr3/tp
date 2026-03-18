package seedu.clinkedin.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.BENSON;
import static seedu.clinkedin.testutil.TypicalPersons.CARL;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindComCommand}.
 */
public class FindComCommandTest {

    private static final String MESSAGE_COMPANIES_LISTED_OVERVIEW = "%d contacts listed working at \"%s\".";

    private Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

    @Test
    public void equals() {
        CompanyContainsKeywordsPredicate firstPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Google"));
        CompanyContainsKeywordsPredicate secondPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Shopee"));

        FindComCommand findFirstCommand = new FindComCommand(firstPredicate);
        FindComCommand findSecondCommand = new FindComCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindComCommand findFirstCommandCopy = new FindComCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noMatchingKeyword_noPersonFound() {
        CompanyContainsKeywordsPredicate predicate = preparePredicate("TikTok");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 0, "TikTok");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_singlePersonFound() {
        CompanyContainsKeywordsPredicate predicate = preparePredicate("Google");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 1, "Google");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        CompanyContainsKeywordsPredicate predicate = preparePredicate("Google Shopee Grab");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 3, "Google, Shopee, Grab");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_caseInsensitiveKeyword_personFound() {
        CompanyContainsKeywordsPredicate predicate = preparePredicate("gOoGlE");
        FindComCommand command = new FindComCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 1, "gOoGlE");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Arrays.asList("Google"));
        FindComCommand findComCommand = new FindComCommand(predicate);
        String expected = FindComCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findComCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code CompanyContainsKeywordsPredicate}.
     */
    private CompanyContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CompanyContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }
}
