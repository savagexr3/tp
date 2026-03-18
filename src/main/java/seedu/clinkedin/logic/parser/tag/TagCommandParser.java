package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.Messages.MESSAGE_UNKNOWN_TAG_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.clinkedin.logic.commands.HelpCommand;
import seedu.clinkedin.logic.commands.tag.TagAssignCommand;
import seedu.clinkedin.logic.commands.tag.TagCommand;
import seedu.clinkedin.logic.commands.tag.TagCreateCommand;
import seedu.clinkedin.logic.commands.tag.TagDeleteCommand;
import seedu.clinkedin.logic.commands.tag.TagListCommand;
import seedu.clinkedin.logic.commands.tag.TagRenameCommand;
import seedu.clinkedin.logic.commands.tag.TagShowCommand;
import seedu.clinkedin.logic.commands.tag.TagUnassignCommand;
import seedu.clinkedin.logic.parser.Parser;
import seedu.clinkedin.logic.parser.exceptions.ParseException;

/**
 * Parses tag command.
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Used for initial separation of tag subcommand word and args.
     */
    private static final Pattern BASIC_TAG_COMMAND_FORMAT = Pattern.compile("(?<subcommandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of tag commands
     * and returns the appropriate Command object for execution.
     *
     * @param args full argument string after "tag"
     * @return the command based on the tag subcommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public TagCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_TAG_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String subcommandWord = matcher.group("subcommandWord");
        final String arguments = matcher.group("arguments");

        switch (subcommandWord) {

        case TagCreateCommand.COMMAND_WORD:
            return new TagCreateCommandParser().parse(arguments);
        case TagShowCommand.COMMAND_WORD:
            return new TagShowCommandParser().parse(arguments);
        case TagDeleteCommand.COMMAND_WORD:
            return new TagDeleteCommandParser().parse(arguments);
        case TagListCommand.COMMAND_WORD:
            return new TagListCommand();
        case TagAssignCommand.COMMAND_WORD:
            return new TagAssignCommandParser().parse(arguments);
        case TagUnassignCommand.COMMAND_WORD:
            return new TagUnassignCommandParser().parse(arguments);
        case TagRenameCommand.COMMAND_WORD:
            return new TagRenameCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_TAG_COMMAND);
        }
    }
}
