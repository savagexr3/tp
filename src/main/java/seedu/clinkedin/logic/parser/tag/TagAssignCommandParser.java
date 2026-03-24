package seedu.clinkedin.logic.parser.tag;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

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

        // parse comma-separated indexes
        String[] indexStrings = parts[0].split(",");
        if (indexStrings.length == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
        }

        List<Index> indexes = new ArrayList<>();
        for (String indexString : indexStrings) {
            String trimmedIndex = indexString.trim();
            if (trimmedIndex.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
            }
            try {
                indexes.add(ParserUtil.parseIndex(trimmedIndex));
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe);
            }
        }

        String tagNameError = Tag.getTagNameValidationError(parts[1].trim());
        if (tagNameError != null) {
            throw new ParseException(tagNameError);
        }

        Tag tag = new Tag(parts[1].trim());
        return new TagAssignCommand(indexes, tag);
    }
}
