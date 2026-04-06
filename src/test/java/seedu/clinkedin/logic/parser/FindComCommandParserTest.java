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
    public void parse_oneValidOneEmptyKeyword_ignoresEmptyAndSucceeds() {
        // Heuristic for multiple inputs: empty optional components after splitting are removed,
        // while remaining valid components should still produce a positive test case.
        FindComCommand expectedFindComCommand =
                new FindComCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("Google")));

        assertParseSuccess(parser, "Google;   ;", expectedFindComCommand);
    }
}
