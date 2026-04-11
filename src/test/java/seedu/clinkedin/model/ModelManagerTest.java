package seedu.clinkedin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.core.GuiSettings;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.NameContainsKeywordsPredicate;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.testutil.AddressBookBuilder;
import seedu.clinkedin.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new CLinkedin(), new CLinkedin(modelManager.getCLinkedin()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    // ================= DELETED PERSON RECORD TESTS =================
    //@@author rxlee04
    @Test
    public void deletePerson_personInCLinkedin_removesPersonAndAddsDeletedRecord() {
        modelManager.addPerson(ALICE);

        int originalDeletedSize = modelManager.getFilteredDeletedPersonRecordList().size();

        modelManager.deletePerson(ALICE);

        // Check: person removed and deleted record added
        assertFalse(modelManager.getCLinkedin().getPersonList().contains(ALICE));
        assertEquals(originalDeletedSize + 1,
                modelManager.getFilteredDeletedPersonRecordList().size());
        assertEquals(ALICE,
                modelManager.getFilteredDeletedPersonRecordList()
                        .get(originalDeletedSize).getPerson());
    }

    @Test
    public void restorePerson_deletedRecordInCLinkedin_restoresPersonAndRemovesDeletedRecord() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);

        DeletedPersonRecord recordToRestore = modelManager.getFilteredDeletedPersonRecordList().get(0);

        modelManager.restorePerson(recordToRestore);

        assertTrue(modelManager.getCLinkedin().getPersonList().stream()
                .anyMatch(p -> p.isSamePerson(ALICE)));
        assertEquals(0, modelManager.getFilteredDeletedPersonRecordList().size());
    }

    @Test
    public void restorePerson_updatesFilteredLists_showsAllAfterRestore() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);

        DeletedPersonRecord recordToRestore = modelManager.getFilteredDeletedPersonRecordList().get(0);

        modelManager.updateFilteredPersonList(person -> false);
        modelManager.updateFilteredDeletedPersonRecordList(record -> false);

        modelManager.restorePerson(recordToRestore);

        assertTrue(modelManager.getFilteredPersonList().stream()
                .anyMatch(p -> p.isSamePerson(ALICE)));
        assertEquals(0, modelManager.getFilteredDeletedPersonRecordList().size());
    }

    @Test
    public void restorePerson_nullDeletedPersonRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.restorePerson(null));
    }

    @Test
    public void getFilteredDeletedPersonRecordList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> modelManager.getFilteredDeletedPersonRecordList().remove(0));
    }

    @Test
    public void updateFilteredDeletedPersonRecordList_filterApplied_listFiltered() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);

        // filter to show none
        modelManager.updateFilteredDeletedPersonRecordList(record -> false);

        assertEquals(0, modelManager.getFilteredDeletedPersonRecordList().size());
    }

    // ================= TAG TESTS =================
    @Test
    public void hasTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTag(null));
    }

    @Test
    public void hasTag_tagNotInCLinkedin_returnsFalse() {
        Tag friends = new Tag("friends");
        assertFalse(modelManager.hasTag(friends));
    }

    @Test
    public void hasTag_tagInCLinkedin_returnsTrue() {
        Tag friends = new Tag("friends");
        modelManager.addTag(friends);
        assertTrue(modelManager.hasTag(friends));
    }

    @Test
    public void addTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addTag(null));
    }

    @Test
    public void deleteTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteTag(null));
    }

    @Test
    public void deleteTag_existingTag_removesTag() {
        Tag friends = new Tag("friends");
        modelManager.addTag(friends);

        modelManager.deleteTag(friends);

        assertFalse(modelManager.hasTag(friends));
    }

    @Test
    public void deleteTag_tagLinkedToPersons_removesTagFromAllPersons() {
        Tag friends = new Tag("friends");

        modelManager.addTag(friends);
        modelManager.addPerson(ALICE); // already has "friends"

        modelManager.deleteTag(friends);

        assertFalse(modelManager.hasTag(friends));
        assertFalse(modelManager.getCLinkedin().getPersonList().get(0).getTags().contains(friends));
    }

    //@@author
    @Test
    public void equals() {
        CLinkedin cLinkedin = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        CLinkedin differentCLinkedin = new CLinkedin();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(cLinkedin, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(cLinkedin, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCLinkedin, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(cLinkedin, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(cLinkedin, differentUserPrefs)));

        // different filteredDeletedPersonRecords -> returns false
        modelManager.deletePerson(ALICE);
        modelManager.addPerson(ALICE);

        assertFalse(modelManager.equals(new ModelManager(cLinkedin, userPrefs)));
    }

    @Test
    public void sortFilteredPersonListByCompany_sortsDisplayedListOnly() {
        // Integration: displayed list is sorted while underlying address book order stays unchanged
        Person shopeePerson = new PersonBuilder(BENSON).build();
        Person googlePerson = new PersonBuilder(ALICE).build();

        CLinkedin cLinkedin = new AddressBookBuilder()
                .withPerson(shopeePerson)
                .withPerson(googlePerson)
                .build();
        ModelManager sortedModelManager = new ModelManager(cLinkedin, new UserPrefs());

        assertEquals(shopeePerson, sortedModelManager.getCLinkedin().getPersonList().get(0));
        assertEquals(googlePerson, sortedModelManager.getCLinkedin().getPersonList().get(1));

        sortedModelManager.sortFilteredPersonListByCompany();

        assertEquals(googlePerson, sortedModelManager.getFilteredPersonList().get(0));
        assertEquals(shopeePerson, sortedModelManager.getFilteredPersonList().get(1));

        assertEquals(shopeePerson, sortedModelManager.getCLinkedin().getPersonList().get(0));
        assertEquals(googlePerson, sortedModelManager.getCLinkedin().getPersonList().get(1));
    }

    @Test
    public void sortFilteredPersonListByCompany_filteredListRemainsFiltered() {
        // Integration: sorting respects the current filter predicate
        Person shopeePerson = new PersonBuilder(BENSON).build();
        Person googlePerson = new PersonBuilder(ALICE).build();

        CLinkedin cLinkedin = new AddressBookBuilder()
                .withPerson(shopeePerson)
                .withPerson(googlePerson)
                .build();
        ModelManager sortedModelManager = new ModelManager(cLinkedin, new UserPrefs());

        sortedModelManager.updateFilteredPersonList(person -> person.isSamePerson(shopeePerson));
        sortedModelManager.sortFilteredPersonListByCompany();

        assertEquals(1, sortedModelManager.getFilteredPersonList().size());
        assertEquals(shopeePerson, sortedModelManager.getFilteredPersonList().get(0));
    }

    @Test
    public void sortFilteredPersonListByCompany_noCompany_sortsByName() {
        // EP: persons without a company are treated as having the same empty company value
        // and are further sorted by name
        Person ben = new PersonBuilder()
                .withName("Ben Tan")
                .withPhone("93456789")
                .withEmail("ben@example.com")
                .withAddress("Ben Street")
                .build();

        Person alex = new PersonBuilder()
                .withName("Alex Tan")
                .withPhone("91234567")
                .withEmail("alex@example.com")
                .withAddress("Alex Street")
                .build();

        CLinkedin cLinkedin = new AddressBookBuilder()
                .withPerson(ben)
                .withPerson(alex)
                .build();
        ModelManager sortedModelManager = new ModelManager(cLinkedin, new UserPrefs());

        sortedModelManager.sortFilteredPersonListByCompany();

        assertEquals(alex, sortedModelManager.getFilteredPersonList().get(0));
        assertEquals(ben, sortedModelManager.getFilteredPersonList().get(1));
    }

    @Test
    public void sortFilteredPersonListByCompany_sameCompany_sortsByName() {
        // EP: persons with the same company value are sorted by name as a secondary key
        Person beta = new PersonBuilder()
                .withName("Beta Tan")
                .withPhone("92345678")
                .withEmail("beta@example.com")
                .withAddress("Beta Street")
                .withCompany("Google")
                .build();

        Person alpha = new PersonBuilder()
                .withName("Alpha Tan")
                .withPhone("91234567")
                .withEmail("alpha@example.com")
                .withAddress("Alpha Street")
                .withCompany("Google")
                .build();

        CLinkedin cLinkedin = new AddressBookBuilder()
                .withPerson(beta)
                .withPerson(alpha)
                .build();
        ModelManager sortedModelManager = new ModelManager(cLinkedin, new UserPrefs());

        sortedModelManager.sortFilteredPersonListByCompany();

        assertEquals(alpha, sortedModelManager.getFilteredPersonList().get(0));
        assertEquals(beta, sortedModelManager.getFilteredPersonList().get(1));
    }

    @Test
    public void equals_differentSortedPersons_returnsFalse() {
        // EP: sorted view differs between two otherwise equal model managers
        CLinkedin cLinkedin = new AddressBookBuilder().withPerson(BENSON).withPerson(ALICE).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManagerOne = new ModelManager(cLinkedin, userPrefs);
        ModelManager modelManagerTwo = new ModelManager(cLinkedin, userPrefs);

        modelManagerOne.sortFilteredPersonListByCompany();

        assertFalse(modelManagerOne.equals(modelManagerTwo));
    }

    @Test
    public void resetFilteredPersonListSorting_restoresOriginalOrder() {
        // Integration: resetting sorting restores original filtered order
        Person shopeePerson = new PersonBuilder(BENSON).build();
        Person googlePerson = new PersonBuilder(ALICE).build();

        CLinkedin cLinkedin = new AddressBookBuilder()
                .withPerson(shopeePerson)
                .withPerson(googlePerson)
                .build();
        ModelManager modelManager = new ModelManager(cLinkedin, new UserPrefs());

        modelManager.sortFilteredPersonListByCompany();
        assertEquals(googlePerson, modelManager.getFilteredPersonList().get(0));
        assertEquals(shopeePerson, modelManager.getFilteredPersonList().get(1));

        modelManager.resetFilteredPersonListSorting();
        assertEquals(shopeePerson, modelManager.getFilteredPersonList().get(0));
        assertEquals(googlePerson, modelManager.getFilteredPersonList().get(1));
    }
}
