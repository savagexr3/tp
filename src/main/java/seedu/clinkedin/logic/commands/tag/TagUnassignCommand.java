package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Removes a tag from a person in the address book.
 */
public class TagUnassignCommand extends TagCommand {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a tag from a person.\n"
            + "Parameters: PERSON_INDEX TAG_NAME\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 friends";

    public static final String MESSAGE_SUCCESS = "Tag removed successfully.";
    public static final String MESSAGE_TAG_NOT_ASSIGNED = "Tag not found for this contact.";

    private final Index index;
    private final Tag tag;

    /**
     * Creates a TagUnassignCommand to remove the specified {@code Tag} from the person at {@code index}.
     */
    public TagUnassignCommand(Index index, Tag tag) {
        requireNonNull(index);
        requireNonNull(tag);
        this.index = index;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (!personToEdit.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_TAG_NOT_ASSIGNED);
        }

        Person editedPerson = personToEdit.removeTag(tag);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TagUnassignCommand)) {
            return false;
        }
        TagUnassignCommand otherCommand = (TagUnassignCommand) other;
        return index.equals(otherCommand.index) && tag.equals(otherCommand.tag);
    }
}
