package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose company name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindComCommand extends Command {

    public static final String COMMAND_WORD = "findcom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose company matches "
            + "the specified company name (case-insensitive).\n"
            + "Parameters: COMPANY\n"
            + "Example: " + COMMAND_WORD + " Google";

    private final CompanyContainsKeywordsPredicate predicate;

    public FindComCommand(CompanyContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW, model.getFilteredPersonList().size(),
                        predicate.getKeywordsString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
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

