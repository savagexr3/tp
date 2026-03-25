package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;
import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.model.Model;

/**
 * Sorts the displayed contact list alphabetically by company name, case-insensitively.
 */
public class SortComCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(SortComCommand.class);
    public static final String COMMAND_WORD = "sortcom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all displayed contacts by company name "
            + "alphabetically (case-insensitive).\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Contacts sorted by company name.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing sortcom: sorting displayed contacts by company");
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
