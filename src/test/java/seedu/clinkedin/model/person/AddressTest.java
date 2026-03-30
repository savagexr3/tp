package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(""));
        assertThrows(IllegalArgumentException.class, () -> new Address("   "));
        assertThrows(IllegalArgumentException.class, () -> new Address("Pasir  Ris"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Pasir/Ris"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Pasir Ris@"));
        assertThrows(IllegalArgumentException.class, () -> new Address("a".repeat(101)));
    }

    @Test
    public void getAddressValidationError_invalidAddress_returnsCorrectMessage() {
        assertEquals(Address.MESSAGE_EMPTY, Address.getAddressValidationError(""));
        assertEquals(Address.MESSAGE_EMPTY, Address.getAddressValidationError("   "));
        assertEquals(Address.MESSAGE_MULTIPLE_SPACES, Address.getAddressValidationError("Pasir  Ris"));
        assertEquals(Address.ADDRESS_CONTAINS_INVALID_CHARACTERS,
                Address.getAddressValidationError("Pasir/Ris"));
        assertEquals(Address.ADDRESS_CONTAINS_INVALID_CHARACTERS,
                Address.getAddressValidationError("Pasir Ris@"));
        assertEquals(Address.MESSAGE_TOO_LONG, Address.getAddressValidationError("a".repeat(101)));
    }

    @Test
    public void isValidAddress() {
        // invalid
        assertFalse(Address.isValidAddress(""));
        assertFalse(Address.isValidAddress("   "));
        assertFalse(Address.isValidAddress("Pasir  Ris"));
        assertFalse(Address.isValidAddress("Pasir/Ris"));
        assertFalse(Address.isValidAddress("Pasir Ris@"));
        assertFalse(Address.isValidAddress("a".repeat(101)));

        // valid
        assertTrue(Address.isValidAddress("Pasir Ris Drive 12"));
        assertTrue(Address.isValidAddress("311, Clementi Ave 2, #02-25"));
        assertTrue(Address.isValidAddress("Blk 123 Tampines Street 11"));
        assertTrue(Address.isValidAddress("A".repeat(100)));
    }

    @Test
    public void equals() {
        Address firstAddress = new Address("Pasir Ris Drive 12");
        Address secondAddress = new Address("Pasir Ris Drive 12");
        Address thirdAddress = new Address("Jurong West Ave 1");

        assertTrue(firstAddress.equals(firstAddress));
        assertTrue(firstAddress.equals(secondAddress));
        assertFalse(firstAddress.equals(thirdAddress));
        assertFalse(firstAddress.equals(1));
        assertFalse(firstAddress.equals(null));
    }

    @Test
    public void toStringMethod() {
        Address address = new Address("Pasir Ris Drive 12");
        assertEquals("Pasir Ris Drive 12", address.toString());
    }

    @Test
    public void hashCodeMethod() {
        Address firstAddress = new Address("Pasir Ris Drive 12");
        Address secondAddress = new Address("Pasir Ris Drive 12");

        assertEquals(firstAddress.hashCode(), secondAddress.hashCode());
    }
}