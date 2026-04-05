package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_DELETED_PERSON_RECORDS;

import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.model.Model;

//@@author rxlee04
/**
 * Lists all deleted contacts to the user.
 */
public class DeletedCommand extends Command {

    public static final String COMMAND_WORD = "deleted";

    public static final String MESSAGE_SUCCESS = "Listed all deleted contacts";

    public static final String MESSAGE_NO_DELETED_RECORDS = "0 deleted person records found.";

    private static final Logger logger = LogsCenter.getLogger(DeletedCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing DeletedCommand");

        model.updateFilteredDeletedPersonRecordList(PREDICATE_SHOW_ALL_DELETED_PERSON_RECORDS);

        if (model.getFilteredDeletedPersonRecordList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_DELETED_RECORDS);
        }

        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
