package seedu.clinkedin.logic.commands.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.BENSON;
import static seedu.clinkedin.testutil.TypicalPersons.DANIEL;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.model.tag.TagContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code TagShowCommand}.
 */
public class TagShowCommandTest {
    private Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

    @Test
    public void equals() {
        Tag tagOne = new Tag("first");
        Tag tagTwo = new Tag("second");

        TagContainsKeywordPredicate firstPredicate = new TagContainsKeywordPredicate(tagOne);
        TagContainsKeywordPredicate secondPredicate = new TagContainsKeywordPredicate(tagTwo);

        TagShowCommand tagShowFirstCommand = new TagShowCommand(firstPredicate, tagOne);
        TagShowCommand tagShowSecondCommand = new TagShowCommand(secondPredicate, tagTwo);

        // same object -> returns true
        assertTrue(tagShowFirstCommand.equals(tagShowFirstCommand));

        // same values -> returns true
        TagShowCommand tagShowFirstCommandCopy = new TagShowCommand(firstPredicate, tagOne);
        assertTrue(tagShowFirstCommand.equals(tagShowFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagShowFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagShowFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(tagShowFirstCommand.equals(tagShowSecondCommand));
    }

    @Test
    public void execute_tagDoesNotExist_throwsCommandException() {
        Tag tag = new Tag("friends");
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(tag);
        TagShowCommand command = new TagShowCommand(predicate, tag);
        String errorMessage = TagShowCommand.MESSAGE_TAG_NOT_FOUND + tag.toString();
        assertThrows(CommandException.class, errorMessage, () -> command.execute(model));
    }

    @Test
    public void execute_friendsTag_multiplePersonsFound() {
        Tag tag = new Tag("friends");
        model.addTag(tag);
        expectedModel.addTag(tag);
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(tag);
        TagShowCommand command = new TagShowCommand(predicate, tag);
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(
                "%d contacts listed with tag '%s'.",
                3,
                tag.toString());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }
}
