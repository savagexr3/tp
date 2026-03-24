package seedu.clinkedin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.exceptions.DuplicatePersonException;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.testutil.PersonBuilder;

public class CLinkedinTest {

    private final CLinkedin cLinkedin = new CLinkedin();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), cLinkedin.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cLinkedin.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        CLinkedin newData = getTypicalCLinkedin();
        cLinkedin.resetData(newData);
        assertEquals(newData, cLinkedin);
    }

    @Test
    public void resetData_withValidReadOnlyCLinkedinWithTags_replacesTagData() {
        CLinkedin newData = new CLinkedin();
        Tag friends = new Tag("friends");

        newData.addTag(friends);
        cLinkedin.resetData(newData);

        assertEquals(newData.getTagList(), cLinkedin.getTagList());
    }

    @Test
    public void resetData_withDeletedPersonRecords_replacesDeletedRecords() {
        DeletedPersonRecord record = new DeletedPersonRecord(ALICE);

        CLinkedin newData = new CLinkedin();
        newData.addDeletedPersonRecord(record);

        cLinkedin.resetData(newData);

        assertEquals(newData.getDeletedPersonRecords(),
                cLinkedin.getDeletedPersonRecords());
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        CLinkedinStub newData = new CLinkedinStub(newPersons, Collections.emptyList(), Collections.emptyList());

        assertThrows(DuplicatePersonException.class, () -> cLinkedin.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cLinkedin.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(cLinkedin.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        cLinkedin.addPerson(ALICE);
        assertTrue(cLinkedin.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        cLinkedin.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(cLinkedin.hasPerson(editedAlice));
    }

    // ================= DELETED PERSON RECORD TESTS =================
    @Test
    public void removePerson_personInCLinkedin_removesPersonAndAddsDeletedRecord() {
        cLinkedin.addPerson(ALICE);

        int originalDeletedSize = cLinkedin.getDeletedPersonRecords().size();

        cLinkedin.removePerson(ALICE);

        assertFalse(cLinkedin.getPersonList().contains(ALICE));
        assertEquals(originalDeletedSize + 1, cLinkedin.getDeletedPersonRecords().size());
        assertEquals(ALICE,
                cLinkedin.getDeletedPersonRecords().get(originalDeletedSize).getPerson());
    }

    @Test
    public void removePerson_samePersonTwice_recordsBothDeletions() {
        cLinkedin.addPerson(ALICE);

        // First deletion
        cLinkedin.removePerson(ALICE);

        // Re-add and delete again
        cLinkedin.addPerson(ALICE);
        cLinkedin.removePerson(ALICE);

        // Check: two deleted records exist for the same person
        assertEquals(2, cLinkedin.getDeletedPersonRecords().size());
        assertEquals(ALICE, cLinkedin.getDeletedPersonRecords().get(0).getPerson());
        assertEquals(ALICE, cLinkedin.getDeletedPersonRecords().get(1).getPerson());
    }

    @Test
    public void addDeletedPersonRecord_validRecord_addsRecord() {
        DeletedPersonRecord record = new DeletedPersonRecord(ALICE);

        cLinkedin.addDeletedPersonRecord(record);

        assertTrue(cLinkedin.getDeletedPersonRecords().contains(record));
    }

    @Test
    public void addDeletedPersonRecord_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> cLinkedin.addDeletedPersonRecord(null));
    }

    @Test
    public void getDeletedPersonRecords_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> cLinkedin.getDeletedPersonRecords().remove(0));
    }

    @Test
    public void pruneExpiredDeletedPersonRecords_removesOldRecords() {
        DeletedPersonRecord oldRecord =
                new DeletedPersonRecord(ALICE, LocalDateTime.now().minusDays(8));
        DeletedPersonRecord recentRecord =
                new DeletedPersonRecord(ALICE, LocalDateTime.now());

        cLinkedin.addDeletedPersonRecord(oldRecord);
        cLinkedin.addDeletedPersonRecord(recentRecord);

        cLinkedin.pruneExpiredDeletedPersonRecords();

        assertFalse(cLinkedin.getDeletedPersonRecords().contains(oldRecord));
        assertTrue(cLinkedin.getDeletedPersonRecords().contains(recentRecord));
    }
    // ================= TAG TESTS =================

    @Test
    public void constructor_initialisesWithEmptyTagList() {
        assertEquals(Collections.emptyList(), cLinkedin.getTagList());
    }

    @Test
    public void hasTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cLinkedin.hasTag(null));
    }

    @Test
    public void hasTag_tagNotInCLinkedin_returnsFalse() {
        Tag friends = new Tag("friends");
        assertFalse(cLinkedin.hasTag(friends));
    }

    @Test
    public void hasTag_tagInCLinkedin_returnsTrue() {
        Tag friends = new Tag("friends");
        cLinkedin.addTag(friends);
        assertTrue(cLinkedin.hasTag(friends));
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> cLinkedin.getTagList().remove(0));
    }

    @Test
    public void removeTag_tagInTagList_removesTag() {
        Tag friends = new Tag("friends");
        cLinkedin.addTag(friends);

        cLinkedin.removeTag(friends);

        assertFalse(cLinkedin.hasTag(friends));
    }

    @Test
    public void removeTag_tagLinkedToPersons_removesTagFromAllPersons() {
        Tag friends = new Tag("friends");
        cLinkedin.addTag(friends);
        cLinkedin.addPerson(ALICE);

        cLinkedin.removeTag(friends);

        assertFalse(cLinkedin.hasTag(friends));
        assertFalse(cLinkedin.getPersonList().get(0).getTags().contains(friends));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> cLinkedin.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = CLinkedin.class.getCanonicalName() + "{persons=" + cLinkedin.getPersonList()
                + ", tags=" + cLinkedin.getTagList() + "}";
        assertEquals(expected, cLinkedin.toString());
    }

    /**
     * A stub ReadOnlyCLinkedin whose persons list can violate interface constraints.
     */
    private static class CLinkedinStub implements ReadOnlyCLinkedin {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<DeletedPersonRecord> deletedPersonRecords = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        CLinkedinStub(Collection<Person> persons, List<DeletedPersonRecord> deletedPersonRecords,
                      Collection<Tag> tags) {
            this.persons.setAll(persons);
            this.deletedPersonRecords.addAll(deletedPersonRecords);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<DeletedPersonRecord> getDeletedPersonRecords() {
            return deletedPersonRecords;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
