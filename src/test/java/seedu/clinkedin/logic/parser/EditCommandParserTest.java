package seedu.clinkedin.logic.parser;

import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinkedin.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.INVALID_LINK_DESC;
import static seedu.clinkedin.logic.commands.CommandTestUtil.LINK_DESC_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.LINK_DESC_BOB;
import static seedu.clinkedin.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_LINK_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.clinkedin.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.clinkedin.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinkedin.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.logic.commands.EditCommand;
import seedu.clinkedin.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.clinkedin.model.person.Link;
import seedu.clinkedin.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid link
        assertParseFailure(parser, "1" + INVALID_LINK_DESC, Link.MESSAGE_INVALID_SCHEME);

        // duplicate link
        assertParseFailure(parser, "1" + LINK_DESC_AMY + LINK_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LINK));
    }

    @Test
    public void parse_linkFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + LINK_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withLink(VALID_LINK_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleFieldsSpecifiedIncludingLink_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + LINK_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withLink(VALID_LINK_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_fieldsSpecifiedWithoutLink_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name only
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        assertParseSuccess(parser, userInput, new EditCommand(targetIndex, descriptor));

        // phone only
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        assertParseSuccess(parser, userInput, new EditCommand(targetIndex, descriptor));

        // email only
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        assertParseSuccess(parser, userInput, new EditCommand(targetIndex, descriptor));

        // address only
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        assertParseSuccess(parser, userInput, new EditCommand(targetIndex, descriptor));

        // link only
        userInput = targetIndex.getOneBased() + LINK_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withLink(VALID_LINK_AMY).build();
        assertParseSuccess(parser, userInput, new EditCommand(targetIndex, descriptor));
    }

    @Test
    public void parse_companyFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + COMPANY_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withCompany(VALID_COMPANY_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_remarkFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + REMARK_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withRemark(VALID_REMARK_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_clearCompany_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " c/";

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.clearCompany();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_clearRemark_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " r/";

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.clearRemark();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateCompany_failure() {
        assertParseFailure(parser, "1" + COMPANY_DESC_AMY + COMPANY_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));
    }

    @Test
    public void parse_duplicateRemark_failure() {
        assertParseFailure(parser, "1" + REMARK_DESC_AMY + REMARK_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));
    }

    @Test
    public void parse_clearLink_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " l/";

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.clearLink();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
