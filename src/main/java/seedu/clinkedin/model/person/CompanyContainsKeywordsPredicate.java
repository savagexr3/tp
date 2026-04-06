package seedu.clinkedin.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.clinkedin.commons.util.StringUtil;
import seedu.clinkedin.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Company} matches any of the keywords given.
 */
public class CompanyContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a {@code CompanyContainsKeywordsPredicate} with the specified keywords.
     *
     * <p>The predicate tests whether a {@code Person}'s company field contains
     * any of the given keywords (case-insensitive).</p>
     *
     * @param keywords A list of keywords to match against the person's company.
     *                 Must not be null.
     */
    public CompanyContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = List.copyOf(keywords);
    }

    public String getKeywordsString() {
        return String.join(", ", keywords);
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        Company company = person.getCompany();
        if (company == null) {
            return false;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(company.value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CompanyContainsKeywordsPredicate)) {
            return false;
        }

        CompanyContainsKeywordsPredicate otherPredicate = (CompanyContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
