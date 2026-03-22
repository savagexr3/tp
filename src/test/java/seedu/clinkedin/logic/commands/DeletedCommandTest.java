package seedu.clinkedin.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;
import seedu.clinkedin.model.person.Person;

public class DeletedCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());

        // create some deleted records first
        Person personToDelete = model.getFilteredPersonList().get(0);
        model.deletePerson(personToDelete);

        expectedModel = new ModelManager(model.getCLinkedin(), new UserPrefs());
    }

    @Test
    public void execute_showsDeletedRecords() {
        DeletedCommand command = new DeletedCommand();

        CommandResult result = command.execute(model);

        assertEquals(DeletedCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(1, model.getFilteredDeletedPersonRecordList().size());
    }
}
