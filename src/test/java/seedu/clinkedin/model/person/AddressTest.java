package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new Address(""));

        // EP: whitespace-only input
        assertThrows(IllegalArgumentException.class, () -> new Address("   "));

        // EP: invalid spacing with multiple consecutive spaces
        assertThrows(IllegalArgumentException.class, () -> new Address("Pasir  Ris"));

        // EP: invalid character '/'
        assertThrows(IllegalArgumentException.class, () -> new Address("Pasir/Ris"));

        // EP: invalid character '@'
        assertThrows(IllegalArgumentException.class, () -> new Address("Pasir Ris@"));

        // BVA: length = 101, just above max valid length 100
        assertThrows(IllegalArgumentException.class, () -> new Address("a".repeat(101)));
    }

    @Test
    public void getAddressValidationError_invalidAddress_returnsCorrectMessage() {
        // EP: empty string
        assertEquals(Address.MESSAGE_EMPTY, Address.getAddressValidationError(""));

        // EP: whitespace-only input
        assertEquals(Address.MESSAGE_EMPTY, Address.getAddressValidationError("   "));

        // EP: invalid spacing with multiple consecutive spaces
        assertEquals(Address.MESSAGE_INVALID_SPACING, Address.getAddressValidationError("Pasir  Ris"));

        // EP: invalid character '/'
        assertEquals(Address.MESSAGE_INVALID_CHARACTERS,
                Address.getAddressValidationError("Pasir/Ris"));

        // EP: invalid character '@'
        assertEquals(Address.MESSAGE_INVALID_CHARACTERS,
                Address.getAddressValidationError("Pasir Ris@"));

        // BVA: length = 101, just above max valid length 100
        assertEquals(Address.MESSAGE_TOO_LONG, Address.getAddressValidationError("a".repeat(101)));
    }

    @Test
    public void isValidAddress() {
        // EP: empty string
        assertFalse(Address.isValidAddress(""));

        // EP: whitespace-only input
        assertFalse(Address.isValidAddress("   "));

        // EP: invalid spacing with multiple consecutive spaces
        assertFalse(Address.isValidAddress("Pasir  Ris"));

        // EP: invalid character '/'
        assertFalse(Address.isValidAddress("Pasir/Ris"));

        // EP: invalid character '@'
        assertFalse(Address.isValidAddress("Pasir Ris@"));

        // BVA: length = 101, just above max valid length 100
        assertFalse(Address.isValidAddress("a".repeat(101)));

        // EP: valid typical address
        assertTrue(Address.isValidAddress("Pasir Ris Drive 12"));

        // EP: valid address containing punctuation allowed by current implementation
        assertTrue(Address.isValidAddress("311, Clementi Ave 2, #02-25"));

        // EP: valid alphanumeric address
        assertTrue(Address.isValidAddress("Blk 123 Tampines Street 11"));

        // BVA: length = 100, max valid length
        assertTrue(Address.isValidAddress("A".repeat(100)));
    }

    @Test
    public void equals() {
        Address firstAddress = new Address("Pasir Ris Drive 12");
        Address secondAddress = new Address("Pasir Ris Drive 12");
        Address thirdAddress = new Address("Jurong West Ave 1");

        // Same object
        assertTrue(firstAddress.equals(firstAddress));

        // Same value
        assertTrue(firstAddress.equals(secondAddress));

        // Different value
        assertFalse(firstAddress.equals(thirdAddress));

        // Different type
        assertFalse(firstAddress.equals(1));

        // Null comparison
        assertFalse(firstAddress.equals(null));
    }

    @Test
    public void toStringMethod() {
        Address address = new Address("Pasir Ris Drive 12");

        // toString should expose the stored address value
        assertEquals("Pasir Ris Drive 12", address.toString());
    }

    @Test
    public void hashCodeMethod() {
        Address firstAddress = new Address("Pasir Ris Drive 12");
        Address secondAddress = new Address("Pasir Ris Drive 12");

        // Equal objects should produce equal hash codes
        assertEquals(firstAddress.hashCode(), secondAddress.hashCode());
    }
}
