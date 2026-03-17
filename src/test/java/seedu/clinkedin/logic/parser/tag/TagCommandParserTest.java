package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagListCommand;

public class TagCommandParserTest {

    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_tagListCommand_returnsTagListCommand() throws Exception {
        assertParseSuccess(parser, "list", new TagListCommand());
    }
}