package seedu.clinkedin.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.clinkedin.commons.util.ToStringBuilder;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.logic.commands.exceptions.CommandException;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_LINK + "LINK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_COMPANY + "Google "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_LINK + "https://www.linkedin.com/feed/"
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PHONE = "Duplicate phone number detected";
    public static final String MESSAGE_TAGS_DO_NOT_EXIST = "These tags do not exist, please create them!: ";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.hasPhoneNumber(toAdd.getPhone())) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        // Checks if tag exist
        ArrayList<Tag> nonExistentTags = new ArrayList<>();
        for (Tag tag : toAdd.getTags()) {
            if (!model.hasTag(tag)) {
                nonExistentTags.add(tag);
            }
        }

        if (!nonExistentTags.isEmpty()) {
            List<String> tagList = nonExistentTags.stream().map(x -> x.toString()).collect(Collectors.toList());
            throw new CommandException(MESSAGE_TAGS_DO_NOT_EXIST + String.join(", ", tagList));
        }

        Person toAddWithExistingTags = getPersonWithTags(toAdd, model);

        model.addPerson(toAddWithExistingTags);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAddWithExistingTags)));
    }

    private static Person getPersonWithTags(Person person, Model model) {
        assert person != null;

        Set<Tag> existingTags = model.getCLinkedin()
                .getTagList()
                .stream()
                .filter(x -> person.getTags().contains(x))
                .collect(Collectors.toSet());


        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                Optional.ofNullable(person.getCompany()),
                person.getAddress(),
                Optional.ofNullable(person.getRemark()),
                Optional.ofNullable(person.getLink()),
                person.getDateAdded(),
                existingTags
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
