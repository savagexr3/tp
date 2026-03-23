package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinkedin.logic.commands.tag.TagAssignCommand;
import seedu.clinkedin.logic.commands.tag.TagColorCommand;
import seedu.clinkedin.logic.parser.Parser;
import seedu.clinkedin.logic.parser.ParserUtil;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.tag.Tag;

public class TagColorCommandParser implements Parser<TagColorCommand> {
    @Override
    public TagColorCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        String[] parts = trimmed.split("\\s+", 2);

        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagColorCommand.MESSAGE_USAGE));
        }

        Tag tag = ParserUtil.parseTag(parts[0], parts[1]);

        return new TagColorCommand(tag, parts[1]);
    }
}
