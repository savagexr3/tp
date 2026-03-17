package seedu.clinkedin.model.tag;

import java.util.function.Predicate;

import seedu.clinkedin.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches the given tag.
 */
public class TagContainsKeywordPredicate implements Predicate<Person> {
    public final Tag keyword;

    public TagContainsKeywordPredicate(Tag keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordPredicate)) {
            return false;
        }

        TagContainsKeywordPredicate otherTagContainsKeywordPredicate = (TagContainsKeywordPredicate) other;
        return keyword.equals(otherTagContainsKeywordPredicate.keyword);
    }

}
