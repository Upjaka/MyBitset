import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BitsetTest {
    Set<String> elements = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5"));
    Bitset<String> bitset = new Bitset<>(elements);


    @Test
    void testEquals() {
        String[] init = new String[] {"1", "2", "3"};
        String[] init1 = new String[] {"3", "4", "5"};
        bitset.addAll(Arrays.asList(init));
        Bitset<String> bitset1 = new Bitset<>(elements);
        bitset1.addAll(Arrays.asList(init1));
        Bitset<String> equals = new Bitset<>(elements);
        equals.addAll(Arrays.asList(init));

        assertEquals(bitset, bitset);
        assertEquals(bitset, equals);
        assertNotEquals(bitset, bitset1);
    }

    @Test
    void add() {
        String[] expected = new String[] {"1"};
        assertTrue(bitset.add("1"));
        assertTrue(Arrays.equals(expected, bitset.toArray()));
        assertFalse(bitset.add("1"));
        assertFalse(bitset.add("6"));
        bitset.clear();
    }

    @Test
    void addAll() {
        String[] expected = new String[] {"1", "2", "3"};
        assertTrue(bitset.addAll(Arrays.asList(expected)));
        assertTrue(Arrays.equals(expected, bitset.toArray()));
        assertFalse(bitset.addAll(Arrays.asList(expected)));
        assertFalse(bitset.addAll(Arrays.asList("7", "8", "9")));
        bitset.clear();
    }

    @Test
    void remove() {
        String[] init = new String[] {"1", "2", "3", "4"};
        bitset.addAll(Arrays.asList(init));
        String[] init1 = new String[] {"1", "2", "3"};
        Bitset<String> expected = new Bitset<>(elements);
        expected.addAll(Arrays.asList(init1));

        assertTrue(bitset.remove("4"));
        assertEquals(expected, bitset);
        assertFalse(bitset.remove("4"));
        bitset.clear();
    }

    @Test
    void removeAll() {
        String[] expected = new String[] {"4", "5"};
        String[] init = new String[] {"1", "2", "3"};
        Bitset<String> bitset1 = new Bitset<>(elements);
        bitset.addAll(elements);
        bitset1.addAll(Arrays.asList(expected));

        assertTrue(bitset.removeAll(Arrays.asList(init)));
        assertEquals(bitset1, bitset);
        assertFalse(bitset.removeAll(Arrays.asList(init)));
        bitset.clear();
    }

    @Test
    void union() {
        String[] init = new String[] {"1", "2", "3"};
        String[] init1 = new String[] {"3", "4", "5"};
        String[] init2 = new String[] {"4", "5"};
        bitset.addAll(Arrays.asList(init));
        Bitset<String> bitset1 = new Bitset<>(elements);
        bitset1.addAll(Arrays.asList(init1));
        Bitset<String> bitset2 = new Bitset<>(elements);
        bitset2.addAll(Arrays.asList(init2));
        Bitset<String> expected = new Bitset<>(elements);
        expected.addAll(elements);

        assertEquals(bitset.union(bitset1), expected);
        assertEquals(bitset.union(bitset2), expected);
        assertEquals(bitset.union(new Bitset<>(elements)), bitset);
        bitset.clear();
    }

    @Test
    void intersections() {
        String[] init = new String[] {"1", "2", "3"};
        String[] init1 = new String[] {"3", "4", "5"};
        String[] init2 = new String[] {"4", "5"};
        bitset.addAll(Arrays.asList(init));
        Bitset<String> bitset1 = new Bitset<>(elements);
        bitset1.addAll(Arrays.asList(init1));
        Bitset<String> bitset2 = new Bitset<>(elements);
        bitset2.addAll(Arrays.asList(init2));
        Bitset<String> expected = new Bitset<>(elements);
        expected.add("3");

        assertEquals(bitset.intersections(bitset1), expected);
        assertEquals(bitset.intersections(bitset2), new Bitset<>(elements));
        assertEquals(bitset.intersections(bitset), bitset);
        bitset.clear();
    }

    @Test
    void complements() {
        String[] init = new String[] {"1", "2", "3"};
        String[] init1 = new String[] {"4", "5"};
        bitset.addAll(Arrays.asList(init));
        Bitset<String> bitset1 = new Bitset<>(elements);
        bitset1.addAll(Arrays.asList(init1));
        Bitset<String> bitset2 = new Bitset<>(elements);
        bitset2.addAll(elements);

        assertEquals(bitset.complements(), bitset1);
        assertEquals(bitset1.complements(), bitset);
        assertEquals(bitset2.complements(), new Bitset<>(elements));
        assertEquals(new Bitset<>(elements).complements(), bitset2);
    }
}