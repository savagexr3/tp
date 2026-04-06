package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        // EP: empty string
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void getEmailValidationError() {
        // EP: null input
        assertEquals(Email.MESSAGE_NULL, Email.getEmailValidationError(null));

        // EP: empty string
        assertEquals(Email.MESSAGE_EMPTY, Email.getEmailValidationError(""));

        // EP: contains spaces
        assertEquals(Email.MESSAGE_SPACE_NOT_ALLOWED,
                Email.getEmailValidationError("john doe@gmail.com"));

        // EP: invalid '@' count = 0
        assertEquals(Email.MESSAGE_INVALID_AT,
                Email.getEmailValidationError("johndoegmail.com"));

        // EP: invalid '@' count = 2
        assertEquals(Email.MESSAGE_INVALID_AT,
                Email.getEmailValidationError("john@@gmail.com"));

        // EP: missing local part
        assertEquals(Email.MESSAGE_INVALID_LOCAL_PART,
                Email.getEmailValidationError("@gmail.com"));

        // EP: local part starts with invalid special character
        assertEquals(Email.MESSAGE_INVALID_LOCAL_PART,
                Email.getEmailValidationError(".john@gmail.com"));

        // EP: local part ends with invalid special character
        assertEquals(Email.MESSAGE_INVALID_LOCAL_PART,
                Email.getEmailValidationError("john.@gmail.com"));

        // EP: invalid character in local part
        assertEquals(Email.MESSAGE_INVALID_LOCAL_PART,
                Email.getEmailValidationError("john!doe@gmail.com"));

        // EP: missing period in domain
        assertEquals(Email.MESSAGE_INVALID_DOMAIN,
                Email.getEmailValidationError("john@gmailcom"));

        // EP: domain starts with period / empty first label
        assertEquals(Email.MESSAGE_INVALID_DOMAIN,
                Email.getEmailValidationError("john@.domain"));

        // EP: empty first label in domain
        assertEquals(Email.MESSAGE_INVALID_DOMAIN,
                Email.getEmailValidationError("john@.com"));

        // EP: domain ends with period / empty last label
        assertEquals(Email.MESSAGE_INVALID_DOMAIN,
                Email.getEmailValidationError("john@gmail."));

        // EP: domain label starts with hyphen
        assertEquals(Email.MESSAGE_INVALID_DOMAIN,
                Email.getEmailValidationError("john@-gmail.com"));

        // EP: domain label ends with hyphen
        assertEquals(Email.MESSAGE_INVALID_DOMAIN,
                Email.getEmailValidationError("john@gmail-.com"));

        // BVA: top-level domain length = 1, just below minimum valid length 2
        assertEquals(Email.MESSAGE_INVALID_DOMAIN,
                Email.getEmailValidationError("john@gmail.c"));

        // EP: valid simple email
        assertEquals(null, Email.getEmailValidationError("john@gmail.com"));

        // EP: valid alphanumeric email
        assertEquals(null, Email.getEmailValidationError("abc123@test.org"));

        // EP: valid email using allowed special characters in local part and hyphen in domain
        assertEquals(null, Email.getEmailValidationError("john.doe+tag@test-domain.com"));
    }

    @Test
    public void isValidEmail() {
        // EP: null input
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // EP: empty string
        assertFalse(Email.isValidEmail(""));

        // EP: whitespace-only input
        assertFalse(Email.isValidEmail(" "));

        // EP: missing local part
        assertFalse(Email.isValidEmail("@example.com"));

        // EP: missing '@'
        assertFalse(Email.isValidEmail("peterjackexample.com"));

        // EP: missing domain name
        assertFalse(Email.isValidEmail("peterjack@"));

        // EP: invalid domain
        assertFalse(Email.isValidEmail("peterjack@-"));

        // EP: invalid character in domain
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com"));

        // EP: spaces in local part
        assertFalse(Email.isValidEmail("peter jack@example.com"));

        // EP: spaces in domain part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com"));

        // EP: leading space
        assertFalse(Email.isValidEmail(" peterjack@example.com"));

        // EP: trailing space
        assertFalse(Email.isValidEmail("peterjack@example.com "));

        // EP: invalid '@' count = 2
        assertFalse(Email.isValidEmail("peterjack@@example.com"));

        // EP: extra '@' inside local/domain split
        assertFalse(Email.isValidEmail("peter@jack@example.com"));

        // EP: local part starts with invalid special character
        assertFalse(Email.isValidEmail("-peterjack@example.com"));

        // EP: local part ends with invalid special character
        assertFalse(Email.isValidEmail("peterjack-@example.com"));

        // EP: consecutive dots
        assertFalse(Email.isValidEmail("peter..jack@example.com"));

        // EP: '@' appears again inside domain
        assertFalse(Email.isValidEmail("peterjack@example@com"));

        // EP: domain starts with period
        assertFalse(Email.isValidEmail("peterjack@.example.com"));

        // EP: domain ends with period
        assertFalse(Email.isValidEmail("peterjack@example.com."));

        // EP: domain label starts with hyphen
        assertFalse(Email.isValidEmail("peterjack@-example.com"));

        // EP: domain label ends with hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-"));

        // BVA: top-level domain length = 1, just below minimum valid length 2
        assertFalse(Email.isValidEmail("peterjack@example.c"));

        // EP: missing period in domain
        assertFalse(Email.isValidEmail("a@bc"));

        // EP: numeric domain without required period
        assertFalse(Email.isValidEmail("123@145"));

        // EP: valid local part with underscore
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));

        // EP: valid local part with period
        assertTrue(Email.isValidEmail("PeterJack.1190@example.com"));

        // EP: valid local part with plus
        assertTrue(Email.isValidEmail("PeterJack+1190@example.com"));

        // EP: valid local part with hyphen inside
        assertTrue(Email.isValidEmail("PeterJack-1190@example.com"));

        // EP: valid simple email
        assertTrue(Email.isValidEmail("test@localhost.com"));

        // EP: valid mixture of allowed characters
        assertTrue(Email.isValidEmail("a1+be.d@example1.com"));

        // EP: valid long domain
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com"));

        // EP: valid long local part
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com"));

        // EP: valid domain with multiple labels
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu"));
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email.sg");

        // Same values
        assertTrue(email.equals(new Email("valid@email.sg")));

        // Same object
        assertTrue(email.equals(email));

        // Null comparison
        assertFalse(email.equals(null));

        // Different type
        assertFalse(email.equals(5.0f));

        // Different value
        assertFalse(email.equals(new Email("other.valid@email.sg")));
    }
}
