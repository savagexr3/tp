package seedu.clinkedin.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.clinkedin.commons.core.LogsCenter;
import seedu.clinkedin.model.person.DeletedPersonRecord;

/**
 * Panel containing the list of deleted person records.
 */
public class DeletedPersonRecordListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeletedPersonRecordListPanel.class);

    @FXML
    private ListView<DeletedPersonRecord> personListView;

    /**
     * Creates a {@code DeletedPersonRecordListPanel} with the given {@code ObservableList}.
     */
    public DeletedPersonRecordListPanel(ObservableList<DeletedPersonRecord> deletedList) {
        super(FXML);
        personListView.setItems(deletedList);
        personListView.setCellFactory(listView -> new DeletedPersonRecordListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DeletedPersonRecord}
     * using a {@code PersonCard}.
     */
    class DeletedPersonRecordListViewCell extends ListCell<DeletedPersonRecord> {
        @Override
        protected void updateItem(DeletedPersonRecord record, boolean empty) {
            super.updateItem(record, empty);

            if (empty || record == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(record, getIndex() + 1).getRoot());
            }
        }
    }

}
