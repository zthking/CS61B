package es.datastructur.synthesizer;
import java.util.Iterator;

/**
 * Implement BoundedQueue<T>
 */
public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /** Index for the next dequeue or peek. */
    private int first;
    /** Index for the next enqueue. */
    private int last;
    /** Variable for the fillCount. */
    private int fillCount;
    /** Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     *Return size of the buffer
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * Return number of items currently in the buffer
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last += 1;
        if (last == rb.length) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T returnValue = rb[first];
        first += 1;
        fillCount -= 1;
        if (first == rb.length) {
            first = 0;
        }
        return returnValue;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int wizPos;
        private int location;

        public ArrayRingBufferIterator() {
            wizPos = 0;
            location = first;
        }

        public boolean hasNext() {
            return wizPos < fillCount;
        }

        /**
         * Do not use dequeue and enqueue.
         * Otherwise, nested iteration will be wrong.
         */
        public T next() {
            T returnItem = rb[location];
            location += 1;
            if (location == capacity()) {
                location = 0;
            }
            wizPos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (other.capacity() != this.capacity()) {
            return false;
        }
        if (other.fillCount() != this.fillCount()) {
            return false;
        }

        ArrayRingBuffer<T> cmp = new ArrayRingBuffer<>(other.capacity());
        for (T itemInO : other) {
            cmp.enqueue(itemInO);
        }

        for (T item : this) {
            if (cmp.dequeue() != item) {
                return false;
            }
        }
        return true;
    }
}
