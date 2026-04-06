package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.logic.commands.AddCommand.MESSAGE_TAGS_DO_NOT_EXIST;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.commons.util.CollectionUtil;
import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
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
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_LINK + "LINK] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_LINK + "https://www.websiteforjohndoe.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_PHONE = "Duplicate phone number detected";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!personToEdit.getPhone().equals(editedPerson.getPhone())
                && model.hasPhoneNumber(editedPerson.getPhone())) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        // Checks if tag exist
        ArrayList<Tag> nonExistentTags = new ArrayList<>();
        for (Tag tag : editedPerson.getTags()) {
            if (!model.hasTag(tag)) {
                nonExistentTags.add(tag);
            }
        }

        if (!nonExistentTags.isEmpty()) {
            throw new CommandException(MESSAGE_TAGS_DO_NOT_EXIST + nonExistentTags.toString());
        }

        Person editedPersonWithTags = getPersonWithTags(editedPerson, model);

        model.setPerson(personToEdit, editedPersonWithTags);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPersonWithTags)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Optional<Company> updatedCompany = editPersonDescriptor.isCompanyEdited()
                ? editPersonDescriptor.getCompany()
                : Optional.ofNullable(personToEdit.getCompany());
        Optional<Remark> updatedRemark = editPersonDescriptor.isRemarkEdited()
                ? editPersonDescriptor.getRemark()
                : Optional.ofNullable(personToEdit.getRemark());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Optional<Link> updatedLink = editPersonDescriptor.isLinkEdited()
                ? editPersonDescriptor.getLink()
                : Optional.ofNullable(personToEdit.getLink());
        DateAdded dateAdded = personToEdit.getDateAdded();
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedCompany, updatedAddress,
                updatedRemark, updatedLink, dateAdded, updatedTags);
    }

    private static Person getPersonWithTags(Person person, Model model) {
        assert person != null;

        Set<Tag> existingTags = model.getCLinkedin()
                .getTagList()
                .stream()
                .filter(x -> person.getTags().contains(x))
                .collect(Collectors.toSet());

        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                Optional.ofNullable(person.getCompany()),
                person.getAddress(),
                Optional.ofNullable(person.getRemark()),
                Optional.ofNullable(person.getLink()),
                person.getDateAdded(),
                existingTags
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Company company;
        private boolean isCompanyEdited;
        private Address address;
        private Remark remark;
        private boolean isRemarkEdited;
        private Link link;
        private boolean isLinkEdited;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            this.company = toCopy.company;
            this.isCompanyEdited = toCopy.isCompanyEdited;
            setAddress(toCopy.address);
            this.remark = toCopy.remark;
            this.isRemarkEdited = toCopy.isRemarkEdited;
            this.link = toCopy.link;
            this.isLinkEdited = toCopy.isLinkEdited;
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field has been edited.
         *
         * A field is considered edited if:
         * - A new value is provided (non-null), OR
         * - The field is explicitly cleared via its corresponding clear method.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, company, address, remark, link, tags)
                    || isRemarkEdited || isCompanyEdited || isLinkEdited;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets the company to be edited.
         *
         * Marks the company field as edited and replaces the existing company value.
         *
         * @param company The new company value. Must not be null.
         */
        public void setCompany(Company company) {
            this.company = company;
            this.isCompanyEdited = true;
        }

        /**
         * Clears the company.
         *
         * Marks the company field as edited and removes the existing company value
         * by setting it to null.
         */
        public void clearCompany() {
            this.company = null;
            this.isCompanyEdited = true;
        }

        /**
         * Returns true if the company field has been edited.
         *
         * This includes both:
         * - Setting a new company value
         * - Explicitly clearing the company
         */
        public boolean isCompanyEdited() {
            return isCompanyEdited;
        }

        /**
         * Returns the company to be edited.
         *
         * @return An {@code Optional} containing the new company if present,
         *         or empty if the company is cleared or not set.
         */
        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        /**
         * Sets the remark to be edited.
         *
         * Marks the remark field as edited and replaces the existing remark value.
         *
         * @param remark The new remark value. Must not be null.
         */
        public void setRemark(Remark remark) {
            this.remark = remark;
            this.isRemarkEdited = true;
        }

        /**
         * Clears the remark.
         *
         * Marks the remark field as edited and removes the existing remark value
         * by setting it to null.
         */
        public void clearRemark() {
            this.remark = null;
            this.isRemarkEdited = true;
        }

        /**
         * Returns the remark to be edited.
         *
         * @return An {@code Optional} containing the new remark if present,
         *         or empty if the remark is cleared or not set.
         */
        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        /**
         * Returns true if the remark field has been edited.
         *
         * This includes both:
         * - Setting a new remark value
         * - Explicitly clearing the remark
         */
        public boolean isRemarkEdited() {
            return isRemarkEdited;
        }

        /**
         * Sets the link to be edited.
         */
        public void setLink(Link link) {
            this.link = link;
            this.isLinkEdited = true;
        }

        /**
         * Clears the link (i.e., removes any existing link).
         */
        public void clearLink() {
            this.link = null;
            this.isLinkEdited = true;
        }

        public Optional<Link> getLink() {
            return Optional.ofNullable(link);
        }

        public boolean isLinkEdited() {
            return isLinkEdited;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(company, otherEditPersonDescriptor.company)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(remark, otherEditPersonDescriptor.remark)
                    && Objects.equals(link, otherEditPersonDescriptor.link)
                    && isLinkEdited == otherEditPersonDescriptor.isLinkEdited
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
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
                    .add("tags", tags)
                    .toString();
        }
    }
}
