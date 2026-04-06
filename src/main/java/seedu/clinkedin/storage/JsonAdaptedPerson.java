package seedu.clinkedin.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.DateAdded;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.person.Remark;
import seedu.clinkedin.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String company;
    private final String address;
    private final String remark;
    private final String link;
    private final String dateAdded;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("company") String company,
            @JsonProperty("address") String address, @JsonProperty("remark") String remark,
                             @JsonProperty("link") String link, @JsonProperty("dateAdded") String dateAdded,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.address = address;
        this.remark = remark;
        this.link = link;
        this.dateAdded = dateAdded;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     *
     * @throws NullPointerException if {@code source} is null.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        company = source.getCompany() != null ? source.getCompany().value : null;
        address = source.getAddress().value;
        remark = source.getRemark() != null ? source.getRemark().value : null;
        link = source.getLink() != null ? source.getLink().value : null;
        dateAdded = source.getDateAdded().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        // Convert all tags from JSON-adapted form to model type
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        // ---------- Name ----------
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        // validate name constraints (length, characters, spacing)
        String nameError = Name.getNameValidationError(name);
        if (nameError != null) {
            throw new IllegalValueException(nameError);
        }
        final Name modelName = new Name(name);

        // ---------- Phone ----------
        if (phone == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        // validate phone constraints (digits only, valid length)
        String phoneError = Phone.getPhoneValidationError(phone);
        if (phoneError != null) {
            throw new IllegalValueException(phoneError);
        }
        final Phone modelPhone = new Phone(phone);

        // ---------- Email ----------
        if (email == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        // validate email format (must contain '@' and domain)
        String emailError = Email.getEmailValidationError(email);
        if (emailError != null) {
            throw new IllegalValueException(emailError);
        }
        final Email modelEmail = new Email(email);

        // ---------- Company (Optional Field) ----------
        Optional<Company> modelCompany;
        if (company == null) {
            modelCompany = Optional.empty();
        } else {
            // validate company constraints (allowed characters, length)
            String companyError = Company.getCompanyNameValidationError(company);
            if (companyError != null) {
                throw new IllegalValueException(companyError);
            }
            modelCompany = Optional.of(new Company(company));
        }

        // ---------- Address ----------
        if (address == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        // validate address constraints (non-empty, length limit)
        String addressError = Address.getAddressValidationError(address);
        if (addressError != null) {
            throw new IllegalValueException(addressError);
        }
        final Address modelAddress = new Address(address);

        // ---------- Remark (Optional Field) ----------
        Optional<Remark> modelRemark;
        if (remark == null) {
            modelRemark = Optional.empty();
        } else {
            // validate remark constraints
            String remarkError = Remark.getRemarkValidationError(remark);
            if (remarkError != null) {
                throw new IllegalValueException(remarkError);
            }
            modelRemark = Optional.of(new Remark(remark));
        }

        // ---------- Link (Optional Field) ----------
        Optional<Link> modelLink = (link == null)
                ? Optional.empty()
                : Optional.of(new Link(link));

        // ---------- DateAdded ----------
        if (dateAdded == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateAdded.class.getSimpleName()));
        }

        if (!DateAdded.isValidDate(dateAdded)) {
            throw new IllegalValueException(DateAdded.MESSAGE_CONSTRAINTS);
        }
        final DateAdded modelDateAdded = new DateAdded(dateAdded);

        // Convert list of tags to set (ensures uniqueness)
        final Set<Tag> modelTags = new HashSet<>(personTags);

        return new Person(modelName, modelPhone, modelEmail,
                modelCompany, modelAddress, modelRemark,
                modelLink, modelDateAdded, modelTags);
    }

}
