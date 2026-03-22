package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Assigns an existing tag to a person in the address book.
 */
public class TagAssignCommand extends TagCommand {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an existing tag to a person.\n"
            + "Parameters: PERSON_INDEX TAG_NAME\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 friends";

    public static final String MESSAGE_SUCCESS = "Tag assigned successfully.";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag does not exist.";
    public static final String MESSAGE_TAG_ALREADY_ASSIGNED = "Tag already assigned to this contact.";

    private final Index index;
    private final Tag tag;

    /**
     * Creates a TagAssignCommand to assign the specified {@code Tag} to the person at {@code index}.
     */
    public TagAssignCommand(Index index, Tag tag) {
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

        if (!model.hasTag(tag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (personToEdit.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_TAG_ALREADY_ASSIGNED);
        }

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        for (Tag t : model.getCLinkedin().getTagList()) {
            if (t.tagName.equalsIgnoreCase(tag.tagName)) {
                updatedTags.add(t);
                break;
            }
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getCompany(),
                personToEdit.getAddress(),
                Optional.ofNullable(personToEdit.getLink()),
                updatedTags
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
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
        return index.equals(otherCommand.index) && tag.equals(otherCommand.tag);
    }
}
