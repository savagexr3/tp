package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateAddedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateAdded((String) null));
    }

    @Test
    public void constructor_invalidDateAdded_throwsIllegalArgumentException() {
        String invalidDateAdded = "";
        assertThrows(IllegalArgumentException.class, () -> new DateAdded(invalidDateAdded));
    }

    @Test
    public void isValidDate() {
        assertThrows(NullPointerException.class, () -> DateAdded.isValidDate(null));
        assertFalse(DateAdded.isValidDate("2026/03/20"));
        assertFalse(DateAdded.isValidDate("hello"));
        assertTrue(DateAdded.isValidDate("20-03-2026"));
    }

    @Test
    public void equals() {
        DateAdded dateAdded = new DateAdded("20-03-2026");
        assertTrue(dateAdded.equals(new DateAdded("20-03-2026")));
        assertTrue(dateAdded.equals(dateAdded));
        assertFalse(dateAdded.equals(null));
        assertFalse(dateAdded.equals(new DateAdded("21-03-2026")));
    }

    @Test
    public void hashCode_test() {
        DateAdded dateAdded1 = new DateAdded("20-03-2026");
        DateAdded dateAdded2 = new DateAdded("20-03-2026");

        assertEquals(dateAdded1.hashCode(), dateAdded2.hashCode());
    }
}
