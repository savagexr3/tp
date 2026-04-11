package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the address book whose company name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindComCommand extends Command {

    public static final String COMMAND_WORD = "findcom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose company matches "
            + "the specified company name (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: COMPANY [;MORE_COMPANIES]...\n"
            + "Example: " + COMMAND_WORD + " Google; Shopee; DBS";

    private static final Logger logger = LogsCenter.getLogger(FindComCommand.class);

    private final CompanyContainsKeywordsPredicate predicate;

    /**
     * Creates a {@code FindComCommand} with the given predicate.
     * @param predicate Predicate used to filter persons by company keywords.
     */
    public FindComCommand(CompanyContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing findcom with keywords: " + predicate.getKeywordsString());

        model.updateFilteredPersonList(predicate);
        int matchCount = model.getFilteredPersonList().size();

        logger.info("findcom matched contacts count: " + matchCount);
        return new CommandResult(
                String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW, matchCount, predicate.getKeywordsString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindComCommand)) {
            return false;
        }

        FindComCommand otherFindCommand = (FindComCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
