import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B {
    private Node root;

    /**
     * Initialize an empty MyTrieSet.
     */
    public MyTrieSet() {
        root = new Node('\0', false);
    }

    /**
     * Clears all items out of Trie.
     */
    @Override
    public void clear() {
        root = new Node('\0', false);
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise.
     */
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            throw new IllegalArgumentException();
        }

        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);

            if (i == (n - 1) && !curr.isKey) {
                return false;
            }
        }

        return true;
    }

    /**
     * Inserts string KEY into Trie.
     */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }

        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /**
     * Returns a list of all words that start with PREFIX.
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node curr = containPrefix(prefix, root);
        if (curr == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (Character c : curr.map.keySet()) {
            String prefixNew = prefix + c;
            list = prefixHelper(prefixNew, list, curr.map.get(c));
        }
        return prefixHelper(prefix, list, curr);
    }

    /**
     * Helper of prefix.
     */
    private List<String> prefixHelper(String prefix, List<String> list, Node n) {
        if (n.isKey) {
            list.add(prefix);
        }
        for (Map.Entry<Character, Node> entry : n.map.entrySet()) {
            String prefixNew = prefix + entry.getKey();
            list = prefixHelper(prefixNew, list, n.map.get(entry.getKey()));
        }
        return list;
    }

    /**
     * Helper of prefix.
     */
    private Node containPrefix(String prefix, Node n) {
        for (int i = 0, len = prefix.length(); i < len; i++) {
            char c = prefix.charAt(i);
            if (!n.map.containsKey(c)) {
                return null;
            }
            n = n.map.get(c);
        }
        return n;
    }

    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     *Map to save character and boolean value.
     */
    private class Node {
        private char key;
        private boolean isKey;
        private HashMap<Character, Node> map;

        Node(char key, boolean isKey) {
            this.key = key;
            this.isKey = isKey;
            map = new HashMap<>();
        }
    }
}
