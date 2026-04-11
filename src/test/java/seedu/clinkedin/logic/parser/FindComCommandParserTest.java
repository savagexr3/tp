package seedu.clinkedin.logic.parser;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.FindComCommand;
import seedu.clinkedin.model.person.CompanyContainsKeywordsPredicate;

public class FindComCommandParserTest {

    private final FindComCommandParser parser = new FindComCommandParser();

    @Test
    public void parse_nullInput_throwsNullPointerException() {
        // EP: required parser input is null
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        // EP: whitespace-only input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlySeparatorsAndEmptyKeywords_throwsParseException() {
        // Heuristic for multiple input: invalid keyword inputs are tested individually and after filtering.
        // EP: semicolon-separated input where all components become empty after trim
        assertParseFailure(parser, " ; ; \t ; ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsTwoCompanies_returnsFindComCommand() {
        // Combination strategy: at least once over representative valid keyword combinations.
        FindComCommand expectedFindComCommand =
                new FindComCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("Google", "Shopee")));

        // EP: two simple valid company keywords with no surrounding whitespace
        assertParseSuccess(parser, "Google;Shopee", expectedFindComCommand);

        // EP: valid keywords with extra surrounding whitespace
        assertParseSuccess(parser, " \n Google; \n \t Shopee  \t", expectedFindComCommand);
    }

    @Test
    public void parse_validArgsCompanyWithSpace_returnsFindComCommand() {
        // EP: one valid company keyword containing spaces and no semicolon
        FindComCommand expectedFindComCommand =
                new FindComCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("NUS Computing")));

        assertParseSuccess(parser, "NUS Computing", expectedFindComCommand);
    }

    @Test
    public void parse_validArgsMultipleCompaniesWithSpaces_returnsFindComCommand() {
        // Heuristic: each valid keyword category should appear in at least one positive test case.
        FindComCommand expectedFindComCommand =
                new FindComCommand(new CompanyContainsKeywordsPredicate(
                        Arrays.asList("NUS Computing", "GovTech", "Sea Limited")));

        assertParseSuccess(parser, "NUS Computing; GovTech; Sea Limited", expectedFindComCommand);
    }

    @Test
    public void parse_oneValidOneEmptyKeyword_throwsParseException() {
        // Heuristic for multiple inputs: only one invalid input
        // (an empty keyword segment after trimming) should appear in one negative test case.
        assertParseFailure(parser, "Google; ;",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_validKeywordWithTrailingSeparator_throwsParseException() {
        // EP: one valid keyword followed by one empty keyword segment
        assertParseFailure(parser,
                "Google;",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_leadingSeparatorBeforeValidKeyword_throwsParseException() {
        // EP: one empty keyword segment followed by one valid keyword
        assertParseFailure(parser,
                ";Google",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlySeparator_throwsParseException() {
        // EP: semicolon input where all keyword segments are empty
        assertParseFailure(parser,
                ";",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindComCommand.MESSAGE_USAGE));
    }
}
