package seedu.clinkedin.logic.parser;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.clinkedin.logic.commands.FindComCommand;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindComCommand object
 */
public class FindComCommandParser implements Parser<FindComCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindComCommand
     * and returns a FindComCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindComCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
        }

        String[] companyNameKeywords = trimmedArgs.split(";");
        companyNameKeywords = Arrays.stream(companyNameKeywords).map(x -> x.trim()).toArray(String[]::new);

        return new FindComCommand(new CompanyContainsKeywordsPredicate(Arrays.asList(companyNameKeywords)));
    }

}
