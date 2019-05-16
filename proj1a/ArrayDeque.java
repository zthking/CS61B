/**
 * Deque in Java.
 *
 * Implement double-ended queues using array.
 * This is for CS61B Spring 2019 Project 1A.
 * https://sp19.datastructur.es/materials/proj/proj1a/proj1a
 *
 * Reference video on YouTube.
 * https://www.youtube.com/watch?v=z3R9-DkVtds
 *
 * @param <T> Generic array to save variables.
 */
public class ArrayDeque<T> {

    private T[] items;

    /**
     * Length of array.
     */
    private int N;
    private int front;
    private int rear;

    /**
     * size = (N - front + rear) % N;
     */
    private int size;

    /**
     * Construct ArrayDeque.
     * Initial array size is 8.
     */
    public ArrayDeque() {
        N = 8;
        items = (T[]) new Object[N];
        size = 0;
    }

    /**
     * Double the array size.
     * Copy the original array to the resized array.
     * Set front to 0.
     */
    private void upSize() {
        int s = size;
        N = 2 * N;
        int lastIndex = size + 1;
        T[] newItems = (T[]) new Object[N];
        int i = 0;
        while (s > 0) {
            s--;
            newItems[i++] = items[front++];
            if (front == lastIndex) {
                front = 0;
            }
        }
        rear = i;
        front = 0;
        items = newItems;
    }

    /**
     * Halve the array size.
     * Copy the original array to the resized array.
     * Set front to 0.
     */
    private void downSize() {
        int s = size;
        int lastIndex = N;
        N = N / 2;
        T[] newItems = (T[]) new Object[N];
        int i = 0;
        while (s > 0) {
            s--;
            newItems[i++] = items[front++];
            if (front == lastIndex) {
                front = 0;
            }
        }
        rear = i;
        front = 0;
        items = newItems;
    }

    public void addFirst(T item) {
        if (size == N - 1) {
            upSize();
        }

        if (front == 0) {
            front = N - 1;
        } else {
            front = front - 1;
        }
        items[front] = item;

        size++;
    }

    public void addLast(T item) {
        if (size == N - 1) {
            upSize();
        }

        items[rear++] = item;
        if (rear == N) {
            rear = 0;
        }

        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            System.out.println("\n");
        } else {
            for (int i = 0; i < size; i++) {
                System.out.print(get(i));
            }
            System.out.println("\n");
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T t = items[front];
        /*items[front] = null;
         * As long as the pointer is changed,
         * there is no need to set items[front] to null.
         */
        front++;
        if (front == N) {
            front = 0;
        }

        size--;
        if (N > 8 && size < N / 2 - 1) {
            downSize();
        }

        return t;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T t;
        if (rear == 0) {
            t = items[N - 1];
            rear = N - 1;
        } else {
            t = items[rear - 1];
            rear--;
        }
        size--;
        if (N > 8 && size < N / 2 - 1) {
            downSize();
        }

        return t;
    }

    public T get(int index) {
        if (index > size) {
            return null;
        }

        if (index < 0) {
            throw new IllegalArgumentException();
        }

        return items[(front + index) % N];

    }
}
