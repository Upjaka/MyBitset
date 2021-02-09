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
public class Bitset<E> implements Iterable<E> {
    private final List<E> elements;

    private final boolean[] bits;

    private final int size;

    public Bitset(Collection<? extends E> collection) {
        elements = new ArrayList<>(new HashSet<>(collection));
        size = elements.size();
        bits = new boolean[size];
        Arrays.fill(bits, false);
    }

    /**
     *
     * @return набор индексов
     */
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

    public boolean contains(E e) {
        if (!elements.contains(e)) return false;
        return bits[elements.indexOf(e)];
    }

    public boolean add(int index) {
        if (index < 0 || index >= size) return false;
        if (bits[index]) return false;
        else {
            bits[index] = true;
            return true;
        }
    }

    public boolean addAll(Collection<Integer> c) {
        boolean modified = false;
        for (int i : c)
            if (add(i)) {
                modified = true;
            }
        return modified;
    }

    public boolean remove(int index) {
        if (bits[index]) {
            bits[index] = false;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean removeAll(Collection<Integer> c) {
        boolean modified = false;
        for (int i : c)
            if (remove(i)) {
                modified = true;
            }
        return modified;
    }

    /**
     * Объединение двух множеств
     */
    public Bitset<E> union(Bitset<E> other) {
        if (elements.equals(other.elements) || elements.containsAll(other.elements)) {
            Bitset<E> result = new Bitset<>(elements);
            Set<Integer> indices = indices();
            indices.addAll(other.indices());
            result.addAll(indices);
            return result;
        }
        else {
            if (other.elements.containsAll(elements)) {
                Bitset<E> result = new Bitset<>(other.elements);
                Set<Integer> indices = indices();
                indices.addAll(other.indices());
                result.addAll(indices);
                return result;
            }
        }
        throw  new IllegalArgumentException("the argument has an invalid set");
    }

    /**
     * Пересечение двух множеств
     */
    public Bitset<E> intersections(Bitset<E> other) {
        Set<Integer> indices = indices();
        Set<Integer> otherIndices = other.indices();
        if (elements.equals(other.elements) || elements.containsAll(other.elements)) {
            Bitset<E> result = new Bitset<>(elements);
            for (int i: indices) {
                if (otherIndices.contains(i)) result.add(i);
            }
            return result;
        }
        else {
            if (other.elements.containsAll(elements)) {
                Bitset<E> result = new Bitset<>(other.elements);
                for (int i: otherIndices) {
                    if (indices.contains(i)) result.add(i);
                }
            }
        }
        throw  new IllegalArgumentException("the argument has an invalid set");
    }

    /**
     * Дополнение множества
     */
    public Bitset<E> complements() {
        Bitset<E> result = new Bitset<>(elements);
        for (int i = 0; i < size; i++) {
            result.bits[i] = !bits[i];
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bitset<?> other = (Bitset<?>) o;
        return elements.equals(other.elements) &&
                Arrays.equals(bits, other.bits);
    }

    @Override
    public Iterator<E> iterator() { return new BitsetIterator(); }

    private class BitsetIterator implements Iterator<E> {
        int cursor;
        int nextElem;

        public BitsetIterator() {
            cursor = 0;
            nextElem = 0;
            for (int i = 1; i < size; i++) {
                if (bits[i]) nextElem = i;
            }
        }

        public boolean hasNext() {
            return nextElem != cursor;
        }

        public E next() {
            cursor = nextElem;
            for (int i = 1; i < size; i++) {
                if (bits[i]) nextElem = i;
            }
            return elements.get(cursor);
        }
    }
}