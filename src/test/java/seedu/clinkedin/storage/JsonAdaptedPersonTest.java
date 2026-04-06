package seedu.clinkedin.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinkedin.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.exceptions.IllegalValueException;
import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.DateAdded;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_COMPANY = "G@ogle";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_REMARK = "";
    private static final String INVALID_LINK = "not-a-valid-link";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_COLOR = "bloo";
    private static final String INVALID_DATE_ADDED = "2026/03/18";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_DATEADDED = BENSON.getDateAdded().toString();
    private static final String VALID_LINK = BENSON.getLink().toString();
    private static final String VALID_TAG = "friends";
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        // EP: fully valid adapted person
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        // EP: invalid name field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        assertThrows(IllegalValueException.class, Name.MESSAGE_INVALID_CHARACTERS, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        // EP: missing required name field
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                VALID_ADDRESS, VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        // EP: invalid phone field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        assertThrows(IllegalValueException.class, Phone.MESSAGE_NON_DIGIT, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        // EP: missing required phone field
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_COMPANY,
                VALID_ADDRESS, VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        // EP: invalid email field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_COMPANY, VALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        assertThrows(IllegalValueException.class, Email.MESSAGE_INVALID_AT, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        // EP: missing required email field
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_COMPANY,
                VALID_ADDRESS, VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        // EP: invalid optional company field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_COMPANY, VALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        assertThrows(IllegalValueException.class, Company.MESSAGE_INVALID_CHARACTERS, person::toModelType);
    }

    @Test
    public void toModelType_nullCompany_success() throws Exception {
        // EP: missing optional company field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getCompany());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        // EP: invalid address field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, INVALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        assertThrows(IllegalValueException.class, Address.MESSAGE_EMPTY, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        // EP: missing required address field
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                null, VALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        // EP: invalid optional remark field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_COMPANY, VALID_ADDRESS, INVALID_REMARK, VALID_LINK, VALID_DATEADDED, VALID_TAGS);

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullRemark_success() throws Exception {
        // EP: missing optional remark field
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_COMPANY, VALID_ADDRESS, null, VALID_LINK, VALID_DATEADDED, VALID_TAGS);

        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getRemark());
    }

    @Test
    public void toModelType_invalidLink_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                        VALID_ADDRESS, VALID_REMARK, INVALID_LINK, VALID_DATEADDED, VALID_TAGS);
        String expectedMessage = Link.MESSAGE_INVALID_SCHEME;
        assertThrows(IllegalArgumentException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLink_success() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_COMPANY, VALID_ADDRESS, VALID_REMARK, null, VALID_DATEADDED, VALID_TAGS);
        // null link is allowed — should not throw
        person.toModelType();
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG, INVALID_COLOR));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidColor_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(VALID_TAG, INVALID_COLOR));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_ADDRESS,
                        VALID_REMARK, VALID_LINK, VALID_DATEADDED, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateAdded_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                        VALID_ADDRESS, VALID_REMARK, VALID_LINK, INVALID_DATE_ADDED, VALID_TAGS);
        String expectedMessage = DateAdded.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateAdded_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                        VALID_ADDRESS, VALID_REMARK, VALID_LINK, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateAdded.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
