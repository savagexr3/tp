package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.tag.Tag;

//@@author rxlee04
/**
 * Deletes a tag from the address book.
 * Also removes the tag from all persons associated with it.
 */
public class TagDeleteCommand extends TagCommand {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tag.\n"
            + "Parameters: TAG_NAME\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " friends";

    public static final String MESSAGE_SUCCESS = "Tag deleted: %1$s";
    public static final String MESSAGE_NOT_EXIST_TAG = "Tag does not exist in the address book.";

    private static final Logger logger = LogsCenter.getLogger(TagDeleteCommand.class);

    private final Tag deleteTag;

    /**
     * Creates a TagDeleteCommand to delete the specified {@code Tag}.
     */
    public TagDeleteCommand(Tag tag) {
        requireNonNull(tag);
        deleteTag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing TagDeleteCommand with tag name: " + deleteTag.toString());

        if (!model.hasTag(deleteTag)) {
            logger.warning("Attempted to delete non-existent tag: " + deleteTag.toString());
            throw new CommandException(MESSAGE_NOT_EXIST_TAG);
        }

        model.deleteTag(deleteTag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, deleteTag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagDeleteCommand)) {
            return false;
        }

        TagDeleteCommand otherTagDeleteCommand = (TagDeleteCommand) other;
        return deleteTag.equals(otherTagDeleteCommand.deleteTag);
    }
}
