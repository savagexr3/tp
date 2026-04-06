package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.model.Model;

/**
 * Sorts the currently displayed contact list alphabetically by company name, case-insensitively.
 * If no contacts are currently displayed, returns a message indicating that there is nothing to sort.
 */
public class SortComCommand extends Command {

    public static final String COMMAND_WORD = "sortcom";
    public static final String MESSAGE_EMPTY = "No contacts to be sorted by company name.";
    public static final String MESSAGE_SUCCESS = "Contacts sorted by company name.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all displayed contacts by company name "
            + "alphabetically (case-insensitive).\n"
            + "Example: " + COMMAND_WORD;

    private static final Logger logger = LogsCenter.getLogger(SortComCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        int displayedCount = model.getFilteredPersonList().size();
        logger.info("Executing sortcom on " + displayedCount + " displayed contact(s).");

        if (displayedCount == 0) {
            logger.info("sortcom finished without sorting because there were no displayed contacts.");
            return new CommandResult(MESSAGE_EMPTY);
        }

        model.sortFilteredPersonListByCompany();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof SortComCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
