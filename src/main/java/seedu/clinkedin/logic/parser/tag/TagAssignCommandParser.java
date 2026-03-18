package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.commands.tag.TagAssignCommand;
import seedu.clinkedin.logic.parser.Parser;
import seedu.clinkedin.logic.parser.ParserUtil;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagAssignCommand object.
 */
public class TagAssignCommandParser implements Parser<TagAssignCommand> {

    @Override
    public TagAssignCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        String[] parts = trimmed.split("\\s+", 2);

        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe);
        }

        String tagNameError = Tag.getTagNameValidationError(parts[1]);
        if (tagNameError != null) {
            throw new ParseException(tagNameError);
        }

        Tag tag = new Tag(parts[1]);
        return new TagAssignCommand(index, tag);
    }
}
