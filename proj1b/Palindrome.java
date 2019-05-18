public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> charValue = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char temp = word.charAt(i);
            charValue.addLast(temp);
        }
        return charValue;
    }
}
