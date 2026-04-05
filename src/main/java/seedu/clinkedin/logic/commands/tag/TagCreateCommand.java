package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_COLOR;

import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.tag.Tag;

//@@author rxlee04
/**
 * Create a new tag in the address book.
 */
public class TagCreateCommand extends TagCommand {
    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new tag.\n"
            + "Parameters: TAG_NAME [" + PREFIX_COLOR + "COLOR]" + "\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " friends " + PREFIX_COLOR + "blue";

    public static final String MESSAGE_SUCCESS = "New tag added: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the address book";

    private static final Logger logger = LogsCenter.getLogger(TagCreateCommand.class);

    private final Tag newTag;

    /**
     * Creates a TagCreateCommand to add the specified {@code Tag}
     */
    public TagCreateCommand(Tag inNewTag) {
        requireNonNull(inNewTag);
        newTag = inNewTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing tag create with tag name: " + newTag.toString());

        if (model.hasTag(newTag)) {
            logger.warning("Duplicate tag creation attempt: " + newTag.toString());
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        model.addTag(newTag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newTag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCreateCommand)) {
            return false;
        }

        TagCreateCommand otherTagCreateCommand = (TagCreateCommand) other;
        return newTag.equals(otherTagCreateCommand.newTag);
    }
}
