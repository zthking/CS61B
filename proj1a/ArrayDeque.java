public class ArrayDeque<T> {

    private T[] items;
    private int N; //N is the length of array.
    private int front;
    private int rear;
    private int size; //size = (N - front + rear) % N;

    public ArrayDeque() {
        N = 8;
        items = (T[]) new Object[N];
        size = 0;
    }

    private void resize() {
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

    public void addFirst(T item) {
        if (size == N - 1) {
            resize();
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
            resize();
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
/*
    public static void main(String[] args) {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        L.addLast(7);
        L.addLast(8);
        L.addLast(9);
        L.addFirst(6);
        L.addFirst(5);
        L.addFirst(4);
        L.printDeque();
        L.removeLast();
        L.removeFirst();
        L.printDeque();
    }
*/
}