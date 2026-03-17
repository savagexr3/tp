package seedu.clinkedin.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsCorrectNumberOfPersons() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        assertEquals(6, persons.length);
    }

    @Test
    public void getSamplePersons_personsWithLink_haveLinkSet() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        // Alex, Bernice, Charlotte, Roy have links
        assertNotNull(persons[0].getLink());
        assertNotNull(persons[1].getLink());
        assertNotNull(persons[2].getLink());
        assertNotNull(persons[5].getLink());
    }

    @Test
    public void getSamplePersons_personsWithoutLink_haveLinkNull() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        // David and Irfan have no link
        assertNull(persons[3].getLink());
        assertNull(persons[4].getLink());
    }

    @Test
    public void getSamplePersons_allPersonsHaveRequiredFields() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        for (Person person : persons) {
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getAddress());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSampleAddressBook_returnsNonNull() {
        ReadOnlyCLinkedin addressBook = SampleDataUtil.getSampleAddressBook();
        assertNotNull(addressBook);
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyCLinkedin addressBook = SampleDataUtil.getSampleAddressBook();
        assertEquals(SampleDataUtil.getSamplePersons().length, addressBook.getPersonList().size());
    }

    @Test
    public void getTagSet_returnsCorrectTags() {
        assertEquals(1, SampleDataUtil.getTagSet("friends").size());
        assertEquals(2, SampleDataUtil.getTagSet("friends", "colleagues").size());
        assertEquals(0, SampleDataUtil.getTagSet().size());
    }
}
