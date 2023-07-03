package ch03;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapTest {
    final String KEY_1 = "KEY1";
    final String KEY_2 = "KEY2";
    final String NULL_KEY = null;
    final String VALUE_1 = "VALUE_1";
    final String VALUE_2 = "VALUE_2";

    private static HashMap<String, String> hashMap;

    @BeforeAll
    static void setUp() {
        hashMap = new HashMap<>();
    }

    @Test
    void putShouldReturnOneElement() {
        hashMap.put(KEY_1, VALUE_1);
        assertEquals(1, hashMap.size());
        assertTrue(hashMap.containsKey(KEY_1));
        assertEquals(VALUE_1, hashMap.get(KEY_1));
    }

    @Test
    void putWithTheSameKeyShouldReplaceValue() {
        hashMap.put(KEY_2, VALUE_1);
        hashMap.put(KEY_2, VALUE_2);
        assertEquals(1, hashMap.size());
        assertTrue(hashMap.containsKey(KEY_2));
        assertEquals(VALUE_2, hashMap.get(KEY_2));
    }

    @Test
    void clearShouldRemoveAllItems() {
        hashMap.put(KEY_1, VALUE_1);
        hashMap.put(KEY_2, VALUE_2);
        assertEquals(2, hashMap.size());
        hashMap.clear();
        assertEquals(0, hashMap.size());
        assertFalse(hashMap.containsKey(KEY_1));
        assertFalse(hashMap.containsKey(KEY_2));
    }

    @Test
    void nullValueCanBeUsedAsKey() {
        hashMap.put(NULL_KEY, VALUE_1);
        assertEquals(1, hashMap.size());
        assertTrue(hashMap.containsKey(NULL_KEY));
        assertEquals(VALUE_1, hashMap.get(NULL_KEY));
    }
}
