import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

final class BitsetTest {
    final List<Integer> allNumbers = Arrays.asList(0, 1, 2, 3, 4, 5);
    final int size = 6;
    Bitset bitset = new Bitset(size);


    @Test
    void testEquals() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        bitset.addAll(init);
        Bitset notEquals = new Bitset(size);
        notEquals.addAll(Arrays.asList(3, 4, 5));
        Bitset equals = new Bitset(size);
        equals.addAll(init);

        assertNotEquals(notEquals, bitset);
        assertEquals(bitset, bitset);
        assertEquals(bitset, equals);
    }

    @Test
    void add() {
        List<Integer> expected = new LinkedList<>(Collections.singleton(0));
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
        assertEquals(new LinkedList<>(init), bitset.indices());
    }

    @Test
    void remove() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        bitset.addAll(init);
        List<Integer> expected = new LinkedList<>(init);
        expected.remove(2);

        assertTrue(bitset.remove(2));
        assertEquals(expected, bitset.indices());
        assertFalse(bitset.remove(4));
    }

    @Test
    void removeAll() {
        List<Integer> expected = Arrays.asList(4, 5);
        List<Integer> init = Arrays.asList(0, 1, 2, 3);
        bitset.addAll(allNumbers);

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
        Bitset bitset1 = new Bitset(size);
        bitset1.addAll(init1);
        Bitset bitset2 = new Bitset(size);
        bitset2.addAll(init2);
        Bitset expected = new Bitset(size);
        expected.addAll(allNumbers);

        assertEquals(bitset.union(bitset1), expected);
        assertEquals(bitset.union(bitset2), expected);
        assertEquals(bitset.union(new Bitset(size)), bitset);
        assertThrows(IllegalArgumentException.class, () -> bitset.union(new Bitset(10)));
    }

    @Test
    void intersections() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        List<Integer> init1 = Arrays.asList(2, 3, 4);
        List<Integer> init2 = Arrays.asList(3, 4);
        bitset.addAll(init);
        Bitset bitset1 = new Bitset(size);
        bitset1.addAll(init1);
        Bitset bitset2 = new Bitset(size);
        bitset2.addAll(init2);
        Bitset expected = new Bitset(size);
        expected.add(2);

        assertEquals(bitset.intersections(bitset1), expected);
        assertEquals(bitset.intersections(bitset2), new Bitset(size));
        assertEquals(bitset.intersections(bitset), bitset);
        assertThrows(IllegalArgumentException.class, () -> bitset.intersections(new Bitset(10)));
    }

    @Test
    void complements() {
        List<Integer> init = Arrays.asList(0, 1, 2);
        List<Integer> init1 = Arrays.asList(3, 4, 5);
        bitset.addAll(init);
        Bitset bitset1 = new Bitset(size);
        bitset1.addAll(init1);
        Bitset bitset2 = new Bitset(size);
        bitset2.addAll(allNumbers);

        assertEquals(bitset.complements(), bitset1);
        assertEquals(bitset1.complements(), bitset);
        assertEquals(bitset2.complements(), new Bitset(size));
        assertEquals(new Bitset(size).complements(), bitset2);
    }

    @Test
    void contains() {
        bitset.add(3);
        assertTrue(bitset.contains(3));
        assertFalse(bitset.contains(1));
    }

    @Test
    void iterator() {
        List<Integer> actual = new LinkedList<>();
        for (int i : bitset) {
            actual.add(i);
        }
        assertEquals(Collections.EMPTY_LIST, actual);
        actual.clear();

        bitset.add(0);
        for (int i : bitset) {
            actual.add(i);
        }
        assertEquals(Collections.singletonList(0), actual);
        actual.clear();

        final List<Integer> expected = Arrays.asList(0, 2, 4);
        bitset.addAll(expected);
        for (int i : bitset) {
            actual.add(i);
        }
        assertEquals(expected, actual);
        actual.clear();

        bitset.addAll(allNumbers);
        for (int i : bitset) {
            actual.add(i);
        }
        assertEquals(allNumbers, actual);
    }
}