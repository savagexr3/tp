package seedu.clinkedin.logic.commands.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.util.Arrays;
import java.util.List;

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
    public void execute_singleValidIndex_success() throws Exception {
        // ALICE (first person) has "friends" tag
        Tag tag = new Tag("friends");
        TagUnassignCommand command = new TagUnassignCommand(List.of(INDEX_FIRST_PERSON), tag);
        CommandResult result = command.execute(model);
        assertEquals(String.format(TagUnassignCommand.MESSAGE_SUCCESS, 1), result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().get(0).getTags().contains(tag));
    }

    @Test
    public void execute_multipleValidIndexes_success() throws Exception {
        // ALICE and BENSON both have "friends" tag
        Tag tag = new Tag("friends");
        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        TagUnassignCommand command = new TagUnassignCommand(indexes, tag);
        CommandResult result = command.execute(model);
        assertEquals(String.format(TagUnassignCommand.MESSAGE_SUCCESS, 2), result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().get(0).getTags().contains(tag));
        assertFalse(model.getFilteredPersonList().get(1).getTags().contains(tag));
    }

    @Test
    public void execute_tagNotAssigned_throwsCommandException() {
        Tag tag = new Tag("colleagues");
        TagUnassignCommand command = new TagUnassignCommand(List.of(INDEX_FIRST_PERSON), tag);
        assertThrows(CommandException.class,
                String.format(TagUnassignCommand.MESSAGE_TAG_NOT_ASSIGNED, 1), () -> command.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Tag tag = new Tag("friends");
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagUnassignCommand command = new TagUnassignCommand(List.of(outOfBound), tag);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        Tag tag = new Tag("friends");
        TagUnassignCommand command1 = new TagUnassignCommand(List.of(INDEX_FIRST_PERSON), tag);
        TagUnassignCommand command2 = new TagUnassignCommand(List.of(INDEX_FIRST_PERSON), tag);
        TagUnassignCommand command3 = new TagUnassignCommand(List.of(INDEX_SECOND_PERSON), tag);

        assertTrue(command1.equals(command2));
        assertTrue(command1.equals(command1));
        assertFalse(command1.equals(null));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(new TagListCommand()));
    }

    @Test
    public void execute_duplicateIndexes_throwsCommandException() {
        Tag tag = new Tag("friends");
        // index 1 appears twice
        TagUnassignCommand command = new TagUnassignCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON), tag);
        assertThrows(CommandException.class,
                String.format(TagUnassignCommand.MESSAGE_DUPLICATE_INDEX, "1"),
                () -> command.execute(model));
    }

    @Test
    public void execute_multipleDuplicateIndexes_throwsCommandException() {
        Tag tag = new Tag("friends");
        // indexes 1 and 2 both appear twice
        TagUnassignCommand command = new TagUnassignCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                        INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);
        assertThrows(CommandException.class,
                String.format(TagUnassignCommand.MESSAGE_DUPLICATE_INDEX, "1, 2"),
                () -> command.execute(model));
    }
}
