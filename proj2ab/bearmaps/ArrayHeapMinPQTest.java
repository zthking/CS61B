package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void sanityGenericsTest() {
        try {
            ArrayHeapMinPQ<Integer> integerMap = new ArrayHeapMinPQ<>();
            ArrayHeapMinPQ<String> stringMap = new ArrayHeapMinPQ<>();
            ArrayHeapMinPQ<Boolean> booleanMap = new ArrayHeapMinPQ<>();
        } catch (Exception e) {
            fail();
        }
    }

    //assumes add/contain work
    @Test
    public void sanityAddTest() {
        ArrayHeapMinPQ<String> stringMap = new ArrayHeapMinPQ<>();
        stringMap.add("aaa", 0.1);
        assertTrue(stringMap.contains("aaa"));
        assertEquals("aaa", stringMap.getSmallest());
    }

    //assumes add work
    @Test
    public void sanityContainTest() {
        ArrayHeapMinPQ<String> stringMap = new ArrayHeapMinPQ<>();
        stringMap.add("aaa", 0.1);
        assertTrue(stringMap.contains("aaa"));
    }

    //assumes add work
    @Test
    public void sanityGetSmallestTest() {
        ArrayHeapMinPQ<String> stringMap1 = new ArrayHeapMinPQ<>();
        stringMap1.add("aaa", 0.1);
        stringMap1.add("bbb", 0.2);
        stringMap1.add("ccc", 0.3);
        assertEquals("aaa", stringMap1.getSmallest());

        ArrayHeapMinPQ<String> stringMap2 = new ArrayHeapMinPQ<>();
        stringMap2.add("aaa", 0.2);
        stringMap2.add("bbb", 0.3);
        stringMap2.add("ccc", 0.1);
        assertEquals("ccc", stringMap2.getSmallest());
    }

    //assumes add/getSmallest work
    @Test
    public void sanityRemoveSmallestTest() {
        ArrayHeapMinPQ<String> stringMap1 = new ArrayHeapMinPQ<>();
        stringMap1.add("aaa", 0.1);
        stringMap1.add("bbb", 0.2);
        stringMap1.add("ccc", 0.3);
        assertEquals("aaa", stringMap1.removeSmallest());
        assertEquals("bbb", stringMap1.getSmallest());
    }

    //assumes add work
    @Test
    public void sanitySizeTest() {
        ArrayHeapMinPQ<String> stringMap1 = new ArrayHeapMinPQ<>();
        stringMap1.add("aaa", 0.1);
        stringMap1.add("bbb", 0.2);
        stringMap1.add("ccc", 0.3);
        assertEquals(3, stringMap1.size());
    }

    //assumes add/getSmallest work
    @Test
    public void sanityChangePriorityTest() {
        ArrayHeapMinPQ<String> stringMap1 = new ArrayHeapMinPQ<>();
        stringMap1.add("aaa", 0.1);
        stringMap1.add("bbb", 0.2);
        stringMap1.add("ccc", 0.3);
        stringMap1.changePriority("aaa", 0.4);
        assertEquals("bbb", stringMap1.getSmallest());
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(ArrayHeapMinPQTest.class);
    }
}
