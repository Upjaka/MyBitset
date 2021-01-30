import java.util.*;

public class Bitset<E> extends AbstractSet<E> implements Set<E> {
    private final ArrayList<E> elements;
    private final Set<E> set;

    public Bitset(Set<E> s) {
        set = new HashSet<>();
        elements = new ArrayList<>(s);
    }

    public Bitset() {
        set = new HashSet<>();
        elements = new ArrayList<>();
    }

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
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return new BitsetIterator();
    }

    public Bitset<E> union(Bitset<E> other) {
        Bitset<E> result = new Bitset<>(elements);
        result.addAll(set);
        result.addAll(other.set);
        return result;
    }

    public Bitset<E> intersections(Bitset<E> other) {
        Bitset<E> result = new Bitset<>(elements);
        for (E e : other.set) {
            if (set.contains(e)) result.add(e);
        }
        return result;
    }

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