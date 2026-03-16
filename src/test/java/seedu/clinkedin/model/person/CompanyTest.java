package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void getCompanyNameValidationError() {
        // null
        assertEquals(Company.MESSAGE_NULL, Company.getCompanyNameValidationError(null));

        // empty
        assertEquals(Company.MESSAGE_EMPTY, Company.getCompanyNameValidationError(""));

        // too long
        assertEquals(Company.MESSAGE_TOO_LONG,
                Company.getCompanyNameValidationError("a".repeat(51)));

        // invalid spaces
        assertEquals(Company.MESSAGE_MULTIPLE_SPACES,
                Company.getCompanyNameValidationError(" Google"));
        assertEquals(Company.MESSAGE_MULTIPLE_SPACES,
                Company.getCompanyNameValidationError("Google "));
        assertEquals(Company.MESSAGE_MULTIPLE_SPACES,
                Company.getCompanyNameValidationError("Google  Singapore"));

        // invalid characters
        assertEquals(Company.MESSAGE_INVALID_CHARACTERS,
                Company.getCompanyNameValidationError("Google!"));
        assertEquals(Company.MESSAGE_INVALID_CHARACTERS,
                Company.getCompanyNameValidationError("Meta@"));

        // valid
        assertEquals(null, Company.getCompanyNameValidationError("Google"));
        assertEquals(null, Company.getCompanyNameValidationError("Google Singapore"));
        assertEquals(null, Company.getCompanyNameValidationError("A1 Holdings"));
        assertEquals(null, Company.getCompanyNameValidationError("Procter & Gamble"));
        assertEquals(null, Company.getCompanyNameValidationError("Dell-EMC"));
        assertEquals(null, Company.getCompanyNameValidationError("Company, Inc."));
    }

    @Test
    public void isValidCompanyName() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompanyName(null));

        // invalid company
        assertFalse(Company.isValidCompanyName("")); // empty string
        assertFalse(Company.isValidCompanyName(" ")); // spaces only
        assertFalse(Company.isValidCompanyName(" Google")); // leading space
        assertFalse(Company.isValidCompanyName("Google ")); // trailing space
        assertFalse(Company.isValidCompanyName("Google  Singapore")); // multiple spaces
        assertFalse(Company.isValidCompanyName("Google!")); // invalid character
        assertFalse(Company.isValidCompanyName("Meta@")); // invalid character
        assertFalse(Company.isValidCompanyName("a".repeat(51))); // too long

        // valid company
        assertTrue(Company.isValidCompanyName("Google"));
        assertTrue(Company.isValidCompanyName("Google Singapore"));
        assertTrue(Company.isValidCompanyName("A1 Holdings"));
        assertTrue(Company.isValidCompanyName("Procter & Gamble"));
        assertTrue(Company.isValidCompanyName("Dell-EMC"));
        assertTrue(Company.isValidCompanyName("Company, Inc."));
    }

    @Test
    public void equals() {
        Company company = new Company("Valid Company");

        // same values -> returns true
        assertTrue(company.equals(new Company("Valid Company")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Other Valid Company")));
    }
}
