package seedu.clinkedin.logic.commands.tag;

import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code TagListCommand}.
 */
public class TagListCommandTest {

    @Test
    public void execute_tagListExist_showsTagList() {
        CLinkedin clinkedin = new CLinkedin();
        clinkedin.addTag(new Tag("colleagues"));
        clinkedin.addTag(new Tag("friends"));

        Model model = new ModelManager(clinkedin, new UserPrefs());
        Model expectedModel = new ModelManager(clinkedin, new UserPrefs());

        String expectedTagStr = "colleagues\nfriends";
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
