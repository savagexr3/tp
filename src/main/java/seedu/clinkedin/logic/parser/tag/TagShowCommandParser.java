package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinkedin.logic.commands.tag.TagShowCommand;
import seedu.clinkedin.logic.parser.Parser;
import seedu.clinkedin.logic.parser.ParserUtil;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.model.tag.TagContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new TagShowCommand object
 */
public class TagShowCommandParser implements Parser<TagShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagShowCommand
     * and returns a TagShowCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TagShowCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagShowCommand.MESSAGE_USAGE));
        }

        if (!trimmedArgs.matches("\\p{Alnum}+")) {
            throw new ParseException("This command only accepts 1 tag as parameter and has to be alphanumeric!");
        }

        Tag tag = ParserUtil.parseTag(trimmedArgs);

        return new TagShowCommand(new TagContainsKeywordPredicate(tag), tag);
    }
}
