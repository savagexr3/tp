package seedu.clinkedin.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.ReadOnlyCLinkedin;
import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.DateAdded;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.person.Remark;
import seedu.clinkedin.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    Optional.of(new Company("Google")), new Address("Blk 30 Geylang Street 29, #06-40"),
                    Optional.of(new Remark("Enjoys AI and basketball")),
                    Optional.of(new Link("https://www.linkedin.com/in/alexyeoh")),
                    new DateAdded("16-02-2026"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    Optional.of(new Company("Shopee")), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    Optional.of(new Remark("Met during internship")),
                    Optional.of(new Link("https://www.linkedin.com/in/berniceyu")),
                    new DateAdded("25-02-2026"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    Optional.of(new Company("Grab")), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    Optional.of(new Remark("Lives nearby")),
                    Optional.of(new Link("https://www.linkedin.com/in/charlotteoliveiro")),
                    new DateAdded("08-03-2026"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    Optional.of(new Company("GovTech")), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    Optional.empty(),
                    Optional.empty(),
                    new DateAdded("13-03-2026"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    Optional.of(new Company("NUS Computing")), new Address("Blk 47 Tampines Street 20, #17-35"),
                    Optional.of(new Remark("Coursemate from CS2103")),
                    Optional.empty(),
                    new DateAdded("15-03-2026"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    Optional.of(new Company("DBS")), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    Optional.of(new Remark("Works in finance")),
                    Optional.of(new Link("https://www.linkedin.com/in/roybalakrishnan")),
                    new DateAdded("20-03-2026"),
                    getTagSet("colleagues"))
            };
    }

    /**
     * Returns sample deleted person records for showcasing the deleted-history feature.
     *
     * One record is intentionally older than 7 days so it will be pruned automatically.
     */
    public static DeletedPersonRecord[] getSampleDeletedPersonRecords() {
        return new DeletedPersonRecord[] {
                new DeletedPersonRecord(
                        new Person(new Name("Brandon Lee"), new Phone("93334444"), new Email("brandon@example.com"),
                                Optional.of(new Company("Sea")), new Address("21 Jurong West Street 52"),
                                Optional.empty(),
                                Optional.empty(),
                                new DateAdded("15-03-2026"),
                                getTagSet("colleagues")),
                        LocalDateTime.now().minusDays(3)
                ),
                new DeletedPersonRecord(
                        new Person(new Name("Cheryl Ng"), new Phone("97778888"), new Email("cheryl@example.com"),
                                Optional.of(new Company("GovTech")), new Address("8 Clementi Avenue 2"),
                                Optional.empty(),
                                Optional.of(new Link("https://www.linkedin.com/in/cherylng")),
                                new DateAdded("15-03-2026"),
                                getTagSet("classmates")),
                        LocalDateTime.now().minusHours(2)
                ),
                new DeletedPersonRecord(
                        new Person(new Name("Brandon Lee"), new Phone("93334444"), new Email("brandon.old@example.com"),
                                Optional.of(new Company("Sea")), new Address("21 Jurong West Street 52"),
                                Optional.empty(),
                                Optional.empty(),
                                new DateAdded("15-03-2026"),
                                getTagSet("colleagues")),
                        LocalDateTime.now().minusMinutes(45)
                )
        };
    }

    public static ReadOnlyCLinkedin getSampleAddressBook() {
        CLinkedin sampleAb = new CLinkedin();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
            for (Tag tag : samplePerson.getTags()) {
                if (!sampleAb.hasTag(tag)) {
                    sampleAb.addTag(tag);
                }
            }
        }

        for (DeletedPersonRecord deletedRecord : getSampleDeletedPersonRecords()) {
            sampleAb.addDeletedPersonRecord(deletedRecord);

            for (Tag tag : deletedRecord.getPerson().getTags()) {
                if (!sampleAb.hasTag(tag)) {
                    sampleAb.addTag(tag);
                }
            }
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}
