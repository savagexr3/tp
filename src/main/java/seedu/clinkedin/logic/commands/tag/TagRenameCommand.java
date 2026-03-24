package seedu.clinkedin.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.clinkedin.logic.commands.CommandResult;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Renames an existing tag and updates all contacts with the tag.
 */
public class TagRenameCommand extends TagCommand {

    public static final String COMMAND_WORD = "rename";
    public static final String MESSAGE_SUCCESS = "Tag renamed successfully.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Renames an existing tag to a new name.\n"
            + "Parameters: OLD_TAG_NAME NEW_TAG_NAME\n"
            + "Example: tag " + COMMAND_WORD + " friends colleagues";
    public static final String MESSAGE_TAG_NOT_FOUND = "Old tag not found.";
    public static final String MESSAGE_DUPLICATE_TAG = "Tag name already exists.";

    private final Tag oldTag;
    private final Tag newTag;

    /**
     * @param oldTag the original tag to be renamed
     * @param newTag the new tag to replace the old tag
     */
    public TagRenameCommand(Tag oldTag, Tag newTag) {
        requireNonNull(oldTag);
        requireNonNull(newTag);
        this.oldTag = oldTag;
        this.newTag = newTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTag(oldTag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        if (model.hasTag(newTag)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        model.addTag(newTag);
        updateWithNewTag(model);
        model.deleteTag(oldTag);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Replaces all contacts with old tags with the new tag.
     */
    private void updateWithNewTag(Model model) {
        List<Person> personList = new ArrayList<>(model.getCLinkedin().getPersonList());

        for (Person person : personList) {
            if (person.getTags().contains(oldTag)) {
                Person editedPerson = editPerson(person, oldTag, newTag);
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
                personToEdit.getCompany(),
                personToEdit.getAddress(),
                java.util.Optional.ofNullable(personToEdit.getLink()),
                personToEdit.getDateAdded(),
                updatedTags
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TagRenameCommand)) {
            return false;
        }
        TagRenameCommand cmd = (TagRenameCommand) other;
        return oldTag.equals(cmd.oldTag) && newTag.equals(cmd.newTag);
    }
}
