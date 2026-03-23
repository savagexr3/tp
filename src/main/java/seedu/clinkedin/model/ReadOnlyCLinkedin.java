package seedu.clinkedin.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.clinkedin.model.person.DeletedPersonRecord;
import seedu.clinkedin.model.person.Person;
import seedu.clinkedin.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCLinkedin {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the deleted person records list.
     */
    List<DeletedPersonRecord> getDeletedPersonRecords();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
