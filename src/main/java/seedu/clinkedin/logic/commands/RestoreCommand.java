package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;

/**
 * Restores a deleted person identified using its displayed index from the deleted person list.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores the deleted person identified by "
            + "the index number used in the displayed deleted person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESTORE_PERSON_SUCCESS = "Restored Person: %1$s";
    public static final String MESSAGE_RESTORE_MISSING_TAGS =
            "Note: Some tags could not be restored as they were renamed and/or deleted.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PHONE = "Duplicate phone number detected";

    private final Index targetIndex;

    public RestoreCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<DeletedPersonRecord> lastShownList = model.getFilteredDeletedPersonRecordList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELETED_PERSON_RECORD_DISPLAYED_INDEX);
        }

        DeletedPersonRecord recordToRestore = lastShownList.get(targetIndex.getZeroBased());

        if (model.hasPerson(recordToRestore.getPerson())) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.hasPhoneNumber(recordToRestore.getPerson().getPhone())) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        Person cleanedPerson = model.restorePerson(recordToRestore);

        int deletedRecordTagCount = recordToRestore.getPerson().getTags().size();
        int restoredTagCount = cleanedPerson.getTags().size();

        String message = String.format(MESSAGE_RESTORE_PERSON_SUCCESS, Messages.format(cleanedPerson));

        if (restoredTagCount < deletedRecordTagCount) {
            message += "\n" + MESSAGE_RESTORE_MISSING_TAGS;
        }

        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RestoreCommand)) {
            return false;
        }

        RestoreCommand otherRestoreCommand = (RestoreCommand) other;
        return targetIndex.equals(otherRestoreCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
