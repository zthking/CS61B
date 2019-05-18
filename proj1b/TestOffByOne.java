import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        boolean actual1 = offByOne.equalChars('a', 'b');
        boolean actual2 = offByOne.equalChars('c', 'e');
        boolean actual3 = offByOne.equalChars('&', '%');
        boolean actual4 = offByOne.equalChars('a', 'B');
        assertTrue(actual1);
        assertFalse(actual2);
        assertTrue(actual3);
        assertFalse(actual4);
    }
}
