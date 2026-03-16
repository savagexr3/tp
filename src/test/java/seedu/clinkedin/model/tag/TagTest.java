package seedu.clinkedin.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void getTagNameValidationError() {
        // null
        assertEquals(Tag.MESSAGE_NULL, Tag.getTagNameValidationError(null));

        // empty
        assertEquals(Tag.MESSAGE_EMPTY, Tag.getTagNameValidationError(""));

        // too long
        assertEquals(Tag.MESSAGE_TOO_LONG,
                Tag.getTagNameValidationError("a".repeat(21)));

        // invalid characters
        assertEquals(Tag.MESSAGE_INVALID_CHARACTERS,
                Tag.getTagNameValidationError("friends!"));
        assertEquals(Tag.MESSAGE_INVALID_CHARACTERS,
                Tag.getTagNameValidationError("cs2103 t"));
        assertEquals(Tag.MESSAGE_INVALID_CHARACTERS,
                Tag.getTagNameValidationError("tag-1"));

        // valid tags
        assertEquals(null, Tag.getTagNameValidationError("friends"));
        assertEquals(null, Tag.getTagNameValidationError("CS2103"));
        assertEquals(null, Tag.getTagNameValidationError("tag123"));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

}
