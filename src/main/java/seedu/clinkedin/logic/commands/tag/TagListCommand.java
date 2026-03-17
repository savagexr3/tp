package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.tag.Tag;

/**
 * Lists all existing tags in CLinkedin to the user.
 */
public class TagListCommand extends TagCommand {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tags:\n%1$s";
    public static final String MESSAGE_NO_TAGS = "No tags have been created yet";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Tag> tags = model.getCLinkedin().getTagList();

        if (tags.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TAGS);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, formatTags(tags)));
    }

    /**
     * Formats a list of tags into a readable string.
     * The tags are separated by newlines.
     *
     * @param tags The list of tags to be formatted.
     * @return A formatted string containing the tag names.
     */
    private String formatTags(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.joining("\n"));
    }
}
