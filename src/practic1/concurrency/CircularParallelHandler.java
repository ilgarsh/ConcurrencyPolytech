package practic1.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;

public class CircularParallelHandler<E extends Number> implements ElementsHandler<E> {
    @Override
    public Vector<E> handle(Vector<E> vector, BiFunction<E, Integer, E> function, int nThreads) {
        Vector<E> results = new Vector<>(vector);
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<Callable<Object>> calls = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            int j = i;
            calls.add(() -> results.changeElementsWithStep(function, j, nThreads));
        }
        try {
            executor.invokeAll(calls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return results;
    }
}
