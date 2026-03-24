package seedu.clinkedin.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.DateAdded;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.util.SampleDataUtil;

public class PersonCardTest {

    private Person buildPerson(String link) {
        return new Person(
                new Name("John Doe"),
                new Phone("91234567"),
                new Email("john@example.com"),
                new Company("Google"),
                new Address("123 Street"),
                link != null ? Optional.of(new Link(link)) : Optional.empty(),
                new DateAdded("20-03-2026"),
                SampleDataUtil.getTagSet()
        );
    }

    @Test
    public void hasLink_personWithLink_returnsTrue() {
        Person person = buildPerson("https://linkedin.com/in/johndoe");
        assertTrue(person.getLink() != null);
    }

    @Test
    public void hasLink_personWithoutLink_returnsFalse() {
        Person person = buildPerson(null);
        assertFalse(person.getLink() != null);
    }

    @Test
    public void getLinkValue_personWithLink_returnsCorrectValue() {
        String url = "https://linkedin.com/in/johndoe";
        Person person = buildPerson(url);
        assertEquals(url, person.getLink().value);
    }

    @Test
    public void getLinkValue_personWithoutLink_returnsNull() {
        Person person = buildPerson(null);
        assertNull(person.getLink());
    }

    @Test
    public void linkUtil_validUrl_isValidUri() {
        assertTrue(LinkUtil.isValidUri("https://linkedin.com/in/johndoe"));
    }

    @Test
    public void linkUtil_malformedUrl_isNotValidUri() {
        assertFalse(LinkUtil.isValidUri("not a valid uri with spaces"));
    }

    @Test
    public void linkUtil_httpUrl_isValidUri() {
        assertTrue(LinkUtil.isValidUri("http://example.com"));
    }

    @Test
    public void linkUtil_emptyUrl_isValidUri() {
        assertTrue(LinkUtil.isValidUri(""));
    }

    @Test
    public void linkUtil_personWithLink_hasCorrectValue() {
        String url = "https://linkedin.com/in/johndoe";
        Person person = buildPerson(url);
        String linkValue = person.getLink() != null ? person.getLink().value : null;
        assertEquals(url, linkValue);
    }

    @Test
    public void linkUtil_personWithoutLink_returnsNull() {
        Person person = buildPerson(null);
        String linkValue = person.getLink() != null ? person.getLink().value : null;
        assertNull(linkValue);
    }
}
