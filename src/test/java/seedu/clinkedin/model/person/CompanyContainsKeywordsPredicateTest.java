package seedu.clinkedin.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.testutil.PersonBuilder;

public class CompanyContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Google");
        List<String> secondPredicateKeywordList = Arrays.asList("Google", "Shopee");

        CompanyContainsKeywordsPredicate firstPredicate =
                new CompanyContainsKeywordsPredicate(firstPredicateKeywordList);
        CompanyContainsKeywordsPredicate secondPredicate =
                new CompanyContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyContainsKeywordsPredicate firstPredicateCopy =
                new CompanyContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        // One keyword
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Google"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Multiple keywords
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Google", "Shopee"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Only one matching keyword
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Shopee", "Grab"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Grab").build()));

        // Mixed-case keywords (case-insensitive)
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("gOoGlE"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Partial match (since using containsWordIgnoreCase)
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("NUS"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("NUS Computing").build()));
    }

    @Test
    public void test_companyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Non-matching keyword
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Shopee"));
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Keywords match other fields but not company
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Alice", "12345678", "Main"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345678")
                .withAddress("Main Street")
                .withCompany("Google")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Google", "Shopee");
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(keywords);

        String expected = CompanyContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
