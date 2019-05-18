public interface Deque<T> {

    public void addFirst(T item);

    public void addLast(T item);

    public int size();

    default public boolean isEmpty() {
        return size() == 0;
    }

    public void printDeque();

    public T removeFirst();

    public T removeLast();

    public T get(int index);
}

class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        private T value;
        private Node next;
        private Node prev;

        private Node(Node p, T v, Node n) {
            prev = p;
            value = v;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Deep copy of other.
     */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    @Override
    public void addFirst(T item) {
        Node tempFirst = new Node(sentinel, item, sentinel.next);
        sentinel.next.prev = tempFirst;
        sentinel.next = tempFirst;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node tempLast = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.next = tempLast;
        sentinel.prev = tempLast;
        size++;
    }

    private T getFirst() {
        if (size == 0) {
            return null;
        }
        return sentinel.next.value;
    }

    private T getLast() {
        if (size == 0) {
            return null;
        }
        return sentinel.prev.value;
    }

    /**
     * Default implementation is added to interface.
     * public boolean isEmpty() {
     * return size == 0;
     * }
     */

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size == 0) {
            System.out.println("\n");
        } else {
            Node printNode = sentinel;
            for (int i = 0; i < size; i++) {
                System.out.print(printNode.next.value + " ");
                printNode = printNode.next;
            }
            System.out.println("\n");
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T t = getFirst();
            Node current = sentinel.next;
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            return t;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T t = getLast();
            Node current = sentinel.prev;
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            return t;
        }
    }

    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        } else if (index < 0) {
            throw new IllegalArgumentException();
        } else {
            Node current = sentinel;
            int i = 0;
            while (i < index + 1) {
                i++;
                current = current.next;
            }
            return current.value;
        }
    }
}

class ArrayDeque<T> implements Deque<T> {

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
     * Deep copy of other.
     */
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

    /**
     * Default implementation is added to interface.
     * public boolean isEmpty() {
     * return size == 0;
     * }
     */

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
