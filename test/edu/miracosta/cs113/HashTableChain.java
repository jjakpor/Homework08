package edu.miracosta.cs113;

import java.util.*;

/**
 * HashTable implementation using chaining to tack a pair of key and value pairs.
 *
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class HashTableChain<K, V> implements Map<K, V> {

    private LinkedList<Entry<K, V>>[] table;
    private int numKeys;
    private static final int CAPACITY = 101;
    private static final double LOAD_THRESHOLD = 1.5;

    ///////////// ENTRY CLASS ///////////////////////////////////////

    /**
     * Contains key-value pairs for HashTable
     *
     * @param <K> the key
     * @param <V> the value
     */
    private static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        /**
         * Creates a new key-value pair
         *
         * @param key   the key
         * @param value the value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value
         *
         * @return the value
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value
         *
         * @param val the new value
         * @return the old value
         */
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }


        @Override
        public boolean equals(Object o) {
            // Implementing equality test for Map.Entry according to the Java API
            if (this == o) return true;
            if (!(o instanceof Map.Entry<?, ?> entry)) return false;
            return Objects.equals(getKey(), entry.getKey()) && Objects.equals(getValue(), entry.getValue());
        }

        @Override
        public int hashCode() {
            // Implementing equality test for Map.Entry.hashCode according to the Java API
            return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }
    }

    ////////////// end Entry Class /////////////////////////////////

    ////////////// EntrySet Class //////////////////////////////////

    /**
     * Inner class to implement set view
     */
    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {


        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new SetIterator();
        }

        @Override
        public int size() {
            return numKeys;
        }
    }

    ////////////// end EntrySet Class //////////////////////////////

    //////////////   SetIterator Class ////////////////////////////

    /**
     * Class that iterates over the table. Index is table location
     * and lastItemReturned is entry
     */
    private class SetIterator implements Iterator<Map.Entry<K, V>> {

        private int index = 0;
        private Entry<K, V> lastItemReturned = null;
        private Iterator<Entry<K, V>> iter = null;

        @Override
        public boolean hasNext() {
            if (iter != null && iter.hasNext()) {
                return true;
            } else {
                for (index += 1, iter = null; index < table.length; index++) {
                    if (table[index] != null) {
                        iter = table[index].iterator();
                        break;
                    }
                }
                return iter != null && iter.hasNext();
            }

        }

        @Override
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                return lastItemReturned = iter.next();
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            iter.remove();
            numKeys--;
        }
    }

    ////////////// end SetIterator Class ////////////////////////////

    /**
     * Default constructor, sets the table to initial capacity size
     */
    public HashTableChain() {
        table = new LinkedList[CAPACITY];
    }

    // returns number of keys
    @Override
    public int size() {
        return numKeys;
    }

    // returns boolean if table has no keys
    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    // returns boolean if table has the searched for key
    @Override
    public boolean containsKey(Object key) {
        return keySet().contains(key);
    }

    /**
     * Get the array index corresponding to a key.
     *
     * @param key the key
     * @return the index corresponding to where a list would be that contains the key
     */
    private int index(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        return index;
    }

    // returns boolean if table has the searched for value
    @Override
    public boolean containsValue(Object value) {
        for (LinkedList<Entry<K, V>> entryList : table) {
            if (entryList != null) {
                for (Map.Entry<K, V> entry : entryList) {
                    if (value.equals(entry.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // returns Value if table has the searched for key
    @Override
    public V get(Object key) {
        if (containsKey(key)) {
            for (Map.Entry<K, V> entry : table[index(key)]) {
                if (key.equals(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    // adds the key and value pair to the table using hashing
    @Override
    public V put(K key, V value) {
        int index = index(key);
        V old = get(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        if (!containsKey(key)) {
            numKeys++;
        }
        table[index].add(new Entry<>(key, value));
        return old;
    }


    /**
     * Resizes the table to be 2X +1 bigger than previous
     */
    private void rehash() {
        // LinkedList<Map.Entry<K, V>>[] newTable = new LinkedList[2*size()+1];
        // numKeys = 2*numKeys + 1;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LinkedList<Entry<K, V>> entries : table) {
            if (entries != null) {
                for (Entry<K, V> nextItem : entries) {
                    sb.append(nextItem.toString()).append(" ");
                }
                sb.append(" ");
            }
        }
        return sb.toString();

    }

    // remove an entry at the key location
    // return removed value
    @Override
    public V remove(Object key) {
        if (containsKey(key)) {
            ListIterator<Entry<K, V>> li = table[index(key)].listIterator();
            Entry<K, V> entry;
            while (li.hasNext()) {
                entry = li.next();
                if (key.equals(entry.getKey())) {
                    li.remove();
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    // throws UnsupportedOperationException
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    // empties the table
    @Override
    public void clear() {
        Arrays.fill(table, null);
        numKeys = 0;
    }

    // returns a view of the keys in set view
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (LinkedList<Entry<K, V>> list : table)
            if (list != null)
                for (Entry<K, V> entry : list)
                    keys.add(entry.getKey());

        return keys;
    }

    // throws UnsupportedOperationException
    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }


    // returns a set view of the hash table
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map<?, ?>)) {
            return false;
        }
        return entrySet().equals(((Map<?, ?>) o).entrySet());

    }

    @Override
    public int hashCode() {
        int sum = 0;
        Set<Map.Entry<K, V>> entries = entrySet();
        for (Map.Entry<K, V> entry : entries) {
            sum += entry.hashCode();
        }
        return sum;
    }
}
