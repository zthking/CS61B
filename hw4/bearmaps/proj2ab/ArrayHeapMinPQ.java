package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Implementation of HeapMinPQ using ArrayList.
 * HashMap is used for contain function.
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> pq;
    private HashMap<T, Integer> pqMap;
    private int size;

    /**
     * Initialize an empty ArrayHeapMinPQ.
     */
    public ArrayHeapMinPQ() {
        pq = new ArrayList<>();
        pq.add(null);
        pqMap = new HashMap<>();
        pqMap.put(null, 0);
        size = 0;
    }

    /**
     * Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        pq.add(new Node(item, priority));
        size += 1;
        pqMap.put(item, size);
        swim(size);
    }

    /**
     * Returns true if the PQ contains the given item.
     */
    @Override
    public boolean contains(T item) {
        return pqMap.containsKey(item);
    }

    /**
     * Returns the minimum item.
     * Throws NoSuchElementException if the PQ is empty.
     */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return pq.get(1).getItem();
    }

    /**
     * Removes and returns the minimum item.
     * Throws NoSuchElementException if the PQ is empty.
     */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T min = pq.get(1).getItem();
        if (size == 1) {
            pq.remove(1);
            size -= 1;
            pqMap.remove(min);
            return min;
        }
        pq.set(1, pq.get(size));
        pq.remove(size);
        size -= 1;
        pqMap.remove(min);
        sink(1);
        return min;
    }

    /**
     * Returns the number of items in the PQ.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Changes the priority of the given item.
     * Throws NoSuchElementException if the item doesn't exist.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = pqMap.get(item);
        double oldPriority = pq.get(index).getPriority();
        pq.get(index).setPriority(priority);
        if (priority > oldPriority) {
            sink(index);
        }
        if (priority < oldPriority) {
            swim(index);
        }
    }

    /************************************************************
     * Helper functions for compares and swaps.
     ************************************************************/
    private boolean lessThan(int i, int j) {
        if (pq.get(i).getPriority() == pq.get(j).getPriority()) {
            if (Math.random() > 0.5) {
                return true;
            }
        }
        return pq.get(i).getPriority() < pq.get(j).getPriority();
    }

    private void swap(int i, int j) {
        Node node = pq.get(j);
        pq.set(j, pq.get(i));
        pq.set(i, node);
        pqMap.put(pq.get(i).getItem(), i);
        pqMap.put(pq.get(j).getItem(), j);
    }

    private void swim(int child) {
        while (child > 1) {
            int parent = child / 2;
            if (!lessThan(child, parent)) {
                break;
            }
            swap(child, parent);
            child = parent;
        }
    }

    private void sink(int parent) {
        while (parent * 2 <= size) {
            int child = parent * 2;
            if (child < size && !lessThan(child, child + 1)) {
                child += 1;
            }
            if (lessThan(parent, child)) {
                break;
            }
            swap(parent, child);
            parent = child;
        }
    }

    /**
     * Helper class to save value and queue.
     */
    private class Node {
        private T item;
        private double priority;

        Node(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double newPriority) {
            this.priority = newPriority;
        }
    }
}
