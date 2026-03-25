package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public static final String MESSAGE_SUCCESS = "Tag color changed successfully.";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag does not exist.";

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

        // Replaces current list of Tags with the new Tag
        List<Tag> tagList = new ArrayList<>(model.getCLinkedin().getTagList());
        int index = tagList.indexOf(tag);
        tagList.set(index, new Tag(tag.tagName, color));
        model.setTags(tagList);

        updateWithNewTag(model);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Replaces all contacts with old tags with the new tag.
     */
    private void updateWithNewTag(Model model) {
        List<Person> personList = new ArrayList<>(model.getCLinkedin().getPersonList());

        for (Person person : personList) {
            if (person.getTags().contains(tag)) {
                Person editedPerson = editPerson(person, tag, new Tag(tag.tagName, color));
                model.setPerson(person, editedPerson);
            }
        }
    }

    /**
     * Returns a new Person with the old tag replaced by the new tag.
     */
    private static Person editPerson(Person personToEdit, Tag oldTag, Tag newTag) {
        assert personToEdit != null;
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.remove(oldTag);
        updatedTags.add(newTag);

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                Optional.ofNullable(personToEdit.getCompany()),
                personToEdit.getAddress(),
                Optional.ofNullable(personToEdit.getRemark()),
                Optional.ofNullable(personToEdit.getLink()),
                personToEdit.getDateAdded(),
                updatedTags
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagColorCommand)) {
            return false;
        }

        TagColorCommand otherTagColorCommand = (TagColorCommand) other;
        return tag.equals(otherTagColorCommand.tag) && color.equals(otherTagColorCommand.color);
    }
}
