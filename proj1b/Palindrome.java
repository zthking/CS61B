/**
 * Return true if input string is a palindrome according to the character comparison test
 * provided by the CharacterComparator passed in as argument cc, otherwise false.
 */
public class Palindrome {

    /**
     * Read every single character in the given string
     * and return the characters to a character type Deque.
     * @param word input string
     * @return     a character type Deque that contains all characters in the input string
     */
    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        } else {
            LinkedListDeque<Character> charValue = new LinkedListDeque<>();
            for (int i = 0; i < word.length(); i++) {
                char temp = word.charAt(i);
                charValue.addLast(temp);
            }
            return charValue;
        }
    }

    /**
     * Return true if input string is palindrome, otherwise false.
     * e.g. Words "a" and "noon" are palindromes. Word "house" is no a palindrome.
     * @param word input string.
     * @return     true if input string is a palindrome, otherwise false
     */
    public boolean isPalindrome(String word) {
        Deque<Character> charValue = wordToDeque(word);
        return isPalindrome(charValue);
    }

    /**
     * Return true if input Deque is palindrome, otherwise false.
     * Recursive implementation of isPalindrome.
     * @param charValue input Deque
     * @return          true if input Deque is a palindrome, otherwise false
     */
    private boolean isPalindrome(Deque<Character> charValue) {
        int length = charValue.size();
        if (length < 2) {
            return true;
        } else {
            if (charValue.removeFirst() != charValue.removeLast()) {
                return false;
            } else {
                return isPalindrome(charValue);
            }
        }
    }

    /**
     * Return true if the string is a palindrome according to the character comparison test
     * provided by the CharacterComparator passed in as argument cc, otherwise false.
     * @param word input string
     * @param cc   character comparator
     * @return     true if input string is a palindrome and
     *             characters int the string are different by exactly one, otherwise false
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> charValue = wordToDeque(word);
        return isPalindrome(charValue, cc);
    }

    /**
     * Return true if the Deque is a palindrome according to the character comparison test
     * provided by the CharacterComparator passed in as argument cc, otherwise false.
     * @param charValue input Deque
     * @param cc        character comparator
     * @return          true if input string is a palindrome and
     *                  characters int the string are different by exactly one, otherwise false
     */
    private boolean isPalindrome(Deque<Character> charValue, CharacterComparator cc) {
        int length = charValue.size();
        if (length < 2) {
            return true;
        } else {
            if (!cc.equalChars(charValue.removeFirst(), charValue.removeLast())) {
                return false;
            } else {
                return isPalindrome(charValue, cc);
            }
        }
    }
}
