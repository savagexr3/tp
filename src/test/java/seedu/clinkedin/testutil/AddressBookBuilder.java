package seedu.clinkedin.testutil;

import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private CLinkedin cLinkedin;

    public AddressBookBuilder() {
        cLinkedin = new CLinkedin();
    }

    public AddressBookBuilder(CLinkedin cLinkedin) {
        this.cLinkedin = cLinkedin;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        cLinkedin.addPerson(person);
        return this;
    }

    public CLinkedin build() {
        return cLinkedin;
    }
}
