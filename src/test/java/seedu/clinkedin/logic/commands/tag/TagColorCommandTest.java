package seedu.clinkedin.logic.commands.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.tag.Tag;

public class TagColorCommandTest {

    private Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

    @Test
    public void equals() {
        Tag tagOne = new Tag("first");
        Tag tagTwo = new Tag("second");

        TagColorCommand tagColorFirstCommand = new TagColorCommand(tagOne, "blue");
        TagColorCommand tagColorSecondCommand = new TagColorCommand(tagTwo, "green");

        // same object -> returns true
        assertTrue(tagColorFirstCommand.equals(tagColorFirstCommand));

        // same values -> returns true
        TagColorCommand tagColorFirstCommandCopy = new TagColorCommand(tagOne, "blue");
        assertTrue(tagColorFirstCommand.equals(tagColorFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagColorFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagColorFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(tagColorFirstCommand.equals(tagColorSecondCommand));
    }

    @Test
    public void execute_validTagValidColor_success() throws CommandException {
        Tag tag = new Tag("friends");
        String color = "gold";
        model.addTag(tag);
        TagColorCommand command = new TagColorCommand(tag, color);
        CommandResult result = command.execute(model);
        assertEquals(TagColorCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().get(0).getTags().contains(tag));
    }

    @Test
    public void execute_tagDoesNotExist_throwsException() throws CommandException {
        Tag tag = new Tag("friends");
        String color = "gold";
        TagColorCommand command = new TagColorCommand(tag, color);
        assertThrows(CommandException.class,
                TagColorCommand.MESSAGE_TAG_NOT_FOUND, () -> command.execute(model));
    }
}
