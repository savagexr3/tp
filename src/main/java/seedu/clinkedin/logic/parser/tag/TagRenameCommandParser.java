package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinkedin.logic.commands.tag.TagRenameCommand;
import seedu.clinkedin.logic.parser.Parser;
import seedu.clinkedin.logic.parser.ParserUtil;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagRenameCommand object
 */
public class TagRenameCommandParser implements Parser<TagRenameCommand> {

    @Override
    public TagRenameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] tags = trimmedArgs.split("\\s+");

        if (tags.length > 2) {
            throw new ParseException(
                    String.format(TagRenameCommand.MESSAGE_SPACE_IN_TAG, TagRenameCommand.MESSAGE_USAGE));
        }

        if (tags.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRenameCommand.MESSAGE_USAGE));
        }

        Tag oldTag = ParserUtil.parseTag(tags[0]);
        Tag newTag = ParserUtil.parseTag(tags[1]);

        return new TagRenameCommand(oldTag, newTag);
    }
}
