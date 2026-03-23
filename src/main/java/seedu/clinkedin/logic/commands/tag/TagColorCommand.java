package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_NONE;

import java.util.Set;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Changes a tag's color
 */
public class TagColorCommand extends TagCommand {
    public static final String COMMAND_WORD = "color";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes a tag's color.\n"
            + "Parameters: TAG_NAME VALID_COLOR\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " rich gold";

    public static final String MESSAGE_SUCCESS = "Tag assigned successfully.";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag does not exist.";
    public static final String MESSAGE_TAG_ALREADY_ASSIGNED = "Tag already assigned to this contact.";

    private Tag tag;
    private String color;

    /**
     * Creates a TagAssignCommand to assign the specified {@code Tag} to the person at {@code index}.
     */
    public TagColorCommand(Tag tag, String color) {
        requireNonNull(tag);
        requireNonNull(color);
        this.tag = tag;
        this.color = color;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTag(tag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        for (Person p : model.getCLinkedin().getPersonList()) {
            for (Tag t : p.getTags()) {
                if (t.tagName.equalsIgnoreCase(tag.tagName)) {
                    t.tagColor = color;
                    break;
                }
            }
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_NONE);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}