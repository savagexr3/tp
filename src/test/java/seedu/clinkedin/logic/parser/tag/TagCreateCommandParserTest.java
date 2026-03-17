package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagCreateCommand;
import seedu.clinkedin.model.tag.Tag;

public class TagCreateCommandParserTest {
    private final TagCreateCommandParser parser = new TagCreateCommandParser();

    @Test
    public void parse_validArgs_success() {
        Tag expectedTag = new Tag("friends");
        assertParseSuccess(parser, "friends", new TagCreateCommand(expectedTag));
    }

    @Test
    public void parse_whitespaceBeforeAndAfter_success() {
        Tag expectedTag = new Tag("friends");
        assertParseSuccess(parser, "   friends   ", new TagCreateCommand(expectedTag));
    }

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCreateCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_blankArg_failure() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCreateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTag_failure() {
        assertParseFailure(parser, "friends!",
                Tag.MESSAGE_INVALID_CHARACTERS);
    }
}
