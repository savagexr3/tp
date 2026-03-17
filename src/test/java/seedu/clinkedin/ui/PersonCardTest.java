package seedu.clinkedin.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
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
    public void openLink_validUrl_parsesSuccessfully() {
        boolean result = false;
        try {
            new java.net.URI("https://linkedin.com/in/johndoe");
            result = true;
        } catch (Exception e) {
            result = false;
        }
        assertTrue(result);
    }

    @Test
    public void openLink_malformedUrl_throwsException() {
        boolean result = true;
        try {
            new java.net.URI("not a valid uri with spaces");
            result = true;
        } catch (Exception e) {
            result = false;
        }
        assertFalse(result);
    }
}
