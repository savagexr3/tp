package seedu.clinkedin.logic.commands;

import static seedu.clinkedin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinkedin.testutil.TypicalPersons.getTypicalCLinkedin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.Model;
import seedu.clinkedin.model.ModelManager;
import seedu.clinkedin.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SortComCommand}.
 */
public class SortComCommandIntegrationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalCLinkedin(), new UserPrefs());
    }

    @Test
    public void execute_sortDisplayedList_success() {
        // Integration: verifies real model sorting behavior and command result.
        SortComCommand command = new SortComCommand();
        expectedModel.sortFilteredPersonListByCompany();

        assertCommandSuccess(command, model, SortComCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
