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

import seedu.clinkedin.logic.commands.tag.TagAssignCommand;
import seedu.clinkedin.model.tag.Tag;

public class TagAssignCommandParserTest {

    private TagAssignCommandParser parser = new TagAssignCommandParser();

    @Test
    public void parse_singleValidIndex_returnsTagAssignCommand() {
        assertParseSuccess(parser, "1 friends",
                new TagAssignCommand(List.of(INDEX_FIRST_PERSON), new Tag("friends")));
    }

    @Test
    public void parse_multipleValidIndexes_returnsTagAssignCommand() {
        assertParseSuccess(parser, "1,2,3 friends",
                new TagAssignCommand(
                        Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                        new Tag("friends")));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTag_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 friends", MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "-1 friends", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_malformedIndexList_throwsParseException() {
        assertParseFailure(parser, "1,,2 friends",
                "Indexes should not contain consecutive commas.");
    }

    @Test
    public void parse_invalidTagName_throwsParseException() {
        assertParseFailure(parser, "1 #invalid", Tag.MESSAGE_INVALID_CHARACTERS);
    }

    @Test
    public void parse_spaceSeparatedIndexes_throwsParseException() {
        assertParseFailure(parser, "1 2 3 friends",
                "Indexes must be comma-separated (e.g. 1,2,3).");
    }

    @Test
    public void parse_leadingComma_throwsParseException() {
        assertParseFailure(parser, ",1,2 friends",
                "Indexes should not start with a comma.");
    }

    @Test
    public void parse_trailingComma_throwsParseException() {
        assertParseFailure(parser, "1,2, friends",
                "Indexes should not end with a comma.");
    }
}
