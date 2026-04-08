package seedu.clinkedin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.commons.core.Version;

public class MainAppTest {

    @Test
    public void version_isCorrect() {
        assertNotNull(MainApp.VERSION);
        assertEquals(new Version(1, 5, 1, true), MainApp.VERSION);
    }
}
