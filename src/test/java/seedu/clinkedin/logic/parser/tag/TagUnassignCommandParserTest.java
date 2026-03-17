package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagUnassignCommand;
import seedu.clinkedin.model.tag.Tag;

public class TagUnassignCommandParserTest {

    private TagUnassignCommandParser parser = new TagUnassignCommandParser();

    @Test
    public void parse_validArgs_returnsTagUnassignCommand() {
        assertParseSuccess(parser, "1 friends",
                new TagUnassignCommand(INDEX_FIRST_PERSON, new Tag("friends")));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagUnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTag_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagUnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagUnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagUnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagName_throwsParseException() {
        assertParseFailure(parser, "1 #invalid", Tag.MESSAGE_INVALID_CHARACTERS);
    }
}
