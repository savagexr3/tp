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

        // Same object
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same values
        CompanyContainsKeywordsPredicate firstPredicateCopy =
                new CompanyContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different type
        assertFalse(firstPredicate.equals(1));

        // Null comparison
        assertFalse(firstPredicate.equals(null));

        // Different keyword lists
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        // Combination strategy: at least once over selected valid keyword inputs.
        // Heuristic: each valid keyword category appears in at least one positive test case.

        // EP: one keyword matches the company exactly
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Google"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Combination: multiple valid keywords supplied, one of them matches
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Google", "Shopee"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Combination: only one keyword in a valid list matches
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Shopee", "Grab"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Grab").build()));

        // EP: case-insensitive match
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("gOoGlE"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // EP: keyword matches one full word inside a multi-word company name
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("NUS"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("NUS Computing").build()));
    }

    @Test
    public void test_companyDoesNotContainKeywords_returnsFalse() {
        // Heuristic: invalid/non-matching situations are tested one at a time before any more complex combinations.

        // EP: zero keywords provided
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // EP: non-matching keyword
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Shopee"));
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Combination: keywords match other Person fields, but not the company field under test
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Alice", "12345678", "Main Street"));
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
