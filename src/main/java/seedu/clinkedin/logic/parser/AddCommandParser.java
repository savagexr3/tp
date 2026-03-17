package seedu.clinkedin.logic.parser;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.clinkedin.logic.commands.AddCommand;
import seedu.clinkedin.logic.parser.exceptions.ParseException;
import seedu.clinkedin.model.person.Address;
import seedu.clinkedin.model.person.Company;
import seedu.clinkedin.model.person.Email;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.model.person.Name;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.person.Phone;
import seedu.clinkedin.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_COMPANY, PREFIX_ADDRESS, PREFIX_LINK, PREFIX_TAG);

        checkFields(argMultimap);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COMPANY,
                PREFIX_ADDRESS, PREFIX_LINK);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Company company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).orElse(""));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Optional<Link> link = ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, company, address, link, tagList);

        return new AddCommand(person);
    }

    /**
     * Checks if any required prefixes are missing and throws a ParseException with a detailed error message if so.
     */
    private void checkFields(ArgumentMultimap argumentMultimap) throws ParseException {
        List<String> fields = new ArrayList<>();

        if (argumentMultimap.getValue(PREFIX_NAME).isEmpty()) {
            fields.add("NAME");
        }
        if (argumentMultimap.getValue(PREFIX_PHONE).isEmpty()) {
            fields.add("PHONE");
        }
        if (argumentMultimap.getValue(PREFIX_EMAIL).isEmpty()) {
            fields.add("EMAIL");
        }
        if (argumentMultimap.getValue(PREFIX_ADDRESS).isEmpty()) {
            fields.add("ADDRESS");
        }
        if (!fields.isEmpty()) {
            String errorMessage = "Invalid command format! Missing required fields: "
                    + String.join(", ", fields) + ".\n"
                    + AddCommand.MESSAGE_USAGE;
            throw new ParseException(errorMessage);
        }
    }
}
