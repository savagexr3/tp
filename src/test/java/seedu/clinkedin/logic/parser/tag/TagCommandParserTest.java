package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.logic.commands.tag.TagListCommand;
import seedu.clinkedin.logic.commands.tag.TagRenameCommand;
import seedu.clinkedin.model.tag.Tag;

public class TagCommandParserTest {

    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_tagListCommand_returnsTagListCommand() throws Exception {
        assertParseSuccess(parser, "list", new TagListCommand());
    }

    @Test
    public void parse_renameCommandWord_returnsTagRenameCommand() {
        assertParseSuccess(parser, "rename friends colleagues",
                new TagRenameCommand(new Tag("friends"), new Tag("colleagues")));
    }
}
