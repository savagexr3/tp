package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_DELETED_PERSON_RECORDS;

import seedu.clinkedin.model.Model;

/**
 * Lists all deleted contacts to the user.
 */
public class DeletedCommand extends Command {

    public static final String COMMAND_WORD = "deleted";

    public static final String MESSAGE_SUCCESS = "Listed all deleted contacts";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDeletedPersonRecordList(PREDICATE_SHOW_ALL_DELETED_PERSON_RECORDS);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
