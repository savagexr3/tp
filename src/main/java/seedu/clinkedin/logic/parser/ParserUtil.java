package seedu.clinkedin.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.commons.util.StringUtil;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.person.Remark;
import seedu.clinkedin.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index must be a positive non-zero integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code oneBasedIndex} is null.
     * @throws ParseException if the specified index is invalid (not a non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        requireNonNull(oneBasedIndex);
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code name} is null.
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        String nameError = Name.getNameValidationError(trimmedName);
        if (nameError != null) {
            throw new ParseException(nameError);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code phone} is null.
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        String phoneError = Phone.getPhoneValidationError(trimmedPhone);
        if (phoneError != null) {
            throw new ParseException(phoneError);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String company} into a {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     * Blank input is treated as an absent optional company.
     *
     * @throws NullPointerException if {@code company} is null.
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Optional<Company> parseCompany(Optional<String> company) throws ParseException {
        requireNonNull(company);
        if (company.isEmpty()) {
            return Optional.empty();
        }

        String trimmedCompanyName = company.get().trim();
        if (trimmedCompanyName.isEmpty()) {
            return Optional.empty();
        }

        String companyNameError = Company.getCompanyNameValidationError(trimmedCompanyName);
        if (companyNameError != null) {
            throw new ParseException(companyNameError);
        }
        return Optional.of(new Company(trimmedCompanyName));
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code address} is null.
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        String addressError = Address.getAddressValidationError(trimmedAddress);
        if (addressError != null) {
            throw new ParseException(addressError);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark} when adding a contact.
     * Leading and trailing whitespaces will be trimmed.
     * Blank input is invalid for add because it does not represent an actual remark value.
     *
     * @throws NullPointerException if {@code remark} is null.
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Optional<Remark> parseRemarkForAdd(Optional<String> remark) throws ParseException {
        requireNonNull(remark);

        if (remark.isEmpty()) {
            return Optional.empty();
        }

        String trimmedRemark = remark.get().trim();
        if (trimmedRemark.isEmpty()) {
            throw new ParseException(Remark.MESSAGE_EMPTY);
        }

        String remarkError = Remark.getRemarkValidationError(trimmedRemark);
        if (remarkError != null) {
            throw new ParseException(remarkError);
        }

        return Optional.of(new Remark(trimmedRemark));
    }

    /**
     * Parses a {@code String remark} into a {@code Remark} when editing a contact.
     * Leading and trailing whitespaces will be trimmed.
     * Blank input is treated as clearing the existing remark.
     *
     * @throws NullPointerException if {@code remark} is null.
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Optional<Remark> parseRemarkForEdit(Optional<String> remark) throws ParseException {
        requireNonNull(remark);

        if (remark.isEmpty()) {
            return Optional.empty();
        }

        String trimmedRemark = remark.get().trim();
        if (trimmedRemark.isEmpty()) {
            return Optional.empty();
        }

        String remarkError = Remark.getRemarkValidationError(trimmedRemark);
        if (remarkError != null) {
            throw new ParseException(remarkError);
        }

        return Optional.of(new Remark(trimmedRemark));
    }

    /**
     * Parses a {@code String company} into a {@code Company} when adding a contact.
     * Leading and trailing whitespaces will be trimmed.
     * Blank input is invalid for add because it does not represent an actual company value.
     *
     * @throws NullPointerException if {@code company} is null.
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Optional<Company> parseCompanyForAdd(Optional<String> company) throws ParseException {
        requireNonNull(company);

        if (company.isEmpty()) {
            return Optional.empty();
        }

        String trimmedCompany = company.get().trim();
        if (trimmedCompany.isEmpty()) {
            throw new ParseException(Company.MESSAGE_EMPTY);
        }

        String companyError = Company.getCompanyNameValidationError(trimmedCompany);
        if (companyError != null) {
            throw new ParseException(companyError);
        }

        return Optional.of(new Company(trimmedCompany));
    }

    /**
     * Parses a {@code String company} into a {@code Company} when editing a contact.
     * Leading and trailing whitespaces will be trimmed.
     * Blank input is treated as clearing the existing company.
     *
     * @throws NullPointerException if {@code company} is null.
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Optional<Company> parseCompanyForEdit(Optional<String> company) throws ParseException {
        requireNonNull(company);

        if (company.isEmpty()) {
            return Optional.empty();
        }

        String trimmedCompany = company.get().trim();
        if (trimmedCompany.isEmpty()) {
            return Optional.empty();
        }

        String companyError = Company.getCompanyNameValidationError(trimmedCompany);
        if (companyError != null) {
            throw new ParseException(companyError);
        }

        return Optional.of(new Company(trimmedCompany));
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code email} is null.
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        String emailError = Email.getEmailValidationError(trimmedEmail);
        if (emailError != null) {
            throw new ParseException(emailError);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code tag} is null.
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        String tagNameError = Tag.getTagNameValidationError(trimmedTag);
        if (tagNameError != null) {
            throw new ParseException(tagNameError);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a tag name and color into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code tag} or {@code color} is null.
     * @throws ParseException if the given {@code tag} or {@code color} is invalid.
     */
    public static Tag parseTag(String tag, String color) throws ParseException {
        requireNonNull(tag);
        requireNonNull(color);
        String trimmedTag = tag.trim();
        String trimmedColor = color.trim();
        String tagNameError = Tag.getTagNameValidationError(trimmedTag);
        String tagColorError = Tag.getColorNameValidationError(trimmedColor);
        if (tagNameError != null) {
            throw new ParseException(tagNameError);
        }
        if (tagColorError != null) {
            throw new ParseException(tagColorError);
        }
        return new Tag(trimmedTag, trimmedColor);
    }

    /**
     * Parses a {@code String link} into a {@code Link}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws NullPointerException if {@code link} is null.
     * @throws ParseException if the given {@code link} is invalid.
     */
    public static Optional<Link> parseLink(Optional<String> link) throws ParseException {
        requireNonNull(link);
        if (link.isEmpty()) {
            return Optional.empty();
        }
        String trimmedLink = link.get().trim();
        String error = Link.getLinkValidationError(trimmedLink);
        if (error != null) {
            throw new ParseException(error);
        }
        return Optional.of(new Link(trimmedLink));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     *
     * @throws NullPointerException if {@code tags} is null.
     * @throws ParseException if any tag is invalid.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
