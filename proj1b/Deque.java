/**
 * Deque interface.
 * Include common methods in ArrayDeque and LinkedListDeque developed in Proj 1A.
 * Solution of CS61B Spring 2019 Proj 1B Task 1.
 * Solution is verified by Autograder Summer 2018 version.
 * https://sp19.datastructur.es/materials/proj/proj1b/proj1b
 * @param <T> Generic type.
 */
public interface Deque<T> {

    void addFirst(T item);

    void addLast(T item);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int index);
}
