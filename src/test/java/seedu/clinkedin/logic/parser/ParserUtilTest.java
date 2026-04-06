package seedu.clinkedin.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.person.Remark;
import seedu.clinkedin.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_REMARK = "Met during career fair";
    private static final String EMPTY_REMARK = "   ";

    private static final String INVALID_COMPANY = "G@ogle";
    private static final String VALID_COMPANY = "Google";
    private static final String EMPTY_COMPANY = "   ";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        // EP: non-numeric index input
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        // EP: numeric input outside int range
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1L)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // BVA: smallest valid one-based index without whitespace
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // EP: valid index with surrounding whitespace
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        // EP: invalid name characters
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        // EP: valid typical name
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        // EP: valid name with surrounding whitespace should be trimmed
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseName_emptyAfterTrim_throwsParseException() {
        // EP: blank input after trim
        assertThrows(ParseException.class, () -> ParserUtil.parseName("   "));
    }

    @Test
    public void parseName_multipleSpaces_throwsParseException() {
        // EP: invalid spacing inside name
        assertThrows(ParseException.class, () -> ParserUtil.parseName("Rachel  Walker"));
    }

    @Test
    public void parseName_validSpecialCharacters_returnsName() throws Exception {
        // EP: valid special characters supported by Name
        Name expectedName = new Name("Mary-Jane O'Connor");
        assertEquals(expectedName, ParserUtil.parseName("  Mary-Jane O'Connor  "));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        // EP: invalid non-digit phone input
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        // EP: valid typical phone
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        // EP: valid phone with surrounding whitespace should be trimmed
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        // EP: blank address after trim
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        // EP: valid typical address
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        // EP: valid address with surrounding whitespace should be trimmed
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        // EP: invalid email format
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        // EP: valid typical email
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        // EP: valid email with surrounding whitespace should be trimmed
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseRemarkForAdd_null_throwsNullPointerException() {
        // EP: required optional input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemarkForAdd(null));
    }

    @Test
    public void parseRemarkForAdd_emptyOptional_returnsEmptyOptional() throws Exception {
        // EP: remark omitted entirely for add
        assertEquals(Optional.empty(), ParserUtil.parseRemarkForAdd(Optional.empty()));
    }

    @Test
    public void parseRemarkForAdd_blankRemark_throwsParseException() {
        // EP: blank remark is invalid when adding
        assertThrows(ParseException.class, Remark.MESSAGE_EMPTY, () ->
                ParserUtil.parseRemarkForAdd(Optional.of(EMPTY_REMARK)));
    }

    @Test
    public void parseRemarkForAdd_invalidValue_throwsParseException() {
        // EP: invalid remark value for add
        assertThrows(ParseException.class, () -> ParserUtil.parseRemarkForAdd(Optional.of("hello/world")));
    }

    @Test
    public void parseRemarkForAdd_validRemarkWithoutWhitespace_returnsRemark() throws Exception {
        // EP: valid typical remark for add
        Optional<Remark> expectedRemark = Optional.of(new Remark(VALID_REMARK));
        assertEquals(expectedRemark, ParserUtil.parseRemarkForAdd(Optional.of(VALID_REMARK)));
    }

    @Test
    public void parseRemarkForAdd_validRemarkWithWhitespace_returnsTrimmedRemark() throws Exception {
        // EP: valid remark with surrounding whitespace should be trimmed for add
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Optional<Remark> expectedRemark = Optional.of(new Remark(VALID_REMARK));
        assertEquals(expectedRemark, ParserUtil.parseRemarkForAdd(Optional.of(remarkWithWhitespace)));
    }

    @Test
    public void parseRemarkForEdit_null_throwsNullPointerException() {
        // EP: required optional input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemarkForEdit(null));
    }

    @Test
    public void parseRemarkForEdit_emptyOptional_returnsEmptyOptional() throws Exception {
        // EP: remark omitted entirely for edit
        assertEquals(Optional.empty(), ParserUtil.parseRemarkForEdit(Optional.empty()));
    }

    @Test
    public void parseRemarkForEdit_blankRemark_returnsEmptyOptional() throws Exception {
        // EP: blank remark means clear remark when editing
        assertEquals(Optional.empty(), ParserUtil.parseRemarkForEdit(Optional.of(EMPTY_REMARK)));
    }

    @Test
    public void parseRemarkForEdit_invalidValue_throwsParseException() {
        // EP: invalid remark value for edit
        assertThrows(ParseException.class, () -> ParserUtil.parseRemarkForEdit(Optional.of("hello/world")));
    }

    @Test
    public void parseRemarkForEdit_validRemarkWithoutWhitespace_returnsRemark() throws Exception {
        // EP: valid typical remark for edit
        Optional<Remark> expectedRemark = Optional.of(new Remark(VALID_REMARK));
        assertEquals(expectedRemark, ParserUtil.parseRemarkForEdit(Optional.of(VALID_REMARK)));
    }

    @Test
    public void parseRemarkForEdit_validRemarkWithWhitespace_returnsTrimmedRemark() throws Exception {
        // EP: valid remark with surrounding whitespace should be trimmed for edit
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Optional<Remark> expectedRemark = Optional.of(new Remark(VALID_REMARK));
        assertEquals(expectedRemark, ParserUtil.parseRemarkForEdit(Optional.of(remarkWithWhitespace)));
    }

    @Test
    public void parseCompany_null_throwsNullPointerException() {
        // EP: required optional input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany(null));
    }

    @Test
    public void parseCompany_invalidValue_throwsParseException() {
        // EP: invalid company value
        assertThrows(ParseException.class, () -> ParserUtil.parseCompany(Optional.of(INVALID_COMPANY)));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsCompany() throws Exception {
        // EP: valid typical company
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(Optional.of(VALID_COMPANY)).get());
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedCompany() throws Exception {
        // EP: valid company with surrounding whitespace should be trimmed
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(Optional.of(companyWithWhitespace)).get());
    }

    @Test
    public void parseCompanyForAdd_null_throwsNullPointerException() {
        // EP: required optional input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompanyForAdd(null));
    }

    @Test
    public void parseCompanyForAdd_emptyOptional_returnsEmptyOptional() throws Exception {
        // EP: company omitted entirely for add
        assertEquals(Optional.empty(), ParserUtil.parseCompanyForAdd(Optional.empty()));
    }

    @Test
    public void parseCompanyForAdd_blankCompany_throwsParseException() {
        // EP: blank company is invalid when adding
        assertThrows(ParseException.class, Company.MESSAGE_EMPTY, () ->
                ParserUtil.parseCompanyForAdd(Optional.of(EMPTY_COMPANY)));
    }

    @Test
    public void parseCompanyForAdd_invalidValue_throwsParseException() {
        // EP: invalid company value for add
        assertThrows(ParseException.class, () -> ParserUtil.parseCompanyForAdd(Optional.of(INVALID_COMPANY)));
    }

    @Test
    public void parseCompanyForAdd_validCompanyWithoutWhitespace_returnsCompany() throws Exception {
        // EP: valid typical company for add
        Optional<Company> expectedCompany = Optional.of(new Company(VALID_COMPANY));
        assertEquals(expectedCompany, ParserUtil.parseCompanyForAdd(Optional.of(VALID_COMPANY)));
    }

    @Test
    public void parseCompanyForAdd_validCompanyWithWhitespace_returnsTrimmedCompany() throws Exception {
        // EP: valid company with surrounding whitespace should be trimmed for add
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Optional<Company> expectedCompany = Optional.of(new Company(VALID_COMPANY));
        assertEquals(expectedCompany, ParserUtil.parseCompanyForAdd(Optional.of(companyWithWhitespace)));
    }

    @Test
    public void parseCompanyForEdit_null_throwsNullPointerException() {
        // EP: required optional input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompanyForEdit(null));
    }

    @Test
    public void parseCompanyForEdit_emptyOptional_returnsEmptyOptional() throws Exception {
        // EP: company omitted entirely for edit
        assertEquals(Optional.empty(), ParserUtil.parseCompanyForEdit(Optional.empty()));
    }

    @Test
    public void parseCompanyForEdit_blankCompany_returnsEmptyOptional() throws Exception {
        // EP: blank company means clear company when editing
        assertEquals(Optional.empty(), ParserUtil.parseCompanyForEdit(Optional.of(EMPTY_COMPANY)));
    }

    @Test
    public void parseCompanyForEdit_invalidValue_throwsParseException() {
        // EP: invalid company value for edit
        assertThrows(ParseException.class, () -> ParserUtil.parseCompanyForEdit(Optional.of(INVALID_COMPANY)));
    }

    @Test
    public void parseCompanyForEdit_validCompanyWithoutWhitespace_returnsCompany() throws Exception {
        // EP: valid typical company for edit
        Optional<Company> expectedCompany = Optional.of(new Company(VALID_COMPANY));
        assertEquals(expectedCompany, ParserUtil.parseCompanyForEdit(Optional.of(VALID_COMPANY)));
    }

    @Test
    public void parseCompanyForEdit_validCompanyWithWhitespace_returnsTrimmedCompany() throws Exception {
        // EP: valid company with surrounding whitespace should be trimmed for edit
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Optional<Company> expectedCompany = Optional.of(new Company(VALID_COMPANY));
        assertEquals(expectedCompany, ParserUtil.parseCompanyForEdit(Optional.of(companyWithWhitespace)));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        // EP: required input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        // EP: invalid tag value
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        // EP: valid typical tag
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        // EP: valid tag with surrounding whitespace should be trimmed
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTag_invalidColor_throwsParseException() {
        // EP: invalid color name
        String color = "bloo";
        assertThrows(ParseException.class, () -> ParserUtil.parseTag("friends", color));
    }

    @Test
    public void parseTag_invalidColorFormat_throwsParseException() {
        // EP: invalid color format under current tag color rules
        String color = "rgb(255,255,255)";
        assertThrows(ParseException.class, () -> ParserUtil.parseTag("friends", color));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        // EP: required collection input is null
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        // Multiple-input heuristic: invalid tag is tested individually within a collection input.
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        // EP: zero tags
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        // Multiple-input heuristic: representative valid tag combination
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
