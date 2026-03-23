package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagColorCommand;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.tag.Tag;

public class TagColorCommandParserTest {
    private TagColorCommandParser parser = new TagColorCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagColorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsTagColorCommand() throws ParseException {
        Tag tag = new Tag("friends", "gold");
        String color = "gold";
        TagColorCommand expectedCommand =
                new TagColorCommand(tag, color);
        assertParseSuccess(parser, "friends gold", expectedCommand);
    }
}
