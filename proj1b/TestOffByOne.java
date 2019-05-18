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
        assertTrue(actual1);
        assertFalse(actual2);
    }
}