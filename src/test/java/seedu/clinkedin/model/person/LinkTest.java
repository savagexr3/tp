package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Link(null));
    }

    @Test
    public void constructor_invalidLink_throwsIllegalArgumentException() {
        String invalidLink = "";
        assertThrows(IllegalArgumentException.class, () -> new Link(invalidLink));
    }

    @Test
    public void getLinkValidationError() {
        // null
        assertEquals(Link.MESSAGE_NULL, Link.getLinkValidationError(null));

        // empty
        assertEquals(Link.MESSAGE_EMPTY, Link.getLinkValidationError(""));

        // contains spaces
        assertEquals(Link.MESSAGE_SPACE_NOT_ALLOWED, Link.getLinkValidationError("https://linkedin.com/in/john doe"));

        // consecutive dots
        assertEquals(Link.MESSAGE_CONSECUTIVE_DOT, Link.getLinkValidationError("https://linked..in.com"));

        // missing scheme
        assertEquals(Link.MESSAGE_INVALID_SCHEME, Link.getLinkValidationError("linkedin.com/in/johndoe"));
        assertEquals(Link.MESSAGE_INVALID_SCHEME, Link.getLinkValidationError("ftp://linkedin.com"));
        assertEquals(Link.MESSAGE_INVALID_SCHEME, Link.getLinkValidationError("www.linkedin.com"));

        // invalid domain
        assertEquals(Link.MESSAGE_INVALID_DOMAIN, Link.getLinkValidationError("https://"));
        assertEquals(Link.MESSAGE_INVALID_DOMAIN, Link.getLinkValidationError("https://noperiod"));
        assertEquals(Link.MESSAGE_INVALID_DOMAIN, Link.getLinkValidationError("https://.linkedin.com"));
        assertEquals(Link.MESSAGE_INVALID_DOMAIN, Link.getLinkValidationError("https://linkedin.com."));
        assertEquals(Link.MESSAGE_INVALID_DOMAIN, Link.getLinkValidationError("https://linked_in.com"));
        assertEquals(Link.MESSAGE_INVALID_DOMAIN, Link.getLinkValidationError("https://linkedin.c"));

        // valid
        assertEquals(null, Link.getLinkValidationError("https://linkedin.com"));
        assertEquals(null, Link.getLinkValidationError("http://linkedin.com"));
        assertEquals(null, Link.getLinkValidationError("https://linkedin.com/in/johndoe"));
        assertEquals(null, Link.getLinkValidationError("https://www.linkedin.com/in/johndoe"));
        assertEquals(null, Link.getLinkValidationError("https://github.com/johndoe?tab=repos"));
    }

    @Test
    public void isValidLink() {
        // null link
        assertThrows(NullPointerException.class, () -> Link.isValidLink(null));

        // invalid links
        assertFalse(Link.isValidLink(""));
        assertFalse(Link.isValidLink("linkedin.com"));
        assertFalse(Link.isValidLink("ftp://linkedin.com"));
        assertFalse(Link.isValidLink("https://"));
        assertFalse(Link.isValidLink("https://noperiod"));
        assertFalse(Link.isValidLink("https://linked_in.com"));
        assertFalse(Link.isValidLink("https://linkedin.c"));
        assertFalse(Link.isValidLink("https://.linkedin.com"));
        assertFalse(Link.isValidLink("https://linkedin.com."));
        assertFalse(Link.isValidLink("https://linked..in.com"));

        // valid links
        assertTrue(Link.isValidLink("https://linkedin.com"));
        assertTrue(Link.isValidLink("http://linkedin.com"));
        assertTrue(Link.isValidLink("https://linkedin.com/in/johndoe"));
        assertTrue(Link.isValidLink("https://www.linkedin.com/in/johndoe"));
        assertTrue(Link.isValidLink("https://github.com/johndoe?tab=repos"));
        assertTrue(Link.isValidLink("https://my-company.com"));
    }

    @Test
    public void equals() {
        Link link = new Link("https://linkedin.com/in/johndoe");

        // same values -> returns true
        assertTrue(link.equals(new Link("https://linkedin.com/in/johndoe")));

        // same object -> returns true
        assertTrue(link.equals(link));

        // null -> returns false
        assertFalse(link.equals(null));

        // different types -> returns false
        assertFalse(link.equals(5.0f));

        // different values -> returns false
        assertFalse(link.equals(new Link("https://github.com/johndoe")));
    }
}
