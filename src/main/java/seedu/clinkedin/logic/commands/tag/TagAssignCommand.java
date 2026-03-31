package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public static final String MESSAGE_TAG_ALREADY_ASSIGNED = "Tag already assigned to contacts at index(es): %s.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index: %d.";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate indexes provided: %s.";

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

    /**
     * Returns the existing tag from the model that matches the given tag name,
     * preserving properties like color.
     */
    private Tag getExistingTag(Model model, Tag tag) {
        for (Tag t : model.getCLinkedin().getTagList()) {
            if (t.tagName.equalsIgnoreCase(tag.tagName)) {
                return t;
            }
        }
        return tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // check for duplicate indexes
        Set<Integer> seen = new HashSet<>();
        List<Integer> duplicates = new ArrayList<>();
        for (Index index : indexes) {
            if (!seen.add(index.getZeroBased())) {
                if (!duplicates.contains(index.getOneBased())) {
                    duplicates.add(index.getOneBased());
                }
            }
        }
        if (!duplicates.isEmpty()) {
            String duplicateList = duplicates.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new CommandException(
                    String.format(MESSAGE_DUPLICATE_INDEX, duplicateList));
        }

        // validate all indexes
        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
            }
        }

        // resolve tag with color preserved (case-insensitive)
        Tag resolvedTag = getExistingTag(model, tag);

        if (!model.hasTag(resolvedTag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        // check for already assigned tags — collect all indexes
        List<Integer> alreadyAssignedIndexes = new ArrayList<>();
        for (Index index : indexes) {
            Person person = lastShownList.get(index.getZeroBased());
            if (person.getTags().contains(resolvedTag)) {
                alreadyAssignedIndexes.add(index.getOneBased());
            }
        }
        if (!alreadyAssignedIndexes.isEmpty()) {
            String assignedList = alreadyAssignedIndexes.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new CommandException(
                    String.format(MESSAGE_TAG_ALREADY_ASSIGNED, assignedList));
        }

        // apply tag to all persons
        List<Person> snapshot = new ArrayList<>(lastShownList);
        for (Index index : indexes) {
            Person personToEdit = snapshot.get(index.getZeroBased());
            Person currentPerson = model.getFilteredPersonList().get(index.getZeroBased());
            Set<Tag> updatedTags = new HashSet<>(currentPerson.getTags());
            updatedTags.add(resolvedTag);
            Person editedPerson = new Person(
                    currentPerson.getName(),
                    currentPerson.getPhone(),
                    currentPerson.getEmail(),
                    Optional.ofNullable(currentPerson.getCompany()),
                    currentPerson.getAddress(),
                    Optional.ofNullable(currentPerson.getRemark()),
                    Optional.ofNullable(currentPerson.getLink()),
                    currentPerson.getDateAdded(),
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
