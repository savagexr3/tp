package seedu.clinkedin.model.tag.exceptions;

//@@author rxlee04
/**
 * Signals that the operation will result in duplicate Tags (Tags are considered duplicates if they have the same
 * name).
 */
public class DuplicateTagException extends RuntimeException {
    public DuplicateTagException() {
        super("Operation would result in duplicate tags");
    }
}
