package seedu.clinkedin.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TagUtilTest {

    @Test
    public void tagUtil_blueColor_hasRightHex() {
        String blueHex = TagUtil.tagColorToHexString("blue");
        assertEquals("#0000FFFF", blueHex);
    }

    @Test
    public void tagUtil_yellowColorTooBright_isTrue() {
        boolean isTooBright = TagUtil.colorIsTooBright("yellow");
        assertEquals(true, isTooBright);
    }

    @Test
    public void tagUtil_blackColorTooBright_isFalse() {
        boolean isTooBright = TagUtil.colorIsTooBright("black");
        assertEquals(false, isTooBright);
    }
}
