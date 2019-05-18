public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        }
        LinkedListDeque<Character> charValue = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char temp = word.charAt(i);
            charValue.addLast(temp);
        }
        return charValue;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> charValue = wordToDeque(word);
        return isPalindrome(charValue);
    }

    private boolean isPalindrome(Deque<Character> charValue) {
        int length = charValue.size();
        if (length < 2) {
            return true;
        } else {
            if (charValue.removeFirst() != charValue.removeLast())
                return false;
            else
                return isPalindrome(charValue);
        }
    }
}
