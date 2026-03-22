package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.clinkedin.commons.core.GuiSettings;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.ReadOnlyUserPrefs;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.tag.Tag;

public class TagDeleteCommandTest {
    @Test
    public void constructor_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagDeleteCommand(null));
    }

    @Test
    public void execute_existingTag_success() throws Exception {
        Tag validTag = new Tag("friends");
        ModelStubWithTag modelStub = new ModelStubWithTag(validTag);

        CommandResult result = new TagDeleteCommand(validTag).execute(modelStub);

        assertEquals(String.format(TagDeleteCommand.MESSAGE_SUCCESS, validTag.tagName), result.getFeedbackToUser());
        assertFalse(modelStub.hasTag(validTag));
    }

    @Test
    public void execute_nonExistingTag_throwsCommandException() {
        Tag validTag = new Tag("friends");
        TagDeleteCommand command = new TagDeleteCommand(validTag);
        ModelStub modelStub = new ModelStubWithoutTag();

        assertThrows(CommandException.class, TagDeleteCommand.MESSAGE_NOT_EXIST_TAG, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Tag friends = new Tag("friends");
        Tag colleagues = new Tag("colleagues");

        TagDeleteCommand deleteFriendsCommand = new TagDeleteCommand(friends);
        TagDeleteCommand deleteColleaguesCommand = new TagDeleteCommand(colleagues);

        // same object -> returns true
        assertTrue(deleteFriendsCommand.equals(deleteFriendsCommand));

        // same values -> returns true
        TagDeleteCommand deleteFriendsCommandCopy = new TagDeleteCommand(friends);
        assertTrue(deleteFriendsCommand.equals(deleteFriendsCommandCopy));

        // different types -> returns false
        assertFalse(deleteFriendsCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFriendsCommand.equals(null));

        // different tag -> returns false
        assertFalse(deleteFriendsCommand.equals(deleteColleaguesCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
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
        public void restorePerson(DeletedPersonRecord deletedPersonRecord) {
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
    }

    /**
     * Model stub with one existing tag.
     */
    private class ModelStubWithTag extends ModelStub {
        private final CLinkedin cLinkedin = new CLinkedin();

        ModelStubWithTag(Tag tag) {
            requireNonNull(tag);
            cLinkedin.addTag(tag);
        }

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return cLinkedin.hasTag(tag);
        }

        @Override
        public void deleteTag(Tag tag) {
            requireNonNull(tag);
            cLinkedin.removeTag(tag);
        }
    }

    /**
     * Model stub without the target tag.
     */
    private class ModelStubWithoutTag extends ModelStub {
        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return false;
        }
    }
}
