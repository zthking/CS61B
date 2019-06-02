package es.datastructur.synthesizer;

/**
 * API includes items that can only be enqueued at the back of the queue,
 * and can only be dequeued from the front of the queue.
 * It has a fixed capacity, and nothing is allowed to enqueue if the queue is full.
 */
public interface BoundedQueue<T> {

    /**
     *Return size of the buffer
     */
    int capacity();

    /**
     * Return number of items currently in the buffer
     */
    int fillCount();

    /**
     * Add item x to the end
     */
    void enqueue(T x);

    /**
     * Delete and return item from the front
     */
    T dequeue();

    /**
     * Return (but do not delete) item from the front
     */
    T peek();

    /**
     *Return true if queue is empty.
     */
    default boolean isEmpty() {
        return (this.fillCount() == 0);
    }

    /**
     *Compare capacity with current count.
     *Return true if queue is full.
     */
    default boolean isFull() {
        return (this.capacity() == this.fillCount());
    }
}
