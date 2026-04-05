package seedu.clinkedin.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.commons.util.JsonUtil;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.testutil.TypicalPersons;

public class JsonSerializableCLinkedinTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableCLinkedin dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableCLinkedin.class).get();
        CLinkedin cLinkedinFromFile = dataFromFile.toModelType();
        CLinkedin typicalPersonsCLinkedin = TypicalPersons.getTypicalCLinkedin();
        assertEquals(cLinkedinFromFile, typicalPersonsCLinkedin);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCLinkedin dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableCLinkedin.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableCLinkedin dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableCLinkedin.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCLinkedin.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    //@@author rxlee04
    @Test
    public void toModelType_withDeletedPersonRecords_success() throws Exception {
        CLinkedin cLinkedin = new CLinkedin();
        cLinkedin.addPerson(TypicalPersons.ALICE);
        cLinkedin.removePerson(TypicalPersons.ALICE);

        JsonSerializableCLinkedin json = new JsonSerializableCLinkedin(cLinkedin);
        CLinkedin result = json.toModelType();

        assertEquals(cLinkedin.getDeletedPersonRecords(),
                result.getDeletedPersonRecords());
    }

    @Test
    public void constructor_nullDeletedPersonRecords_handlesGracefully() throws Exception {
        JsonSerializableCLinkedin json =
                new JsonSerializableCLinkedin(null, null, null);

        CLinkedin result = json.toModelType();

        assertEquals(0, result.getDeletedPersonRecords().size());
    }

    @Test
    public void toModelType_prunesExpiredDeletedRecords() throws Exception {
        CLinkedin cLinkedin = new CLinkedin();

        // old record (>7 days)
        cLinkedin.addDeletedPersonRecord(
                new DeletedPersonRecord(TypicalPersons.ALICE,
                        LocalDateTime.now().minusDays(8)));

        // recent record
        cLinkedin.addDeletedPersonRecord(
                new DeletedPersonRecord(TypicalPersons.ALICE,
                        LocalDateTime.now()));

        JsonSerializableCLinkedin json = new JsonSerializableCLinkedin(cLinkedin);
        CLinkedin result = json.toModelType();

        assertEquals(1, result.getDeletedPersonRecords().size());
    }
}
