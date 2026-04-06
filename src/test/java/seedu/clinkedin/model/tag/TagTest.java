package seedu.clinkedin.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void constructor_invalidColorName_throwsIllegalArgumentException() {
        String invalidColorName = "bloo";
        assertThrows(IllegalArgumentException.class, () -> new Tag("s", invalidColorName));
    }

    public void getTagNameValidationError() {
        // EP: null tag name
        assertEquals(Tag.MESSAGE_NULL, Tag.getTagNameValidationError(null));

        // EP: empty tag name
        assertEquals(Tag.MESSAGE_EMPTY, Tag.getTagNameValidationError(""));

        // BVA: length = 21, just above max valid length 20
        assertEquals(Tag.MESSAGE_TOO_LONG, Tag.getTagNameValidationError("a".repeat(21)));

        // EP: invalid characters
        assertEquals(Tag.MESSAGE_INVALID_CHARACTERS, Tag.getTagNameValidationError("friends!"));
        assertEquals(Tag.MESSAGE_INVALID_CHARACTERS, Tag.getTagNameValidationError("cs2103 t"));
        assertEquals(Tag.MESSAGE_INVALID_CHARACTERS, Tag.getTagNameValidationError("tag-1"));

        // BVA: length = 20, max valid length
        assertEquals(null, Tag.getTagNameValidationError("a".repeat(20)));

        // EP: valid tags
        assertEquals(null, Tag.getTagNameValidationError("friends"));
        assertEquals(null, Tag.getTagNameValidationError("CS2103"));
        assertEquals(null, Tag.getTagNameValidationError("tag123"));
    }

    @Test
    public void isValidTagName() {
        // EP: null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // EP: invalid characters
        assertFalse(Tag.isValidTagName("friends!"));

        // BVA: length = 20, max valid length
        assertTrue(Tag.isValidTagName("a".repeat(20)));

        // BVA: length = 21, just above max valid length 20
        assertFalse(Tag.isValidTagName("a".repeat(21)));
    }

    @Test
    public void getColorNameValidationError() {
        // Invalid color name
        assertEquals(Tag.MESSAGE_INVALID_COLOR_NAME, Tag.getColorNameValidationError("bloo"));
    }

    @Test
    public void isValidColorName() {
        // null color name
        assertThrows(NullPointerException.class, () -> Tag.isValidColorName(null));
    }
}
