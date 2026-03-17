package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinkedin.logic.commands.tag.TagDeleteCommand;
import seedu.clinkedin.logic.parser.Parser;
import seedu.clinkedin.logic.parser.ParserUtil;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.tag.Tag;

/**
 * Parses input arguments and creates a {@code TagDeleteCommand} object.
 */
public class TagDeleteCommandParser implements Parser<TagDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagDeleteCommand
     * and returns a TagDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TagDeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagDeleteCommand.MESSAGE_USAGE));
        }

        Tag tag = ParserUtil.parseTag(trimmedArgs);

        return new TagDeleteCommand(tag);
    }
}
