package seedu.clinkedin.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.commons.exceptions.DataLoadingException;
import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.commons.util.FileUtil;
import seedu.clinkedin.commons.util.JsonUtil;
import seedu.clinkedin.model.ReadOnlyCLinkedin;

/**
 * A class to access Clinkedin data stored as a json file on the hard disk.
 */
public class JsonCLinkedinStorage implements CLinkedinStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCLinkedinStorage.class);

    private Path filePath;

    public JsonCLinkedinStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCLinkedinFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCLinkedin> readCLinkedin() throws DataLoadingException {
        return readCLinkedin(filePath);
    }

    /**
     * Similar to {@link #readCLinkedin()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyCLinkedin> readCLinkedin(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableCLinkedin> jsonCLinkedin = JsonUtil.readJsonFile(
                filePath, JsonSerializableCLinkedin.class);
        if (!jsonCLinkedin.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCLinkedin.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveCLinkedin(ReadOnlyCLinkedin cLinkedin) throws IOException {
        saveCLinkedin(cLinkedin, filePath);
    }

    /**
     * Similar to {@link #saveCLinkedin(ReadOnlyCLinkedin)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCLinkedin(ReadOnlyCLinkedin cLinkedin, Path filePath) throws IOException {
        requireNonNull(cLinkedin);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCLinkedin(cLinkedin), filePath);
    }

}
