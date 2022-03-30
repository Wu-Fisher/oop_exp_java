package junit.demo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AssertTest {
    @Test
    void testAssertEqual() {
        assertEquals("ABC", "ABC");
        assertEquals(20, 20, "optional assertion message");
        assertEquals(2 + 2, 4);
        assertThrows(expectedType, () -> {
            throw new Exception();
        });
    }

    @Test
    void testAssertFalse() {
        assertFalse("FirstName".length() == 2);
        assertFalse(10 < 20, "assertion message: test assert false");
    }

    @Test
    void testAssertNull() {
        String str1 = null;
        String str2 = "abc";
        assertNull(str1);
        assertNotNull(str2);
    }

    @Test
    void testAssertAll() {
        String str1 = "abc";
        String str2 = "pqr";
        String str3 = "xyz";
        assertAll(
                () -> assertEquals(str1, "abc"), () -> assertEquals(str2, "pqr"), () -> assertEquals(str3, "zzz"));
    }
}
