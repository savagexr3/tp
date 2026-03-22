package seedu.clinkedin.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.DateAdded;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_COMPANY = "AWS";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Company company;
    private Address address;
    private Link link; // null if not provided
    private DateAdded dateAdded;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     * Link is not set by default.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        company = new Company(DEFAULT_COMPANY);
        address = new Address(DEFAULT_ADDRESS);
        link = null;
        dateAdded = new DateAdded();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        company = personToCopy.getCompany();
        address = personToCopy.getAddress();
        link = personToCopy.getLink();
        dateAdded = personToCopy.getDateAdded();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code seedu.clinkedin.model.person.Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String companyName) {
        this.company = new Company(companyName);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Link} of the {@code Person} that we are building.
     */
    public PersonBuilder withLink(String link) {
        this.link = new Link(link);
        return this;
    }

    /**
     * Sets the {@code DateAdded} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateAdded(String dateAdded) {
        this.dateAdded = new DateAdded(dateAdded);
        return this;
    }


    public Person build() {
        return new Person(name, phone, email, company, address, Optional.ofNullable(link), dateAdded, tags);
    }

}
