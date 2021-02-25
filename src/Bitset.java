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

    /**
     * Конструктор битсета размера size
     */
    public Bitset(int size) {
        bits = new boolean[size];
        Arrays.fill(bits, false);
    }

    /**
     * Возвращает список индексов, значение которых равно true
     */
    public List<Integer> indices() {
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) result.add(i);
        }
        return result;
    }

    /**
     * @return true, если элемент с заданным индексом содержится в битсете
     */
    public boolean contains(int index) {
        return bits[index];
    }

    /**
     * Добавляет элемент с заданным индексом в битсет.
     * @return true, если битсет изменился
     */
    public boolean add(int index) {
        if (index < 0 || index >= bits.length || bits[index]) return false;
        bits[index] = true;
        return true;
    }

    /**
     * Добавляет все элементы с заданными индексами.
     * @return true, если битсет изменился
     */
    public boolean addAll(Collection<Integer> collection) {
        boolean modified = false;
        for (int i : collection)
            if (add(i)) {
                modified = true;
            }
        return modified;
    }

    /**
     * Удаляет элемент с заданным индексом из битсета.
     * @return true, если элемент находился в битсете
     */
    public boolean remove(int index) {
        if (bits[index]) {
            bits[index] = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Удаляет все элементы с заданными индексами из битсета.
     * @return true, если битсет изменился
     */
    public boolean removeAll(Collection<Integer> collection) {
        boolean modified = false;
        for (int i : collection)
            if (remove(i)) {
                modified = true;
            }
        return modified;
    }

    /**
     * Объединение двух битсетов. Объединить можно только битсеты одинакового размера.
     * @return битсет, который является объединением двух исходных битсетов
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public Bitset union(Bitset other) {
        if (bits.length != other.bits.length) throw new IllegalArgumentException("the argument has an invalid size");
        Bitset result = new Bitset(bits.length);
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] || other.bits[i]) result.bits[i] = true;
        }
        return result;
    }

    /**
     * Пересечение двух битсетов. Найти пересечение можно только у битсетов одинакового размера
     * @return битсет, который является пересечением двух исходных битсетов
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public Bitset intersections(Bitset other) {
        if (bits.length != other.bits.length) throw new IllegalArgumentException("the argument has an invalid size");
        Bitset result = new Bitset(bits.length);
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] && other.bits[i]) result.bits[i] = true;
        }
        return result;
    }

    /**
     * Дополнение множества
     * @return битсет, который является дополнением исходного битсета
     */
    public Bitset complements() {
        Bitset result = new Bitset(bits.length);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = !bits[i];
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (boolean bit : bits) {
            if (bit) sb.append(1);
            else sb.append(0);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bitset other = (Bitset) o;
        return bits.length == other.bits.length && Arrays.equals(bits, other.bits);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new BitsetIterator();
    }

    private class BitsetIterator implements Iterator<Integer> {
        int nextIndex;

        public BitsetIterator() {
            nextIndex = -1;
        }

        @Override
        public boolean hasNext() {
            for (int i = nextIndex + 1; i < bits.length; i++) {
                if (bits[i]) {
                    nextIndex = i;
                    return true;
                }
            }
            return false;
        }

        @Override
        public Integer next() {
            return nextIndex;
        }
    }
}