package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Removes a tag from one or more persons in the address book.
 */
public class TagUnassignCommand extends TagCommand {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a tag from one or more persons.\n"
            + "Parameters: INDEX[,INDEX]... TAG_NAME\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1,2,3 friends";

    public static final String MESSAGE_SUCCESS = "Tag removed successfully from %d contact(s).";
    public static final String MESSAGE_TAG_NOT_ASSIGNED = "Tag not found for contact at index %d.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index: %d.";

    private final List<Index> indexes;
    private final Tag tag;

    /**
     * Creates a TagUnassignCommand to remove the specified {@code Tag}
     * from all persons at the given {@code indexes}.
     */
    public TagUnassignCommand(List<Index> indexes, Tag tag) {
        requireNonNull(indexes);
        requireNonNull(tag);
        this.indexes = indexes;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // validate all indexes first
        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
            }
        }

        // check all persons have the tag
        for (Index index : indexes) {
            Person person = lastShownList.get(index.getZeroBased());
            if (!person.getTags().contains(tag)) {
                throw new CommandException(
                        String.format(MESSAGE_TAG_NOT_ASSIGNED, index.getOneBased()));
            }
        }

        // remove tag from all persons
        List<Person> snapshot = new ArrayList<>(lastShownList);
        for (Index index : indexes) {
            Person personToEdit = snapshot.get(index.getZeroBased());
            Person currentPerson = model.getFilteredPersonList().get(index.getZeroBased());
            Person editedPerson = currentPerson.removeTag(tag);
            model.setPerson(personToEdit, editedPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, indexes.size()));
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
        return indexes.equals(otherCommand.indexes) && tag.equals(otherCommand.tag);
    }
}
