import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> input = new Queue<>();
        int testSize = 10000;
        for (int i = testSize; i > 0; i--) {
            input.enqueue(i);
        }
        Queue<Integer> actual = QuickSort.quickSort(input);
        assertTrue(isSorted(actual));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> input = new Queue<>();
        int testSize = 10000;
        for (int i = testSize; i > 0; i--) {
            input.enqueue(i);
        }
        Queue<Integer> actual = MergeSort.mergeSort(input);
        assertTrue(isSorted(actual));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
