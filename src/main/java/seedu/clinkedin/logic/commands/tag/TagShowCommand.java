package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.model.tag.TagContainsKeywordPredicate;

/**
 * Shows users that belong to the tag
 */
public class TagShowCommand extends TagCommand {
    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows contacts belonging to a singular tag.\n"
            + "Parameters: TAG_NAME\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " friends";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag not found: ";

    private final TagContainsKeywordPredicate predicate;
    private Tag tag;

    /**
     * Creates a TagShowCommand to filter persons to the specified {@code Tag}
     */
    public TagShowCommand(TagContainsKeywordPredicate predicate, Tag tag) {
        this.predicate = predicate;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.hasTag(tag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND + tag.toString());
        }

        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format("%d contacts listed with tag '%s'.",
                        model.getFilteredPersonList().size(),
                        tag.toString()));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagShowCommand)) {
            return false;
        }

        TagShowCommand otherTagShowCommand = (TagShowCommand) other;
        return predicate.equals(otherTagShowCommand.predicate);
    }
}
