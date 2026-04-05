package seedu.clinkedin.logic.commands.tag;

import seedu.clinkedin.logic.commands.Command;

//@@author rxlee04
/**
 * Represents a tag command with hidden internal logic and the ability to be executed.
 */
public abstract class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = "tag command requires a subcommand.\n"
            + "Examples:\n"
            + "tag list\n"
            + "tag create TAG_NAME\n"
            + "tag delete TAG_NAME\n"
            + "tag rename OLD_TAG NEW_TAG\n"
            + "tag assign PERSON_INDEX TAG_NAME\n"
            + "tag unassign PERSON_INDEX TAG_NAME\n"
            + "tag show TAG_NAME";
}
