package ch06;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestCollectionTest {
    private static Set<String> setA = new LinkedHashSet<>();
    private static Set<String> setB = new LinkedHashSet<>();
    static String s1 = "s1";
    static String s2 = "s2";

    @BeforeAll
    static void setUp(){
        setA.add(s1);
        setA.add(s2);
        setB.add(s1);
        setB.add(s2);
    }

    @Test
    void collectionsUtilityMethods(){
        assertThat(setA,hasItem(s1));
        assertThat(setA,hasItem(s2));
        assertThat(setA,not("xyz"));
        assertThat(setA,hasItems(s1,s2));
        assertThat(setB,hasItems(s1,s2));
    }
}
