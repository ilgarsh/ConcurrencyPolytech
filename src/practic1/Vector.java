package practic1;

import java.util.function.BiFunction;

class Vector<E extends Number> {

    private Object[] elements;

    Vector(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Illegal size: " + size);
        }
        this.elements = new Object[size];
    }

    Vector(Vector<E> vector) {
        this.elements = vector.getElements().clone();
    }

    Object[] getElements() {
        return elements;
    }

    int getSize() {
        return elements.length;
    }

    boolean initVector(E number) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = number;
        }
        return true;
    }

    boolean changeElements(BiFunction<E, Integer, E> function) {
        return changeElements(function, 0, elements.length);
    }

    boolean changeElements(BiFunction<E, Integer, E> function, int start, int end) {
        for (int i = start; i < end; i++) {
            elements[i] = function.apply((E) elements[i], i);
        }
        return true;
    }

    boolean changeElementsWithStep(BiFunction<E, Integer, E> function, int start, int step) {
        for (int i = start; i < elements.length; i += step) {
            elements[i] = function.apply((E) elements[i], i);
        }
        return true;
    }
}
