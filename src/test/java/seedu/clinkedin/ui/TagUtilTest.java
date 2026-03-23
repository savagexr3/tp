package seedu.clinkedin.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;

/**
 *
 */
public class TagUtilTest {

    @Test
    public void execute_validLabelValidColor_returnCorrectLabel() {
        // Toolkit not initialized error without this line
        JFXPanel fxPanel = new JFXPanel();

        String tagName = "friends";
        String color = "blue";
        Label tagLabel = new Label();
        tagLabel = TagUtil.tagLabel(tagName, color);
        assertTrue(tagLabel.getStyle().contains("#0000FF"));
        assertTrue(tagLabel.getText().equalsIgnoreCase(tagName));
    }
}
