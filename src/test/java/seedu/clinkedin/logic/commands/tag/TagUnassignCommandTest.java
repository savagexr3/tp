package seedu.clinkedin.logic.commands.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.tag.Tag;

public class TagUnassignCommandTest {

    private Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

    @Test
    public void execute_validIndexValidTag_success() throws Exception {
        // ALICE (first person) has "friends" tag
        Tag tag = new Tag("friends");
        TagUnassignCommand command = new TagUnassignCommand(INDEX_FIRST_PERSON, tag);
        CommandResult result = command.execute(model);
        assertEquals(TagUnassignCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().get(0).getTags().contains(tag));
    }

    @Test
    public void execute_tagNotAssigned_throwsCommandException() {
        Tag tag = new Tag("colleagues");
        TagUnassignCommand command = new TagUnassignCommand(INDEX_FIRST_PERSON, tag);
        assertThrows(CommandException.class,
                TagUnassignCommand.MESSAGE_TAG_NOT_ASSIGNED, () -> command.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Tag tag = new Tag("friends");
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagUnassignCommand command = new TagUnassignCommand(outOfBound, tag);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        Tag tag = new Tag("friends");
        TagUnassignCommand command1 = new TagUnassignCommand(INDEX_FIRST_PERSON, tag);
        TagUnassignCommand command2 = new TagUnassignCommand(INDEX_FIRST_PERSON, tag);
        TagUnassignCommand command3 = new TagUnassignCommand(INDEX_SECOND_PERSON, tag);

        assertTrue(command1.equals(command2));
        assertTrue(command1.equals(command1));
        assertFalse(command1.equals(null));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(new TagListCommand()));
    }
}
