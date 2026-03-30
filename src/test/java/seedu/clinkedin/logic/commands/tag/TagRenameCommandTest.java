package seedu.clinkedin.logic.commands.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

public class TagRenameCommandTest {

    @Test
    public void constructor_nullOldTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagRenameCommand(null, new Tag("tag")));
    }

    @Test
    public void constructor_nullNewTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagRenameCommand(new Tag("tag"), null));
    }

    @Test
    public void execute_renameTag_success() {
        Model model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        Tag oldTag = new Tag("friends");
        Tag newTag = new Tag("closefriends");

        if (!model.hasTag(oldTag)) {
            model.addTag(oldTag);
        }

        Model expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        if (!expectedModel.hasTag(oldTag)) {
            expectedModel.addTag(oldTag);
        }

        expectedModel.addTag(newTag);

        List<Person> expectedPersonList = new ArrayList<>(expectedModel.getCLinkedin().getPersonList());
        for (Person person : expectedPersonList) {
            if (person.getTags().contains(oldTag)) {
                Set<Tag> updatedTags = new HashSet<>(person.getTags());
                updatedTags.remove(oldTag);
                updatedTags.add(newTag);

                Person editedPerson = new Person(
                        person.getName(),
                        person.getPhone(),
                        person.getEmail(),
                        Optional.ofNullable(person.getCompany()),
                        person.getAddress(),
                        Optional.ofNullable(person.getRemark()),
                        Optional.ofNullable(person.getLink()),
                        person.getDateAdded(),
                        updatedTags
                );
                expectedModel.setPerson(person, editedPerson);
            }
        }
        expectedModel.deleteTag(oldTag);
        TagRenameCommand command = new TagRenameCommand(oldTag, newTag);
        assertCommandSuccess(command, model, TagRenameCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_oldTagNotFound_throwsCommandException() {
        Model model = new ModelManager(new CLinkedin(), new UserPrefs());
        Tag oldTag = new Tag("nonexistent");
        Tag newTag = new Tag("newtag");

        TagRenameCommand command = new TagRenameCommand(oldTag, newTag);
        assertCommandFailure(command, model, TagRenameCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void execute_newTagAlreadyExists_throwsCommandException() {
        Model model = new ModelManager(new CLinkedin(), new UserPrefs());
        Tag oldTag = new Tag("friends");
        Tag newTag = new Tag("colleagues");
        model.addTag(oldTag);
        model.addTag(newTag);

        TagRenameCommand command = new TagRenameCommand(oldTag, newTag);
        assertCommandFailure(command, model, TagRenameCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void execute_sameOldAndNewTagName_throwsCommandException() {
        Model model = new ModelManager(new CLinkedin(), new UserPrefs());
        Tag oldTag = new Tag("friends");
        Tag newTag = new Tag("friends");

        TagRenameCommand renameCommand = new TagRenameCommand(oldTag, newTag);
        assertCommandFailure(renameCommand, model, TagRenameCommand.MESSAGE_SAME_TAG);
    }

    @Test
    public void equals() {
        Tag oldTag1 = new Tag("friends");
        Tag newTag1 = new Tag("colleagues");
        Tag oldTag2 = new Tag("family");
        Tag newTag2 = new Tag("coworkers");

        TagRenameCommand rename1 = new TagRenameCommand(oldTag1, newTag1);
        TagRenameCommand rename1Copy = new TagRenameCommand(oldTag1, newTag1);
        TagRenameCommand rename2 = new TagRenameCommand(oldTag2, newTag2);
        assertTrue(rename1.equals(rename1));
        assertTrue(rename1.equals(rename1Copy));
        assertFalse(rename1.equals(1));
        assertFalse(rename1.equals(null));
        assertFalse(rename1.equals(rename2));
    }
}
