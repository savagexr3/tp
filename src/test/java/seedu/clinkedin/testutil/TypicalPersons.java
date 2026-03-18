package seedu.clinkedin.testutil;

import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_LINK_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_LINK_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPhone("94351253")
            .withEmail("alice@example.com")
            .withCompany("Google")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withLink("https://linkedin.com/in/alicepauline")
            .withTags("friends")
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("98765432")
            .withEmail("johnd@example.com")
            .withCompany("Shopee")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withLink("https://linkedin.com/in/bensonmeier")
            .withTags("owesMoney", "friends")
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withCompany("Grab")
            .withAddress("wall street")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withCompany("GovTech")
            .withAddress("10th street")
            .withLink("https://linkedin.com/in/danielmeier")
            .withTags("friends")
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("94822244")
            .withEmail("werner@example.com")
            .withCompany("DBS")
            .withAddress("michegan ave")
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("94824277")
            .withEmail("lydia@example.com")
            .withCompany("NUS Computing")
            .withAddress("little tokyo")
            .withLink("https://linkedin.com/in/fionakunz")
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("94824422")
            .withEmail("anna@example.com")
            .withCompany("Sea")
            .withAddress("4th street")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("84824244")
            .withEmail("stefan@example.com")
            .withCompany("Grab")
            .withAddress("little india")
            .withLink("https://linkedin.com/in/hoonmeier")
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84821311")
            .withEmail("hans@example.com")
            .withCompany("Google")
            .withAddress("chicago ave")
            .withLink("https://linkedin.com/in/idamueller")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY).withAddress(VALID_ADDRESS_AMY)
            .withLink(VALID_LINK_AMY).withTags(VALID_TAG_FRIEND)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB).withAddress(VALID_ADDRESS_BOB)
            .withLink(VALID_LINK_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code CLinkedin} with all the typical persons.
     */
    public static CLinkedin getTypicalCLinkedin() {
        CLinkedin ab = new CLinkedin();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
