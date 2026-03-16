package seedu.clinkedin.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.HOON;
import static seedu.clinkedin.testutil.TypicalPersons.IDA;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.clinkedin.commons.exceptions.DataLoadingException;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.ReadOnlyCLinkedin;

public class JsonCLinkedinStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCLinkedin_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCLinkedin(null));
    }

    private java.util.Optional<ReadOnlyCLinkedin> readCLinkedin(String filePath) throws Exception {
        return new JsonCLinkedinStorage(Paths.get(filePath)).readCLinkedin(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCLinkedin("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readCLinkedin("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonCLinkedin_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCLinkedin("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonCLinkedin_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCLinkedin("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveCLinkedin_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        CLinkedin original = getTypicalCLinkedin();
        JsonCLinkedinStorage jsonCLinkedinStorage = new JsonCLinkedinStorage(filePath);

        // Save in new file and read back
        jsonCLinkedinStorage.saveCLinkedin(original, filePath);
        ReadOnlyCLinkedin readBack = jsonCLinkedinStorage.readCLinkedin(filePath).get();
        assertEquals(original, new CLinkedin(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonCLinkedinStorage.saveCLinkedin(original, filePath);
        readBack = jsonCLinkedinStorage.readCLinkedin(filePath).get();
        assertEquals(original, new CLinkedin(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonCLinkedinStorage.saveCLinkedin(original); // file path not specified
        readBack = jsonCLinkedinStorage.readCLinkedin().get(); // file path not specified
        assertEquals(original, new CLinkedin(readBack));

    }

    @Test
    public void saveAddressBook_nullCLinkedin_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCLinkedin(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveCLinkedin(ReadOnlyCLinkedin addressBook, String filePath) {
        try {
            new JsonCLinkedinStorage(Paths.get(filePath))
                    .saveCLinkedin(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCLinkedin_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCLinkedin(new CLinkedin(), null));
    }
}
