package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        // EP: empty string
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void getPhoneValidationError() {
        // EP: null input
        assertEquals(Phone.MESSAGE_NULL, Phone.getPhoneValidationError(null));

        // EP: empty string
        assertEquals(Phone.MESSAGE_EMPTY, Phone.getPhoneValidationError(""));

        // EP: non-digit alphabetic input
        assertEquals(Phone.MESSAGE_NON_DIGIT, Phone.getPhoneValidationError("phone"));

        // EP: alphanumeric input
        assertEquals(Phone.MESSAGE_NON_DIGIT, Phone.getPhoneValidationError("9011p041"));

        // EP: spaces within number
        assertEquals(Phone.MESSAGE_NON_DIGIT, Phone.getPhoneValidationError("9312 1534"));

        // BVA: length = 7, just below minimum valid length 8
        assertEquals(Phone.MESSAGE_TOO_SHORT, Phone.getPhoneValidationError("1234567"));

        // BVA: length = 16, just above maximum valid length 15
        assertEquals(Phone.MESSAGE_TOO_LONG,
                Phone.getPhoneValidationError("1234567890123456"));

        // BVA: length = 8, minimum valid length
        assertEquals(null, Phone.getPhoneValidationError("12345678"));

        // EP: valid typical phone number
        assertEquals(null, Phone.getPhoneValidationError("93121534"));

        // BVA: length = 15, maximum valid length
        assertEquals(null, Phone.getPhoneValidationError("123456789012345"));
    }

    @Test
    public void isValidPhone() {
        // EP: null input
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // EP: empty string
        assertFalse(Phone.isValidPhone(""));

        // EP: whitespace-only input
        assertFalse(Phone.isValidPhone(" "));

        // BVA: length = 7, just below minimum valid length 8
        assertFalse(Phone.isValidPhone("1234567"));

        // EP: alphabetic input
        assertFalse(Phone.isValidPhone("phone"));

        // EP: alphanumeric input
        assertFalse(Phone.isValidPhone("9011p041"));

        // EP: spaces within number
        assertFalse(Phone.isValidPhone("9312 1534"));

        // BVA: length = 16, just above maximum valid length 15
        assertFalse(Phone.isValidPhone("1234567890123456"));

        // BVA: length = 8, minimum valid length
        assertTrue(Phone.isValidPhone("91112356"));

        // EP: valid typical 8-digit number
        assertTrue(Phone.isValidPhone("93121534"));

        // BVA: length = 15, maximum valid length
        assertTrue(Phone.isValidPhone("124293842033123"));
    }

    @Test
    public void equals() {
        Phone phone = new Phone("91829354");

        // Same values
        assertTrue(phone.equals(new Phone("91829354")));

        // Same object
        assertTrue(phone.equals(phone));

        // Null comparison
        assertFalse(phone.equals(null));

        // Different type
        assertFalse(phone.equals(5.0f));

        // Different value
        assertFalse(phone.equals(new Phone("99574534")));
    }
}
