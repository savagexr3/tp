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

public class TagCreateCommandTest {
    @Test
    public void constructor_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCreateCommand(null));
    }

    @Test
    public void execute_newTag_success() throws Exception {
        ModelStubAcceptingTagAdded modelStub = new ModelStubAcceptingTagAdded();
        Tag validTag = new Tag("friends");

        CommandResult commandResult = new TagCreateCommand(validTag).execute(modelStub);

        assertEquals(String.format(TagCreateCommand.MESSAGE_SUCCESS, validTag.tagName),
                commandResult.getFeedbackToUser());
        assertTrue(modelStub.hasTag(validTag));
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        Tag validTag = new Tag("friends");
        TagCreateCommand tagCreateCommand = new TagCreateCommand(validTag);
        ModelStubWithTag modelStub = new ModelStubWithTag(validTag);

        assertThrows(CommandException.class, TagCreateCommand.MESSAGE_DUPLICATE_TAG, ()
                -> tagCreateCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Tag friends = new Tag("friends");
        Tag colleagues = new Tag("colleagues");

        TagCreateCommand createFriendsCommand = new TagCreateCommand(friends);
        TagCreateCommand createColleaguesCommand = new TagCreateCommand(colleagues);

        // same object -> returns true
        assertTrue(createFriendsCommand.equals(createFriendsCommand));

        // same tag name -> returns true
        TagCreateCommand createFriendsCommandCopy = new TagCreateCommand(friends);
        assertTrue(createFriendsCommand.equals(createFriendsCommandCopy));

        // different types -> returns false
        assertFalse(createFriendsCommand.equals(1));

        // null -> returns false
        assertFalse(createFriendsCommand.equals(null));

        // different tag -> returns false
        assertFalse(createFriendsCommand.equals(createColleaguesCommand));
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
     * A Model stub that contains a single tag.
     */
    private class ModelStubWithTag extends ModelStub {
        private final Tag tag;

        ModelStubWithTag(Tag tag) {
            requireNonNull(tag);
            this.tag = tag;
        }

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return this.tag.equals(tag);
        }
    }

    /**
     * A Model stub that always accepts the tag being added.
     */
    private class ModelStubAcceptingTagAdded extends ModelStub {
        private final CLinkedin cLinkedin = new CLinkedin();

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return cLinkedin.hasTag(tag);
        }

        @Override
        public void addTag(Tag tag) {
            requireNonNull(tag);
            cLinkedin.addTag(tag);
        }

        @Override
        public ReadOnlyCLinkedin getCLinkedin() {
            return cLinkedin;
        }
    }
}
