package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        // EP: empty string
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void getNameValidationError() {
        // EP: null input
        assertEquals(Name.MESSAGE_NULL, Name.getNameValidationError(null));

        // EP: empty string
        assertEquals(Name.MESSAGE_EMPTY, Name.getNameValidationError(""));

        // BVA: length = 101, just above max valid length 100
        assertEquals(Name.MESSAGE_TOO_LONG, Name.getNameValidationError("a".repeat(101)));

        // BVA: length = 100, max valid length
        assertEquals(null, Name.getNameValidationError("a".repeat(100)));

        // BVA: length = 1, min valid length
        assertEquals(null, Name.getNameValidationError("a"));

        // EP: leading spaces
        assertEquals(Name.MESSAGE_INVALID_SPACING, Name.getNameValidationError("  Bob"));

        // EP: trailing spaces
        assertEquals(Name.MESSAGE_INVALID_SPACING, Name.getNameValidationError("Bob  "));

        // EP: multiple consecutive spaces inside
        assertEquals(Name.MESSAGE_INVALID_SPACING, Name.getNameValidationError("Bob  Tan"));

        // EP: invalid characters
        assertEquals(Name.MESSAGE_INVALID_CHARACTERS, Name.getNameValidationError("James123*"));

        // EP: valid typical name
        assertEquals(null, Name.getNameValidationError("Bob Tan"));
    }

    @Test
    public void isValidName() {
        // EP: null input
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // EP: empty string
        assertFalse(Name.isValidName(""));

        // EP: whitespace-only input
        assertFalse(Name.isValidName(" "));

        // EP: only invalid symbol characters
        assertFalse(Name.isValidName("^"));

        // EP: invalid symbol mixed into name
        assertFalse(Name.isValidName("peter*"));

        // EP: digits only
        assertFalse(Name.isValidName("123"));

        // EP: alphanumeric name not allowed by spec
        assertFalse(Name.isValidName("peter the 2nd"));

        // BVA: length = 101, just above max valid length 100
        assertFalse(Name.isValidName("a".repeat(101)));

        // BVA: length = 100, max valid length
        assertTrue(Name.isValidName("a".repeat(100)));

        // BVA: length = 1, min valid length
        assertTrue(Name.isValidName("a"));

        // EP: valid lowercase multi-word name
        assertTrue(Name.isValidName("peter jack"));

        // EP: valid capitalized name
        assertTrue(Name.isValidName("Capital Tan"));

        // EP: valid long name within boundary
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr Robin"));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // Same values
        assertTrue(name.equals(new Name("Valid Name")));

        // Same object
        assertTrue(name.equals(name));

        // Null comparison
        assertFalse(name.equals(null));

        // Different type
        assertFalse(name.equals(5.0f));

        // Different value
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
