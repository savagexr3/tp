package seedu.clinkedin.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinkedin.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.tag.exceptions.DuplicateTagException;
import seedu.clinkedin.model.tag.exceptions.TagNotFoundException;

//@@author rxlee04
public class UniqueTagListTest {
    private final UniqueTagList uniqueTagList = new UniqueTagList();

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.contains(null));
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.contains(new Tag("friends")));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        Tag friends = new Tag("friends");
        uniqueTagList.add(friends);
        assertTrue(uniqueTagList.contains(friends));
    }

    @Test
    public void add_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.add(null));
    }

    @Test
    public void add_duplicateTag_throwsDuplicateTagException() {
        Tag friends = new Tag("friends");
        uniqueTagList.add(friends);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.add(friends));
    }

    @Test
    public void remove_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.remove(null));
    }

    @Test
    public void remove_tagDoesNotExist_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.remove(new Tag("friends")));
    }

    @Test
    public void remove_existingTag_removesTag() {
        Tag friends = new Tag("friends");
        uniqueTagList.add(friends);
        uniqueTagList.remove(friends);

        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTags((List<Tag>) null));
    }

    @Test
    public void setTags_list_replacesOwnListWithProvidedList() {
        Tag friends = new Tag("friends");
        Tag colleagues = new Tag("colleagues");

        uniqueTagList.add(friends);
        List<Tag> tagList = Collections.singletonList(colleagues);
        uniqueTagList.setTags(tagList);

        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(colleagues);

        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_listWithDuplicateTags_throwsDuplicateTagException() {
        Tag friends = new Tag("friends");
        List<Tag> listWithDuplicateTags = Arrays.asList(friends, friends);

        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTags(listWithDuplicateTags));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueTagList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTagList.asUnmodifiableObservableList().toString(), uniqueTagList.toString());
    }
}
