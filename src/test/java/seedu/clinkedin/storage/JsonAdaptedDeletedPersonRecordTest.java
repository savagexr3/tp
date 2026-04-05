package seedu.clinkedin.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinkedin.storage.JsonAdaptedDeletedPersonRecord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.model.person.DeletedPersonRecord;

//@@author rxlee04
public class JsonAdaptedDeletedPersonRecordTest {

    @Test
    public void toModelType_validRecord_returnsDeletedPersonRecord() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2026, 3, 22, 10, 30);
        DeletedPersonRecord record = new DeletedPersonRecord(ALICE, dateTime);

        JsonAdaptedDeletedPersonRecord adapted =
                new JsonAdaptedDeletedPersonRecord(record);

        assertEquals(record, adapted.toModelType());
    }

    @Test
    public void toModelType_nullPerson_throwsIllegalValueException() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 3, 22, 10, 30);

        JsonAdaptedDeletedPersonRecord adapted =
                new JsonAdaptedDeletedPersonRecord(null, dateTime.toString());

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "person");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullDeletedDateTime_throwsIllegalValueException() {
        JsonAdaptedDeletedPersonRecord adapted =
                new JsonAdaptedDeletedPersonRecord(new JsonAdaptedPerson(ALICE), null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "deletedDateTime");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }
}
