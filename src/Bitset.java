import java.util.*;

/**
 * Вариант 18 -- bitset [Java]
 * <p>
 * Реализовать множество над заданным набором объектов. Количество элементов в наборе задается в конструкторе.
 * Конкретный элемент набора идентифицируется неотрицательным целым от нуля до количества элементов - 1
 * (альтернатива -- уникальным именем).
 * Операции: пересечение, объединение, дополнение; добавление/удаление заданного элемента (массива элементов),
 * проверка принадлежности элемента множеству.
 * Бонус: итератор по множеству.
 *
 * @author Лудов Александр.
 */
public class Bitset implements Iterable<Integer> {
    private final boolean[] bits;

    private final int size;

    public Bitset(int size) {
        this.size = size;
        bits = new boolean[size];
        Arrays.fill(bits, false);
    }

    public Set<Integer> indices() {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (bits[i]) result.add(i);
        }
        return result;
    }

    public boolean contains(int index) {
        return bits[index];
    }

    public boolean add(int index) {
        if (index < 0 || index >= size) return false;
        if (bits[index]) return false;
        else {
            bits[index] = true;
            return true;
        }
    }

    public boolean addAll(Collection<Integer> collection) {
        boolean modified = false;
        for (int i : collection)
            if (add(i)) {
                modified = true;
            }
        return modified;
    }

    public boolean remove(int index) {
        if (bits[index]) {
            bits[index] = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeAll(Collection<Integer> collection) {
        boolean modified = false;
        for (int i : collection)
            if (remove(i)) {
                modified = true;
            }
        return modified;
    }

    /**
     * Объединение двух множеств
     */
    public Bitset union(Bitset other) {
        if (size != other.size) throw new IllegalArgumentException("the argument has an invalid size");
        Bitset result = new Bitset(size);
        result.addAll(indices());
        result.addAll(other.indices());
        return result;
    }

    /**
     * Пересечение двух множеств
     */
    public Bitset intersections(Bitset other) {
        if (size != other.size) throw new IllegalArgumentException("the argument has an invalid size");
        Bitset result = new Bitset(size);
        Set<Integer> indices = indices();
        Set<Integer> otherIndices = other.indices();
        for (int i : indices) {
            if (otherIndices.contains(i) || indices.contains(i)) result.add(i);
        }
        return result;
    }

    /**
     * Дополнение множества
     */
    public Bitset complements() {
        Bitset result = new Bitset(size);
        for (int i = 0; i < size; i++) {
            result.bits[i] = !bits[i];
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (bits[i]) sb.append(1);
            else sb.append(0);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bitset other = (Bitset) o;
        return size == other.size && Arrays.equals(bits, other.bits);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new BitsetIterator();
    }

    private class BitsetIterator implements Iterator<Integer> {
        int cursor;
        int nextIndex;

        public BitsetIterator() {
            cursor = 0;
            nextIndex = 0;
            for (int i = 1; i < size; i++) {
                if (bits[i]) nextIndex = i;
            }
        }

        public boolean hasNext() {
            return nextIndex != cursor;
        }

        public Integer next() {
            cursor = nextIndex;
            for (int i = 1; i < size; i++) {
                if (bits[i]) nextIndex = i;
            }
            return cursor;
        }
    }
}