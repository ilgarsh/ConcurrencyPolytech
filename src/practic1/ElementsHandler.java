package practic1;

import java.util.function.BiFunction;

public interface ElementsHandler<E extends Number> {
    Vector<E> handle(Vector<E> vector, BiFunction<E, Integer, E> function, int nThreads);
}
