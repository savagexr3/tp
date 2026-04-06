package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new Remark(""));

        // EP: invalid character '/'
        assertThrows(IllegalArgumentException.class, () -> new Remark("hello/world"));

        // BVA: length = 201, just above max valid length 200
        assertThrows(IllegalArgumentException.class, () -> new Remark("a".repeat(201)));
    }

    @Test
    public void getRemarkValidationError_invalidRemark_returnsCorrectMessage() {
        // EP: null input
        assertEquals(Remark.MESSAGE_NULL, Remark.getRemarkValidationError(null));

        // EP: empty string
        assertEquals(Remark.MESSAGE_EMPTY, Remark.getRemarkValidationError(""));

        // EP: invalid character '/'
        assertEquals(Remark.MESSAGE_INVALID_CHARACTERS,
                Remark.getRemarkValidationError("hello/world"));

        // BVA: length = 201, just above max valid length 200
        assertEquals(Remark.MESSAGE_TOO_LONG, Remark.getRemarkValidationError("a".repeat(201)));

        // BVA: length = 200, max valid length
        assertEquals(null, Remark.getRemarkValidationError("A".repeat(200)));
    }

    @Test
    public void isValidRemark() {
        // EP: empty string
        assertFalse(Remark.isValidRemark(""));

        // EP: invalid character '/'
        assertFalse(Remark.isValidRemark("hello/world"));

        // BVA: length = 201, just above max valid length 200
        assertFalse(Remark.isValidRemark("a".repeat(201)));

        // EP: valid simple remark
        assertTrue(Remark.isValidRemark("Enjoys networking"));

        // EP: valid sentence-like remark
        assertTrue(Remark.isValidRemark("Follow up next week"));

        // BVA: length = 200, max valid length
        assertTrue(Remark.isValidRemark("A".repeat(200)));
    }

    @Test
    public void equals() {
        Remark firstRemark = new Remark("Enjoys networking");
        Remark secondRemark = new Remark("Enjoys networking");
        Remark thirdRemark = new Remark("Follow up next week");

        // Same object
        assertTrue(firstRemark.equals(firstRemark));

        // Same value
        assertTrue(firstRemark.equals(secondRemark));

        // Different value
        assertFalse(firstRemark.equals(thirdRemark));

        // Different type
        assertFalse(firstRemark.equals(1));

        // Null comparison
        assertFalse(firstRemark.equals(null));
    }

    @Test
    public void toStringMethod() {
        Remark remark = new Remark("Enjoys networking");

        // toString should expose the stored remark value
        assertEquals("Enjoys networking", remark.toString());
    }

    @Test
    public void hashCodeMethod() {
        Remark firstRemark = new Remark("Enjoys networking");
        Remark secondRemark = new Remark("Enjoys networking");

        // Equal objects should produce equal hash codes
        assertEquals(firstRemark.hashCode(), secondRemark.hashCode());
    }
}
