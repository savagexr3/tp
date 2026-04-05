package seedu.clinkedin.logic.parser;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.commands.RestoreCommand;
import seedu.clinkedin.logic.parser.exceptions.ParseException;

//@@author rxlee04
/**
 * Parses input arguments and creates a new RestoreCommand object.
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a RestoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RestoreCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RestoreCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE), pe);
        }
    }
}
