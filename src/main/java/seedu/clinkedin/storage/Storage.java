package seedu.clinkedin.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinkedin.commons.exceptions.DataLoadingException;
import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.ReadOnlyUserPrefs;
import seedu.clinkedin.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CLinkedinStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCLinkedinFilePath();

    @Override
    Optional<ReadOnlyCLinkedin> readCLinkedin() throws DataLoadingException;

    @Override
    void saveCLinkedin(ReadOnlyCLinkedin addressBook) throws IOException;

}
