package seedu.clinkedin.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.logic.Messages.MESSAGE_INVALID_DELETED_PERSON_RECORD_DISPLAYED_INDEX;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.clinkedin.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.clinkedin.testutil.TypicalPersons.ALICE;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.core.index.Index;
import seedu.clinkedin.logic.Messages;
import seedu.clinkedin.model.CLinkedin;
import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.person.DeletedPersonRecord;

public class RestoreCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        model.deletePerson(ALICE);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        RestoreCommand restoreCommand = new RestoreCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS,
                Messages.format(ALICE));

        Model expectedModel = new ModelManager(new CLinkedin(model.getCLinkedin()), new UserPrefs());
        DeletedPersonRecord expectedRecordToRestore = expectedModel.getFilteredDeletedPersonRecordList().get(0);
        expectedModel.restorePerson(expectedRecordToRestore);

        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeletedPersonRecordList().size() + 1);
        RestoreCommand restoreCommand = new RestoreCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, MESSAGE_INVALID_DELETED_PERSON_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        model.addPerson(ALICE);

        RestoreCommand restoreCommand = new RestoreCommand(Index.fromZeroBased(0));

        assertCommandFailure(restoreCommand, model, RestoreCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals() {
        RestoreCommand restoreFirstCommand = new RestoreCommand(INDEX_FIRST_PERSON);
        RestoreCommand restoreSecondCommand = new RestoreCommand(INDEX_SECOND_PERSON);

        assertTrue(restoreFirstCommand.equals(restoreFirstCommand));
        assertTrue(restoreFirstCommand.equals(new RestoreCommand(INDEX_FIRST_PERSON)));

        assertFalse(restoreFirstCommand.equals(1));
        assertFalse(restoreFirstCommand.equals(null));
        assertFalse(restoreFirstCommand.equals(restoreSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        RestoreCommand restoreCommand = new RestoreCommand(targetIndex);
        String expected = RestoreCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, restoreCommand.toString());
    }
}
