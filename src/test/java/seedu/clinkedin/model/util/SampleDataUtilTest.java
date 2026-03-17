package seedu.clinkedin.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.clinkedin.model.ReadOnlyCLinkedin;

public class SampleDataUtilTest {

    @Test
    public void getSampleData_success() {
        ReadOnlyCLinkedin sampleAb = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAb);
        assertFalse(sampleAb.getPersonList().isEmpty());
        assertFalse(sampleAb.getTagList().isEmpty());
    }
}
