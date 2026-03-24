package seedu.clinkedin.ui;

import java.awt.Desktop;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.model.person.DeletedPersonRecord;
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
    private Label dateAdded;
    @FXML
    private FlowPane tags;
    @FXML
    private Label deletedDateTime;

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
        dateAdded.setText("Added on: " + person.getDateAdded().value);
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

        deletedDateTime.setVisible(false);
        deletedDateTime.setManaged(false);
    }

    /**
     * Creates a {@code PersonCard} for a deleted person record.
     */
    public PersonCard(DeletedPersonRecord deletedPersonRecord, int displayedIndex) {
        this(deletedPersonRecord.getPerson(), displayedIndex);

        deletedDateTime.setText(formatDeletedDateTime(deletedPersonRecord.getDeletedDateTime()));
        deletedDateTime.setVisible(true);
        deletedDateTime.setManaged(true);
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

    private String formatDeletedDateTime(LocalDateTime deletedTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(deletedTime, now);

        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();

        String timeAgo;
        if (days > 0) {
            timeAgo = days + (days == 1 ? " day ago" : " days ago");
        } else if (hours > 0) {
            timeAgo = hours + (hours == 1 ? " hour ago" : " hours ago");
        } else {
            timeAgo = minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");
        String formattedDate = deletedTime.format(formatter);

        return "Deleted: " + timeAgo + " (on " + formattedDate + ")";
    }

    /**
     * Returns the deleted date-time label for testing purposes.
     * Package-private to avoid exposing UI internals publicly.
     */
    Label getDeletedDateTimeLabel() {
        return deletedDateTime;
    }
}
