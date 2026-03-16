package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void getPhoneValidationError() {

        // null
        assertEquals(Phone.MESSAGE_NULL, Phone.getPhoneValidationError(null));

        // empty
        assertEquals(Phone.MESSAGE_EMPTY, Phone.getPhoneValidationError(""));

        // non digit
        assertEquals(Phone.MESSAGE_NON_DIGIT, Phone.getPhoneValidationError("phone"));
        assertEquals(Phone.MESSAGE_NON_DIGIT, Phone.getPhoneValidationError("9011p041"));
        assertEquals(Phone.MESSAGE_NON_DIGIT, Phone.getPhoneValidationError("9312 1534"));

        // too short
        assertEquals(Phone.MESSAGE_TOO_SHORT, Phone.getPhoneValidationError("1234567"));

        // too long
        assertEquals(Phone.MESSAGE_TOO_LONG,
                Phone.getPhoneValidationError("1234567890123456"));

        // valid numbers
        assertEquals(null, Phone.getPhoneValidationError("12345678"));
        assertEquals(null, Phone.getPhoneValidationError("93121534"));
        assertEquals(null, Phone.getPhoneValidationError("123456789012345"));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("91112356")); // exactly 9 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void equals() {
        Phone phone = new Phone("91829354");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("91829354")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("99574534")));
    }
}
