package seedu.clinkedin.logic.commands.tag;

import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code TagListCommand}.
 */
public class TagListCommandTest {
    private Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

    @Test
    public void execute_tagListExist_showsTagList() {
        String expectedTagStr = expectedModel.getCLinkedin().getTagList().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.joining("\n"));
        String expectedMsg = String.format(TagListCommand.MESSAGE_SUCCESS, expectedTagStr);

        assertCommandSuccess(new TagListCommand(), model, new CommandResult(expectedMsg), expectedModel);
    }

    @Test
    public void execute_tagListEmpty_showsNoTagsMsg() {
        Model model = new ModelManager(new CLinkedin(), new UserPrefs());
        Model expectedModel = new ModelManager(new CLinkedin(), new UserPrefs());

        assertCommandSuccess(new TagListCommand(), model, new CommandResult(TagListCommand.MESSAGE_NO_TAGS),
                expectedModel);
    }
}
