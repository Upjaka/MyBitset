import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

final class BitsetTest {
    List<Integer> elements = Arrays.asList(0, 1, 2, 3, 4, 5);
    Bitset<Integer> bitset = new Bitset<>(elements);


    @Test
    void testEquals() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        bitset.addAll(init);
        Bitset<Integer> notEquals = new Bitset<>(elements);
        notEquals.addAll(Arrays.asList(3, 4, 5));
        Bitset<Integer> equals = new Bitset<>(elements);
        equals.addAll(init);

        assertNotEquals(notEquals, bitset);
        assertEquals(bitset, bitset);
        assertEquals(bitset, equals);
    }

    @Test
    void add() {
        Set<Integer> expected = new HashSet<>(Collections.singletonList(0));
        assertTrue(bitset.add(0));
        assertEquals(expected, bitset.indices());
        assertTrue(bitset.add(1));
        expected.add(1);
        assertEquals(expected, bitset.indices());
        assertFalse(bitset.add(0));
        assertFalse(bitset.add(6));
    }

    @Test
    void addAll() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        assertTrue(bitset.addAll(init));
        assertEquals(new HashSet<>(init), bitset.indices());
    }

    @Test
    void remove() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        bitset.addAll(init);
        Set<Integer> expected = new HashSet<>(init);
        expected.remove(2);

        assertTrue(bitset.remove(2));
        assertEquals(expected, bitset.indices());
        assertFalse(bitset.remove(4));
    }

    @Test
    void removeAll() {
        Set<Integer> expected = new HashSet<>(Arrays.asList(4, 5));
        Set<Integer> init = new HashSet<>(Arrays.asList(0, 1, 2, 3));
        bitset.addAll(elements);

        assertTrue(bitset.removeAll(init));
        assertEquals(expected, bitset.indices());
        assertFalse(bitset.removeAll(init));
    }

    @Test
    void union() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        List<Integer> init1 = Arrays.asList(3, 4, 5);
        List<Integer> init2 = Arrays.asList(2, 3, 4, 5);
        bitset.addAll(init);
        Bitset<Integer> bitset1 = new Bitset<>(elements);
        bitset1.addAll(init1);
        Bitset<Integer> bitset2 = new Bitset<>(elements);
        bitset2.addAll(init2);
        Bitset<Integer> expected = new Bitset<>(elements);
        expected.addAll(elements);

        assertEquals(bitset.union(bitset1), expected);
        assertEquals(bitset.union(bitset2), expected);
        assertEquals(bitset.union(new Bitset<>(elements)), bitset);
    }

    @Test
    void intersections() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        List<Integer> init1 = Arrays.asList(2, 3, 4);
        List<Integer> init2 = Arrays.asList(3, 4);
        bitset.addAll(init);
        Bitset<Integer> bitset1 = new Bitset<>(elements);
        bitset1.addAll(init1);
        Bitset<Integer> bitset2 = new Bitset<>(elements);
        bitset2.addAll(init2);
        Bitset<Integer> expected = new Bitset<>(elements);
        expected.add(2);

        assertEquals(bitset.intersections(bitset1), expected);
        assertEquals(bitset.intersections(bitset2), new Bitset<>(elements));
        assertEquals(bitset.intersections(bitset), bitset);
    }

    @Test
    void complements() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        List<Integer> init1 = Arrays.asList(3, 4, 5);
        bitset.addAll(init);
        Bitset<Integer> bitset1 = new Bitset<>(elements);
        bitset1.addAll(init1);
        Bitset<Integer> bitset2 = new Bitset<>(elements);
        bitset2.addAll(elements);

        assertEquals(bitset.complements(), bitset1);
        assertEquals(bitset1.complements(), bitset);
        assertEquals(bitset2.complements(), new Bitset<>(elements));
        assertEquals(new Bitset<>(elements).complements(), bitset2);
    }

    @Test
    void contains() {
        bitset.add(3);
        assertTrue(bitset.contains(3));
        assertFalse(bitset.contains(1));
    }
}