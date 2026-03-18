package seedu.clinkedin.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(PersonCard.class);

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label company;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Hyperlink link;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        if (person.getLink() != null) {
            link.setText(person.getLink().value);
            link.setOnAction(e -> LinkUtil.openLink(person.getLink().value));
        } else {
            link.setVisible(false);
            link.setManaged(false);
        }
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        String companyName = person.getCompany().companyName;
        if (companyName.isEmpty()) {
            company.setManaged(false);
            company.setVisible(false);
        } else {
            company.setText(companyName);
            company.setManaged(true);
            company.setVisible(true);
        }
    }

    /**
     * Returns true if the person has a link.
     * Package-private for testing.
     */
    boolean hasLink() {
        return person.getLink() != null;
    }

    /**
     * Returns the link value, or null if no link.
     * Package-private for testing.
     */
    String getLinkValue() {
        return person.getLink() != null ? person.getLink().value : null;
    }

    /**
     * Opens the given URL in the system's default web browser.
     */
    private void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                logger.warning("Desktop browsing is not supported on this system.");
            }
        } catch (Exception e) {
            logger.warning("Failed to open link: " + url + " — " + e.getMessage());
        }
    }
}
