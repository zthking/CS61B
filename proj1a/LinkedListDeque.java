public class LinkedListDeque {

    /** Initiate a naked list StuffNode*/
    private class StuffNode {
        public int first;
        public StuffNode next;
        public StuffNode prev;

        public StuffNode(StuffNode p, int f, StuffNode n) {
            prev = p;
            first = f;
            next = n;
        }
    }

    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new StuffNode(null, 777, null);
        size = 0;
    }

    public LinkedListDeque(int x) {
        sentinel = new StuffNode(null, 777, null);
        sentinel.next = new StuffNode(sentinel, x, null);
        size = 1;
    }

    public void addFirst(int item) {
        sentinel.next = new StuffNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next.next;
        size += 1;
    }
    public StuffNode addLast(int item) {
        sentinel.next = new StuffNode(sentinel, item, null);
        size += 1;
        return sentinel;
    }

    public int getFirst() {
        return sentinel.next.first;
    }
    public int getLast() {
        return sentinel.next.first;
    }
    public static void main(String[] args) {
        LinkedListDeque L = new LinkedListDeque(7);
        System.out.println(L.getFirst());
        L.addFirst(6);
        System.out.println(L.getFirst());
        L.addFirst(5);
        System.out.println(L.getFirst());
        L.addFirst(8);
        System.out.println(L.getFirst());
        //System.out.println(L.next.getFirst());
    }
}
