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

        String[] tokens = trimmed.split("\\s+");
        if (tokens.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
        }

        // last token is the tag name, everything before is the index list
        String tagName = tokens[tokens.length - 1];
        String indexPart = trimmed.substring(0, trimmed.lastIndexOf(tagName)).trim();

        // check if indexes appear to be space-separated instead of comma-separated
        // allow spaces around commas (e.g. "1 , 2") but not pure space separation (e.g. "1 2 3")
        if (indexPart.matches(".*\\d+\\s+\\d+.*") && !indexPart.contains(",")) {
            throw new ParseException("Indexes must be comma-separated (e.g. 1,2,3).");
        }

        // remove all spaces to handle "1 , 2 , 3" and "1, 2, 3"
        indexPart = indexPart.replaceAll("\\s+", "");

        if (indexPart.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAssignCommand.MESSAGE_USAGE));
        }

        // check for leading comma
        if (indexPart.startsWith(",")) {
            throw new ParseException("Indexes should not start with a comma.");
        }

        // check for trailing comma
        if (indexPart.endsWith(",")) {
            throw new ParseException("Indexes should not end with a comma.");
        }

        // use -1 limit to keep trailing empty strings from consecutive/trailing commas
        String[] indexStrings = indexPart.split(",", -1);
        List<Index> indexes = new ArrayList<>();
        for (String indexString : indexStrings) {
            if (indexString.isEmpty()) {
                throw new ParseException("Indexes should not contain consecutive commas.");
            }
            try {
                indexes.add(ParserUtil.parseIndex(indexString));
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe);
            }
        }

        String tagNameError = Tag.getTagNameValidationError(tagName);
        if (tagNameError != null) {
            throw new ParseException(tagNameError);
        }

        Tag tag = new Tag(tagName);
        return new TagAssignCommand(indexes, tag);
    }
}
