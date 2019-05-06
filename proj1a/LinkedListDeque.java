/** Reference videos from YouTube.
 *  https://www.youtube.com/watch?v=D34TOdo4UEk
 *  https://www.youtube.com/watch?v=OGKkpWgg1Fs
 */

public class LinkedListDeque<T> {

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

    public void addFirst(T item) {
        Node tempFirst = new Node(sentinel, item, sentinel.next);
        sentinel.next.prev = tempFirst; //Must link the next Node of sentinel first.
        sentinel.next = tempFirst;  //Must link sentinel to temp at second step.
        size++;
    }

    public void addLast(T item) {
        Node tempLast = new Node(sentinel.prev,item,sentinel);
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
            Node printNode = sentinel;
            for (int i = 0; i<size; i++){
                System.out.print(printNode.next.value + " ");
                printNode = printNode.next;
            }
            System.out.println("\n");
        }
    }

    public T removeFirst() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        T t = getFirst();
        Node current = sentinel.next;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return t;
    }

    public T removeLast() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        T t = getLast();
        Node current = sentinel.prev;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return t;
    }

    public T get(int index) {
        if (index > size) {
            return null;
        } else if (index < 0){
            throw new IllegalArgumentException();
        } else{
            Node current = sentinel;
            int i = 0;
            while (i < index+1) {
                i++;
                current = current.next;
            }
            return current.value;
        }
    }

    public T getRecursive(int index) {
        if (index > size) {
            return null;
        } else if (index < 0) {
            throw new IllegalArgumentException();
        } else {
                return getNodeRecursive(sentinel,index+1);
        }
    }

    private T getNodeRecursive(Node current, int index) {
        if (index == 1) {
            return current.next.value;
        }
        return getNodeRecursive(current.next, index-1);
    }

    /**This delete method can delete the node at any location.
     *
    private void delete(int index) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        if (index > size) {
            throw new IllegalArgumentException();
        } else {
            Node current = sentinel;
            int i = 0;
            while (i < index) {
                i++;
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
        }
    }
    */
    /*
    public void testForward() {
        System.out.println(sentinel.value);
        System.out.println(sentinel.next.value);
        System.out.println(sentinel.next.next.value);
        System.out.println(sentinel.next.next.next.value);
    }

    public void testBack() {
        System.out.println(sentinel.value);
        System.out.println(sentinel.prev.value);
        System.out.println(sentinel.prev.prev.value);
        System.out.println(sentinel.prev.prev.prev.value);
    }

    public void testAddFirst() {
        addFirst(7);
        addFirst(6);
        addFirst(5);
    }

    public void testAddLast() {
        addLast(8);
        addLast(9);
        addLast(10);
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        L.addFirst(7);
        L.addFirst(6);
        L.addFirst(5);
        L.addLast(8);
        L.addLast(9);
        L.addLast(10);
        //System.out.println(L.get(5));
        //L.testAddFirst();
        //L.testAddLast();
        //L.testAddFirst();
        //L.testForward();
        //System.out.println("\n");
        //L.testBack();
        //L.printDeque();
        //L.delete(2);
        //System.out.println(L.getRecursive(0));

        //System.out.println(L.getRecursive(-7));
        System.out.println(L.removeLast());
        //L.testForward();
        L.testBack();
    }
*/
}
