/**
 * Copy from Proj 1A and revise to implement interface.
 * Check Proj 1A for more detail.
 */
public class LinkedListDeque<T> implements Deque<T> {

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

    public  LinkedListDeque(LinkedListDeque other) {
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
