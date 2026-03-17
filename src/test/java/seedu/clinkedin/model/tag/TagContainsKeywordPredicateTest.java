package seedu.clinkedin.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.testutil.PersonBuilder;


public class TagContainsKeywordPredicateTest {

    @Test
    public void equals() {
        Tag firstPredicateKeyword = new Tag("friends");
        Tag secondPredicateKeyword = new Tag("jobless");

        TagContainsKeywordPredicate firstPredicate = new TagContainsKeywordPredicate(firstPredicateKeyword);
        TagContainsKeywordPredicate secondPredicate = new TagContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordPredicate firstPredicateCopy = new TagContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasTag_returnsTrue() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_personDoesNotHaveTag_returnsFalse() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("jobless").build()));
    }
}
