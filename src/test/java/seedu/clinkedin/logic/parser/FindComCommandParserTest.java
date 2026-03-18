package seedu.clinkedin.logic.parser;


import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.FindComCommand;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;

public class FindComCommandParserTest {

    private FindComCommandParser parser = new FindComCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsTwoCompanies_returnsFindComCommand() {
        FindComCommand expectedFindComCommand =
                new FindComCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("Google", "Shopee")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "Google;Shopee", expectedFindComCommand);

        // with extra whitespaces around separators
        assertParseSuccess(parser, " \n Google; \n \t Shopee  \t", expectedFindComCommand);
    }

    @Test
    public void parse_validArgsCompanyWithSpace_returnsFindComCommand() {
        FindComCommand expectedFindComCommand =
                new FindComCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("NUS Computing")));

        // company name with spaces should remain one keyword if no semicolon
        assertParseSuccess(parser, "NUS Computing", expectedFindComCommand);
    }

    @Test
    public void parse_validArgsMultipleCompaniesWithSpaces_returnsFindComCommand() {
        FindComCommand expectedFindComCommand =
                new FindComCommand(new CompanyContainsKeywordsPredicate(
                        Arrays.asList("NUS Computing", "GovTech", "Sea Limited")));

        assertParseSuccess(parser, "NUS Computing; GovTech; Sea Limited", expectedFindComCommand);
    }
}
