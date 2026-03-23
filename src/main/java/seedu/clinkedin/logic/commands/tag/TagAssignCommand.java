package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Assigns an existing tag to one or more persons in the address book.
 */
public class TagAssignCommand extends TagCommand {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an existing tag to one or more persons.\n"
            + "Parameters: INDEX[,INDEX]... TAG_NAME\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1,2,3 friends";

    public static final String MESSAGE_SUCCESS = "Tag assigned successfully to %d contact(s).";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag does not exist.";
    public static final String MESSAGE_TAG_ALREADY_ASSIGNED = "Tag already assigned to contact at index %d.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index: %d.";

    private final List<Index> indexes;
    private final Tag tag;

    /**
     * Creates a TagAssignCommand to assign the specified {@code Tag}
     * to all persons at the given {@code indexes}.
     */
    public TagAssignCommand(List<Index> indexes, Tag tag) {
        requireNonNull(indexes);
        requireNonNull(tag);
        this.indexes = indexes;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (!model.hasTag(tag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        // validate all indexes first
        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
            }
        }

        // check for already assigned tags
        for (Index index : indexes) {
            Person person = lastShownList.get(index.getZeroBased());
            if (person.getTags().contains(tag)) {
                throw new CommandException(
                        String.format(MESSAGE_TAG_ALREADY_ASSIGNED, index.getOneBased()));
            }
        }

        // apply tag to all persons
        // use a snapshot to avoid concurrent modification
        List<Person> snapshot = new ArrayList<>(lastShownList);
        for (Index index : indexes) {
            Person personToEdit = snapshot.get(index.getZeroBased());
            // get current version from model in case earlier iterations updated it
            Person currentPerson = model.getFilteredPersonList().get(index.getZeroBased());
            Set<Tag> updatedTags = new HashSet<>(currentPerson.getTags());
            updatedTags.add(tag);
            Person editedPerson = new Person(
                    currentPerson.getName(),
                    currentPerson.getPhone(),
                    currentPerson.getEmail(),
                    currentPerson.getCompany(),
                    currentPerson.getAddress(),
                    Optional.ofNullable(currentPerson.getLink()),
                    updatedTags
            );
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
        if (!(other instanceof TagAssignCommand)) {
            return false;
        }
        TagAssignCommand otherCommand = (TagAssignCommand) other;
        return indexes.equals(otherCommand.indexes) && tag.equals(otherCommand.tag);
    }
}
