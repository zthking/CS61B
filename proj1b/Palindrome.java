public class Palindrome {

    public Deque<Character> wordToDeque(String word) {

        Deque<Character> charValue = new Deque<>() {
            @Override
            public void addFirst(Character item) {

            }

            @Override
            public void addLast(Character item) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public void printDeque() {

            }

            @Override
            public Character removeFirst() {
                return null;
            }

            @Override
            public Character removeLast() {
                return null;
            }

            @Override
            public Character get(int index) {
                return null;
            }
        };

        for (int i = 0; i < word.length(); i++) {
            char temp = word.charAt(i);
            charValue.addLast(temp);
        }

        return charValue;
    }
}
