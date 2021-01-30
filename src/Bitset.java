import java.util.*;

/**
 * Вариант 18 -- bitset [Java]
 *
 * Реализовать множество над заданным набором объектов. Количество элементов в наборе задается в конструкторе.
 * Конкретный элемент набора идентифицируется неотрицательным целым от нуля до количества элементов - 1
 * (альтернатива -- уникальным именем).
 * Операции: пересечение, объединение, дополнение; добавление/удаление заданного элемента (массива элементов),
 * проверка принадлежности элемента множеству.
 * Бонус: итератор по множеству.
 *
 * @author Лудов Александр.
 */
public class Bitset<E> extends AbstractSet<E> implements Set<E> {
    /**
     * Набор элементов.
     */
    private final ArrayList<E> elements;
    /**
     * Множество над набором.
     */
    private final Set<E> set;

    /**
     * Конструктор
     * @param s задает набор над которым  реалуется множество.
     */
    public Bitset(Set<E> s) {
        set = new HashSet<>();
        elements = new ArrayList<>(s);
    }

    /**
     * Приватный конструктор, используется внутри класса в методах union(), intersections() и complements()
     * для создания дубликата исходного Bitset'а.
     */
    private Bitset(ArrayList<E> list) {
        set = new HashSet<>();
        elements = new ArrayList<>(list);
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public boolean add(E e) {
        if (!elements.contains(e)) return false;
        else return set.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (!elements.containsAll(c)) return false;
        else {
            boolean modified = false;
            for (E e : c)
                if (add(e)) {
                    modified = true;
                }
            return modified;
        }
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return new BitsetIterator();
    }

    /**
     * Объединение двух множеств
     */
    public Bitset<E> union(Bitset<E> other) {
        Bitset<E> result = new Bitset<>(elements);
        result.addAll(set);
        result.addAll(other.set);
        return result;
    }

    /**
     * Пересечение двух множеств
     */
    public Bitset<E> intersections(Bitset<E> other) {
        Bitset<E> result = new Bitset<>(elements);
        for (E e : other.set) {
            if (set.contains(e)) result.add(e);
        }
        return result;
    }

    /**
     * Дополнение множества
     */
    public Bitset<E> complements() {
        Bitset<E> result = new Bitset<>(elements);
        for (E e : elements) {
            if (!set.contains(e)) result.add(e);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bitset<?> bitset = (Bitset<?>) o;
        return Objects.equals(elements, bitset.elements) &&
                Objects.equals(set, bitset.set);
    }

    /**
     * По идее итератор можно и не писать, а использовать set.iterator().
     * Но раз в задании написано, я сделал. Правда получилось не очень, я не нашел,
     * как итераторы реализованы в Set.
     */
    private class BitsetIterator implements Iterator<E> {
        int index;

        public BitsetIterator() {
            index = 0;
        }

        public boolean hasNext() {
            if (index == size() - 1) return false;
            for (int i = index + 1; i < size(); i++) {
                if (set.contains(elements.get(i))) return true;
            }
            return false;
        }

        public E next() {
            if (index == size() - 1) throw new NoSuchElementException();
            index++;
            while (!set.contains(elements.get(index))) {
                index++;
            }
            return elements.get(index - 1);
        }
    }
}