package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.tag.Tag;

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

        if (!model.hasTag(deleteTag)) {
            throw new CommandException(MESSAGE_NOT_EXIST_TAG);
        }

        model.deleteTag(deleteTag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, deleteTag.tagName));
    }
}
