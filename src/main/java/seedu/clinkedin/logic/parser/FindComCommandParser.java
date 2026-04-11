package seedu.clinkedin.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.logic.commands.FindComCommand;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new {@code FindComCommand} object.
 */
public class FindComCommandParser implements Parser<FindComCommand> {

    private static final Logger logger = LogsCenter.getLogger(FindComCommandParser.class);

    /**
     * Parses the given {@code args} in the context of the {@code FindComCommand}
     * and returns a {@code FindComCommand} object for execution.
     *
     * @param args Raw user input containing one or more company keywords separated by semicolons.
     * @return A {@code FindComCommand} containing the parsed company keywords.
     * @throws NullPointerException if {@code args} is null.
     * @throws ParseException if the input does not contain any valid company keyword.
     */
    @Override
    public FindComCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
        }

        String[] companyKeywordsArray = trimmedArgs.split(";", -1);
        for (String keyword : companyKeywordsArray) {
            if (keyword.trim().isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
            }
        }

        List<String> companyKeywords = Arrays.stream(companyKeywordsArray)
                .map(String::trim)
                .toList();

        logger.info("Parsed findcom keywords: " + companyKeywords);
        return new FindComCommand(new CompanyContainsKeywordsPredicate(companyKeywords));
    }
}
