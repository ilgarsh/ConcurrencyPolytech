package practice1.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;

public class ParallelHandler<E extends Number> implements ElementsHandler<E> {

    @Override
    public Vector<E> handle(Vector<E> vector, BiFunction<E, Integer, E> function, int nThreads) {
        Vector<E> results = new Vector<>(vector);
        int range = vector.getSize() / nThreads;
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<Callable<Object>> calls = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads - 1; i++) {
            int start = range * i;
            int end = start + range;
            calls.add(() -> results.changeElements(function, start, end));
        }
        int start = range * (nThreads - 1);
        int end = vector.getSize();
        calls.add(() -> results.changeElements(function, start, end));
        try {
            executor.invokeAll(calls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return results;
    }
}
