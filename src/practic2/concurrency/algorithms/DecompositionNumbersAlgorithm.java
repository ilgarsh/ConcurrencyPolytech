package practic2.concurrency.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DecompositionNumbersAlgorithm implements FinderAlgorithm{

    private int nThreads = 5;

    public static final DecompositionNumbersAlgorithm INSTANCE = new DecompositionNumbersAlgorithm();

    public DecompositionNumbersAlgorithm withNThreads(int nThreads) {
        this.nThreads = nThreads;
        return this;
    }

    private DecompositionNumbersAlgorithm(){}

    @Override
    public int[] getAllPrimes(int[] basePrimes, Utils.PrimeNumber[] numbers) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Callable<Object>> calls = new ArrayList<>(nThreads);

        int start = (int) Math.sqrt(numbers.length);
        int range = ( numbers.length - start ) / nThreads;
        for (int i = 0; i < nThreads - 1; i++) {
            int localStart = i * range;
            int localEnd = localStart + range;
            calls.add(() -> Utils.findPrimes(basePrimes, numbers, localStart, localEnd));
        }
        int localStart = (nThreads - 1) * range;
        int localEnd = numbers.length;
        calls.add(() -> Utils.findPrimes(basePrimes, numbers, localStart, localEnd));

        try {
            executorService.invokeAll(calls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        return Utils.getPrimes(numbers);
    }
}
