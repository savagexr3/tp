package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeletedPersonRecordTest {

    @Test
    public void constructorAndGetters() {
        LocalDateTime deletedDateTime = LocalDateTime.of(2026, 3, 22, 10, 30);
        DeletedPersonRecord record = new DeletedPersonRecord(ALICE, deletedDateTime);

        assertEquals(ALICE, record.getPerson());
        assertEquals(deletedDateTime, record.getDeletedDateTime());
    }

    @Test
    public void equals() {
        LocalDateTime deletedDateTime = LocalDateTime.of(2026, 3, 22, 10, 30);
        LocalDateTime differentDateTime = LocalDateTime.of(2026, 3, 23, 10, 30);

        DeletedPersonRecord aliceRecord = new DeletedPersonRecord(ALICE, deletedDateTime);
        DeletedPersonRecord aliceRecordCopy = new DeletedPersonRecord(ALICE, deletedDateTime);
        DeletedPersonRecord bobRecord = new DeletedPersonRecord(BOB, deletedDateTime);
        DeletedPersonRecord aliceDifferentTimeRecord = new DeletedPersonRecord(ALICE, differentDateTime);

        // same values -> returns true
        assertTrue(aliceRecord.equals(aliceRecordCopy));

        // same object -> returns true
        assertTrue(aliceRecord.equals(aliceRecord));

        // null -> returns false
        assertFalse(aliceRecord.equals(null));

        // different type -> returns false
        assertFalse(aliceRecord.equals(1));

        // different person -> returns false
        assertFalse(aliceRecord.equals(bobRecord));

        // different deletion time -> returns false
        assertFalse(aliceRecord.equals(aliceDifferentTimeRecord));
    }
}
