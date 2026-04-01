package seedu.clinkedin.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.commons.exceptions.DataLoadingException;
import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.ReadOnlyUserPrefs;
import seedu.clinkedin.model.UserPrefs;

/**
 * Manages storage of CLinkedin data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CLinkedinStorage cLinkedinStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code CLinkedinStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(CLinkedinStorage cLinkedinStorage, UserPrefsStorage userPrefsStorage) {
        this.cLinkedinStorage = cLinkedinStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CLinkedin methods ==============================

    @Override
    public Path getCLinkedinFilePath() {
        return cLinkedinStorage.getCLinkedinFilePath();
    }

    @Override
    public Optional<ReadOnlyCLinkedin> readCLinkedin() throws DataLoadingException {
        return readCLinkedin(cLinkedinStorage.getCLinkedinFilePath());
    }

    @Override
    public Optional<ReadOnlyCLinkedin> readCLinkedin(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cLinkedinStorage.readCLinkedin(filePath);
    }

    @Override
    public void saveCLinkedin(ReadOnlyCLinkedin cLinkedin) throws IOException {
        saveCLinkedin(cLinkedin, cLinkedinStorage.getCLinkedinFilePath());
    }

    @Override
    public void saveCLinkedin(ReadOnlyCLinkedin cLinkedin, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cLinkedinStorage.saveCLinkedin(cLinkedin, filePath);
    }

}
