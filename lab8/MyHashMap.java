import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of HashMap.
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private int size;                       // number of key-value pairs
    private int tableSize;                  // hash table size
    private double loadFactor;              // load factor to resize the table
    private MyHashMap<K, V>.Entry[] table;  // array to be used as hash table
    private Set<K> keySet;                  // set to save all added key

    /**
     * Initialize an empty symbol table.
     */
    public MyHashMap() {
        size = 0;
        tableSize = 16;
        loadFactor = 0.75;
        table = (MyHashMap<K, V>.Entry[]) new MyHashMap.Entry[tableSize];
        keySet = new HashSet<>();
    }

    /**
     * Initialize an empty symbol table with table size.
     */
    public MyHashMap(int initialSize) {
        size = 0;
        tableSize = initialSize;
        loadFactor = 0.75;
        table = (MyHashMap<K, V>.Entry[]) new MyHashMap.Entry[tableSize];
    }

    /**
     * Initialize an empty symbol table with table size and load factor.
     */
    public MyHashMap(int initialSize, double loadFactor) {
        size = 0;
        tableSize = initialSize;
        this.loadFactor = loadFactor;
        table = (MyHashMap<K, V>.Entry[]) new MyHashMap.Entry[tableSize];
    }

    /**
     * Return hash value of key between 0 and tableSize-1.
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % tableSize;
    }

    /**
     * Resize the hash table to have the given number of table size
     * and rehash all of the keys.
     */
    private void resize(int newTableSize) {
        MyHashMap<K, V>.Entry[] newTable =
                (MyHashMap<K, V>.Entry[]) new MyHashMap.Entry[newTableSize];
        Set<K> oldSet = this.keySet();
        for (K k : oldSet) {
            int reHash = (k.hashCode() & 0x7FFFFFFF) % newTableSize;
            if (newTable[reHash] == null) {
                newTable[reHash] = new Entry(k, get(k), null);
            } else {
                newTable[reHash] = newTable[reHash].addOrReplaceValue(k, get(k));
            }
        }
        tableSize = newTableSize;
        table = newTable;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        size = 0;
        keySet = new HashSet<>();
        table = (MyHashMap<K, V>.Entry[]) new MyHashMap.Entry[tableSize];
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        return table[index].getValueInEntry(key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        int index = hash(key);

        if (table[index] == null) {
            Entry entry = new Entry(key, value, null);
            table[index] = entry;
            size += 1;
            keySet.add(key);
        } else {
            Entry entry = table[index].addOrReplaceValue(key, value);
            if (!table[index].hasKey(key)) {
                size += 1;
                keySet.add(key);
            }
            table[index] = entry;
        }

        if ((double) size / tableSize > loadFactor) {
            resize(2 * tableSize);
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        if (size == 0) {
            return null;
        }
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Implementation of iterator.
     */
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    /**
     * Linked list to save key-value pair.
     */
    private class Entry {
        private K key;
        private V value;
        private Entry next;

        public Entry(K k, V v, Entry next) {
            this.key = k;
            this.value = v;
            this.next = next;
        }

        /**
         * Return the value at give key. If the key is not mapped, return null.
         */
        public V getValueInEntry(K k) {
            if (k == null) {
                throw new IllegalArgumentException();
            }
            return getValueInEntry(k, this);
        }

        /**
         * Helper method to return the value at give key. If the key is not mapped, return null.
         */
        private V getValueInEntry(K k, Entry entry) {
            if (k == null) {
                throw new IllegalArgumentException();
            }
            if (entry == null) {
                return null;
            }
            if (entry.key.equals(k)) {
                return entry.value;
            }
            return getValueInEntry(k, entry.next);
        }

        /**
         * Return true if the entry has given key. Otherwise, return false.
         */
        public boolean hasKey(K k) {
            if (k == null) {
                throw new IllegalArgumentException();
            }
            return hasKey(k, this);
        }

        /**
         * Helper method to return true if the entry has given key. Otherwise, return false.
         */
        private boolean hasKey(K k, Entry entry) {
            if (k == null) {
                throw new IllegalArgumentException();
            }
            if (entry == null) {
                return false;
            }
            if (entry.key == k) {
                return true;
            }
            return hasKey(k, entry.next);
        }

        /**
         * If the given key is not mapped in the entry, add the key-map pair to entry.
         * If the give key is mapped, replace the old value with the new value.
         */
        public Entry addOrReplaceValue(K k, V v) {
            if (k == null) {
                throw new IllegalArgumentException();
            }
            return addOrReplaceValue(k, v, this);
        }

        /**
         * Helper method of addOrReplaceValue method.
         */
        private Entry addOrReplaceValue(K k, V v, Entry entry) {
            if (k == null) {
                throw new IllegalArgumentException();
            }
            if (entry == null) {
                entry = new Entry(k, v, null);
                return entry;
            }
            if (entry.key == k) {
                entry.value = v;
                return entry;
            }
            return addOrReplaceValue(k, v, entry.next);
        }
    }
}
