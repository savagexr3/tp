package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinkedin.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagUnassignCommand;
import seedu.clinkedin.model.tag.Tag;

public class TagUnassignCommandParserTest {

    private TagUnassignCommandParser parser = new TagUnassignCommandParser();

    @Test
    public void parse_singleValidIndex_returnsTagUnassignCommand() {
        assertParseSuccess(parser, "1 friends",
                new TagUnassignCommand(List.of(INDEX_FIRST_PERSON), new Tag("friends")));
    }

    @Test
    public void parse_multipleValidIndexes_returnsTagUnassignCommand() {
        assertParseSuccess(parser, "1,2,3 friends",
                new TagUnassignCommand(
                        Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                        new Tag("friends")));
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
        assertParseFailure(parser, "0 friends", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "-1 friends", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_malformedIndexList_throwsParseException() {
        assertParseFailure(parser, "1,,2 friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagUnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagName_throwsParseException() {
        assertParseFailure(parser, "1 #invalid", Tag.MESSAGE_INVALID_CHARACTERS);
    }
}
