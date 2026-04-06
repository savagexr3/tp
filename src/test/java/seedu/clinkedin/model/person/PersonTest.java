package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_LINK_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinkedin.testutil.Assert.assertThrows;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        // Defensive coding: returned tag collection must be immutable from the caller's perspective
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // EP: same object
        assertTrue(ALICE.isSamePerson(ALICE));

        // EP: null input
        assertFalse(ALICE.isSamePerson(null));

        // EP: same identity field (name), other fields different
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // EP: different identity field (name)
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // EP: same spelling but different case still counts as different because Name equality is case-sensitive
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // EP: different name value
        editedBob = new PersonBuilder(BOB).withName("Bob Choo Jr").build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // EP: same values
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // EP: same object
        assertTrue(ALICE.equals(ALICE));

        // EP: null input
        assertFalse(ALICE.equals(null));

        // EP: different type
        assertFalse(ALICE.equals(5));

        // EP: entirely different person
        assertFalse(ALICE.equals(BOB));

        // EP: different name field
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different phone field
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different email field
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different company field
        editedAlice = new PersonBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different remark field
        editedAlice = new PersonBuilder(ALICE).withRemark(VALID_REMARK_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different address field
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different link field
        editedAlice = new PersonBuilder(ALICE).withLink(VALID_LINK_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // EP: different tags field
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        // Integration: verifies toString includes all relevant Person fields
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", company=" + ALICE.getCompany() + ", address=" + ALICE.getAddress()
                + ", remark=" + ALICE.getRemark() + ", link=" + ALICE.getLink()
                + ", dateAdded=" + ALICE.getDateAdded() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
