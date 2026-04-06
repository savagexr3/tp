package seedu.clinkedin.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.commands.EditCommand;
import seedu.clinkedin.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Remark;
import seedu.clinkedin.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code EditCommand} object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code args} in the context of the {@code EditCommand}
     * and returns an {@code EditCommand} object for execution.
     *
     * @throws NullPointerException if {@code args} is null.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_COMPANY, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_LINK, PREFIX_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COMPANY,
                PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_LINK);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            Optional<Company> parsedCompany = ParserUtil.parseCompanyForEdit(argMultimap.getValue(PREFIX_COMPANY));
            if (parsedCompany.isPresent()) {
                editPersonDescriptor.setCompany(parsedCompany.get());
            } else {
                editPersonDescriptor.clearCompany();
            }
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            Optional<Remark> parsedRemark = ParserUtil.parseRemarkForEdit(argMultimap.getValue(PREFIX_REMARK));
            if (parsedRemark.isPresent()) {
                editPersonDescriptor.setRemark(parsedRemark.get());
            } else {
                editPersonDescriptor.clearRemark();
            }
        }
        if (argMultimap.getValue(PREFIX_LINK).isPresent()) {
            String linkValue = argMultimap.getValue(PREFIX_LINK).get().trim();
            if (linkValue.isEmpty()) {
                editPersonDescriptor.clearLink();
            } else {
                Optional<Link> parsedLink = ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK));
                parsedLink.ifPresent(editPersonDescriptor::setLink);
            }
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contains only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     *
     * @throws NullPointerException if {@code tags} is null.
     * @throws ParseException if any tag is invalid.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        requireNonNull(tags);

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
