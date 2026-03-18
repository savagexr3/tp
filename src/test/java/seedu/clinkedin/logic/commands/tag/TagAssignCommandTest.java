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

public class TagAssignCommandTest {

    private Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

    @Test
    public void execute_validIndexValidTag_success() throws Exception {
        Tag tag = new Tag("colleagues");
        model.addTag(tag);
        TagAssignCommand command = new TagAssignCommand(INDEX_FIRST_PERSON, tag);
        CommandResult result = command.execute(model);
        assertEquals(TagAssignCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(model.getFilteredPersonList().get(0).getTags().contains(tag));
    }

    @Test
    public void execute_tagNotInModel_throwsCommandException() {
        Tag tag = new Tag("nonexistent");
        TagAssignCommand command = new TagAssignCommand(INDEX_FIRST_PERSON, tag);
        assertThrows(CommandException.class,
                TagAssignCommand.MESSAGE_TAG_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void execute_tagAlreadyAssigned_throwsCommandException() {
        // ensure the tag exists in the model first
        Tag tag = new Tag("friends");
        if (!model.hasTag(tag)) {
            model.addTag(tag);
        }
        // ALICE (first person) already has "friends" tag
        TagAssignCommand command = new TagAssignCommand(INDEX_FIRST_PERSON, tag);
        assertThrows(CommandException.class,
                TagAssignCommand.MESSAGE_TAG_ALREADY_ASSIGNED, () -> command.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Tag tag = new Tag("friends");
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagAssignCommand command = new TagAssignCommand(outOfBound, tag);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        Tag tag = new Tag("friends");
        TagAssignCommand command1 = new TagAssignCommand(INDEX_FIRST_PERSON, tag);
        TagAssignCommand command2 = new TagAssignCommand(INDEX_FIRST_PERSON, tag);
        TagAssignCommand command3 = new TagAssignCommand(INDEX_SECOND_PERSON, tag);

        assertTrue(command1.equals(command2));
        assertTrue(command1.equals(command1));
        assertFalse(command1.equals(null));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(new TagListCommand()));
    }
}
