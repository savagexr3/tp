package seedu.clinkedin.model.person;

import static seedu.clinkedin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Company company;
    private final Address address;
    private final Remark remark;
    private final Link link;
    private final DateAdded dateAdded;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Name, phone, email, address and tags must be present and not null.
     * Link is optional and may be absent.
     */
    public Person(Name name, Phone phone, Email email, Optional<Company> company,
                  Address address, Optional<Remark> remark, Optional<Link> link, DateAdded dateAdded, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, dateAdded, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company.orElse(null);
        this.address = address;
        this.remark = remark.orElse(null);
        this.link = link.orElse(null);
        this.dateAdded = dateAdded;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Company getCompany() {
        return company;
    }

    public Address getAddress() {
        return address;
    }

    public Remark getRemark() {
        return remark;
    }
    /**
     * Returns the link, or null if not provided.
     */
    public Link getLink() {
        return link;
    }

    public DateAdded getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }
    /**
     * Returns a new {@code Person} with the specified tag removed.
     * The original person remains unchanged.
     *
     * @param tagToRemove The tag to be removed.
     * @return A new person without the specified tag.
     */
    public Person removeTag(Tag tagToRemove) {
        requireAllNonNull(tagToRemove);

        Set<Tag> updatedTags = new HashSet<>(tags);
        updatedTags.remove(tagToRemove);

        return new Person(name, phone, email, Optional.ofNullable(company), address, Optional.ofNullable(remark),
                Optional.ofNullable(link), dateAdded, updatedTags);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && Objects.equals(company, otherPerson.company)
                && address.equals(otherPerson.address)
                && Objects.equals(remark, otherPerson.remark)
                && Objects.equals(link, otherPerson.link)
                && dateAdded.equals(otherPerson.dateAdded)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, company, address, remark, link, dateAdded, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("company", company)
                .add("address", address)
                .add("remark", remark)
                .add("link", link)
                .add("dateAdded", dateAdded)
                .add("tags", tags)
                .toString();
    }

}
