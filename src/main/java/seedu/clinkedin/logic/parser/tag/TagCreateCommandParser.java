package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_COLOR;

import seedu.clinkedin.logic.commands.tag.TagCreateCommand;
import seedu.clinkedin.logic.parser.ArgumentMultimap;
import seedu.clinkedin.logic.parser.ArgumentTokenizer;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COLOR);
        String trimmedArgs = argMultimap.getPreamble().trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCreateCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COLOR);

        if (!argMultimap.getValue(PREFIX_COLOR).isEmpty()) {
            return new TagCreateCommand(
                    ParserUtil.parseTag(trimmedArgs, argMultimap.getValue(PREFIX_COLOR).orElse("")));
        }

        Tag tag = ParserUtil.parseTag(trimmedArgs);

        return new TagCreateCommand(tag);
    }
}
