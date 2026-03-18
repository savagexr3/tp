package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagRenameCommand;
import seedu.clinkedin.model.tag.Tag;

public class TagRenameCommandParserTest {

    private TagRenameCommandParser parser = new TagRenameCommandParser();

    @Test
    public void parse_validArgs_returnsTagRenameCommand() {
        Tag oldTag = new Tag("friends");
        Tag newTag = new Tag("colleagues");
        TagRenameCommand expectedCommand = new TagRenameCommand(oldTag, newTag);

        assertParseSuccess(parser, "friends colleagues", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRenameCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "friends", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, "friends colleagues family", expectedMessage);
    }
}
