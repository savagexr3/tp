package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Remark(""));
        assertThrows(IllegalArgumentException.class, () -> new Remark("hello/world"));
        assertThrows(IllegalArgumentException.class, () -> new Remark("a".repeat(201)));
    }

    @Test
    public void getRemarkValidationError_invalidRemark_returnsCorrectMessage() {
        assertEquals(Remark.MESSAGE_NULL, Remark.getRemarkValidationError(null));
        assertEquals(Remark.MESSAGE_EMPTY, Remark.getRemarkValidationError(""));
        assertEquals("Remark cannot contain '/'.",
                Remark.getRemarkValidationError("hello/world"));
        assertEquals(Remark.MESSAGE_TOO_LONG, Remark.getRemarkValidationError("a".repeat(201)));
    }

    @Test
    public void isValidRemark() {
        // invalid
        assertFalse(Remark.isValidRemark(""));
        assertFalse(Remark.isValidRemark("hello/world"));
        assertFalse(Remark.isValidRemark("a".repeat(201)));

        // valid
        assertTrue(Remark.isValidRemark("Enjoys networking"));
        assertTrue(Remark.isValidRemark("Follow up next week"));
        assertTrue(Remark.isValidRemark("A".repeat(200)));
    }

    @Test
    public void equals() {
        Remark firstRemark = new Remark("Enjoys networking");
        Remark secondRemark = new Remark("Enjoys networking");
        Remark thirdRemark = new Remark("Follow up next week");

        assertTrue(firstRemark.equals(firstRemark));
        assertTrue(firstRemark.equals(secondRemark));
        assertFalse(firstRemark.equals(thirdRemark));
        assertFalse(firstRemark.equals(1));
        assertFalse(firstRemark.equals(null));
    }

    @Test
    public void toStringMethod() {
        Remark remark = new Remark("Enjoys networking");
        assertEquals("Enjoys networking", remark.toString());
    }

    @Test
    public void hashCodeMethod() {
        Remark firstRemark = new Remark("Enjoys networking");
        Remark secondRemark = new Remark("Enjoys networking");

        assertEquals(firstRemark.hashCode(), secondRemark.hashCode());
    }
}