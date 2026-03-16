package seedu.clinkedin.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinkedin.commons.exceptions.DataLoadingException;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.ReadOnlyCLinkedin;

/**
 * Represents a storage for {@link CLinkedin}.
 */
public interface CLinkedinStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCLinkedinFilePath();

    /**
     * Returns CLinkedin data as a {@link ReadOnlyCLinkedin}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyCLinkedin> readCLinkedin() throws DataLoadingException;

    /**
     * @see #getCLinkedinFilePath()
     */
    Optional<ReadOnlyCLinkedin> readCLinkedin(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyCLinkedin} to the storage.
     * @param cLinkedin cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCLinkedin(ReadOnlyCLinkedin cLinkedin) throws IOException;

    /**
     * @see #saveCLinkedin(ReadOnlyCLinkedin)
     */
    void saveCLinkedin(ReadOnlyCLinkedin cLinkedin, Path filePath) throws IOException;

}
