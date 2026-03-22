package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinkedin.logic.commands.tag.TagCreateCommand;
import seedu.clinkedin.logic.parser.Parser;
import seedu.clinkedin.logic.parser.ParserUtil;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCreateCommand object
 */
public class TagCreateCommandParser implements Parser<TagCreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCreateCommand
     * and returns a TagCreateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TagCreateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+", 2);
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCreateCommand.MESSAGE_USAGE));
        }

        if (parts.length == 2) {
            return new TagCreateCommand(ParserUtil.parseTag(parts[0], parts[1]));
        }

        Tag tag = ParserUtil.parseTag(trimmedArgs);

        return new TagCreateCommand(tag);
    }
}
