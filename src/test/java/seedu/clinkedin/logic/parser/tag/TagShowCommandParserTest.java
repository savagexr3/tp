package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagShowCommand;
import seedu.clinkedin.model.tag.Tag;
import seedu.clinkedin.model.tag.TagContainsKeywordPredicate;

public class TagShowCommandParserTest {
    private TagShowCommandParser parser = new TagShowCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagShowCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsTagShowCommand() {
        Tag tag = new Tag("friends");
        TagShowCommand expectedCommand =
                new TagShowCommand(new TagContainsKeywordPredicate(tag), tag);
        assertParseSuccess(parser, "friends", expectedCommand);
    }
}
