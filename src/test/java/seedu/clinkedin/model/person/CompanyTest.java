package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new Company(""));
    }

    @Test
    public void constructor_validCompany_success() {
        // EP: valid typical company name
        Company company = new Company("Google");
        assertEquals("Google", company.value);
    }

    @Test
    public void getCompanyNameValidationError() {
        // EP: null input
        assertEquals(Company.MESSAGE_NULL, Company.getCompanyNameValidationError(null));

        // EP: empty string
        assertEquals(Company.MESSAGE_EMPTY, Company.getCompanyNameValidationError(""));

        // BVA: length = 51, just above max valid length 50
        assertEquals(Company.MESSAGE_TOO_LONG,
                Company.getCompanyNameValidationError("a".repeat(51)));

        // BVA: length = 50, max valid length
        assertEquals(null,
                Company.getCompanyNameValidationError("a".repeat(50)));

        // BVA: length = 49, just below max boundary
        assertEquals(null,
                Company.getCompanyNameValidationError("a".repeat(49)));

        // EP: leading space
        assertEquals(Company.MESSAGE_INVALID_SPACING,
                Company.getCompanyNameValidationError(" Google"));

        // EP: trailing space
        assertEquals(Company.MESSAGE_INVALID_SPACING,
                Company.getCompanyNameValidationError("Google "));

        // EP: multiple consecutive spaces
        assertEquals(Company.MESSAGE_INVALID_SPACING,
                Company.getCompanyNameValidationError("Google  Singapore"));

        // EP: invalid symbol not allowed by regex
        assertEquals(Company.MESSAGE_INVALID_CHARACTERS,
                Company.getCompanyNameValidationError("Google!"));

        // EP: invalid symbol not allowed by regex
        assertEquals(Company.MESSAGE_INVALID_CHARACTERS,
                Company.getCompanyNameValidationError("Meta@"));

        // EP: valid simple name
        assertEquals(null, Company.getCompanyNameValidationError("Google"));

        // EP: valid multi-word company name
        assertEquals(null, Company.getCompanyNameValidationError("Google Singapore"));

        // EP: valid alphanumeric company name
        assertEquals(null, Company.getCompanyNameValidationError("A1 Holdings"));

        // EP: valid ampersand usage
        assertEquals(null, Company.getCompanyNameValidationError("Procter & Gamble"));

        // EP: valid hyphen usage
        assertEquals(null, Company.getCompanyNameValidationError("Dell-EMC"));

        // EP: valid comma and full stop usage
        assertEquals(null, Company.getCompanyNameValidationError("Company, Inc."));
    }

    @Test
    public void isValidCompanyName() {
        // EP: null input
        assertThrows(NullPointerException.class, () -> Company.isValidCompanyName(null));

        // EP: empty string
        assertFalse(Company.isValidCompanyName(""));

        // EP: whitespace-only input
        assertFalse(Company.isValidCompanyName(" "));

        // EP: leading space
        assertFalse(Company.isValidCompanyName(" Google"));

        // EP: trailing space
        assertFalse(Company.isValidCompanyName("Google "));

        // EP: multiple consecutive spaces
        assertFalse(Company.isValidCompanyName("Google  Singapore"));

        // EP: invalid symbol
        assertFalse(Company.isValidCompanyName("Google!"));

        // EP: invalid symbol
        assertFalse(Company.isValidCompanyName("Meta@"));

        // BVA: length = 51, just above max valid length 50
        assertFalse(Company.isValidCompanyName("a".repeat(51)));

        // BVA: length = 50, max valid length
        assertTrue(Company.isValidCompanyName("a".repeat(50)));

        // EP: valid simple name
        assertTrue(Company.isValidCompanyName("Google"));

        // EP: valid multi-word company name
        assertTrue(Company.isValidCompanyName("Google Singapore"));

        // EP: valid alphanumeric company name
        assertTrue(Company.isValidCompanyName("A1 Holdings"));

        // EP: valid ampersand usage
        assertTrue(Company.isValidCompanyName("Procter & Gamble"));

        // EP: valid hyphen usage
        assertTrue(Company.isValidCompanyName("Dell-EMC"));

        // EP: valid comma and full stop usage
        assertTrue(Company.isValidCompanyName("Company, Inc."));
    }

    @Test
    public void equals() {
        Company company = new Company("Valid Company");

        // Same values
        assertTrue(company.equals(new Company("Valid Company")));

        // Same object
        assertTrue(company.equals(company));

        // Null comparison
        assertFalse(company.equals(null));

        // Different type
        assertFalse(company.equals(5.0f));

        // Different value
        assertFalse(company.equals(new Company("Other Valid Company")));
    }
}
