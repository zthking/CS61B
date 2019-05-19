/**
 * Copy from Proj 1A and revise to implement interface.
 * Check Proj 1A for more detail.
 * Method is not actually used.
 */
public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int N;
    private int front;
    private int rear;
    private int size;

    public ArrayDeque() {
        N = 8;
        items = (T[]) new Object[N];
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        N = 8;
        items = (T[]) new Object[N];
        size = 0;
        front = 0;
        rear = 0;

        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

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

    @Override
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

    @Override
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

    @Override
    public int size() {
        return size;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
