package seedu.clinkedin.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.clinkedin.commons.util.StringUtil;
import seedu.clinkedin.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Company} matches any of the keywords given.
 */
public class CompanyContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public CompanyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getKeywordsString() {
        return String.join(", ", keywords);
    }

    @Override
    public boolean test(Person person) {
        if (person.getCompany() == null) {
            return false;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        person.getCompany().companyName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyContainsKeywordsPredicate)) {
            return false;
        }

        CompanyContainsKeywordsPredicate otherCompanyContainsKeywordsPredicate =
                (CompanyContainsKeywordsPredicate) other;
        return keywords.equals(otherCompanyContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

