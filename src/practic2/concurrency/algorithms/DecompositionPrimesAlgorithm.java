package practic2.concurrency.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DecompositionPrimesAlgorithm implements FinderAlgorithm{

    private int nThreads = 5;

    public static final DecompositionPrimesAlgorithm INSTANCE = new DecompositionPrimesAlgorithm();

    public DecompositionPrimesAlgorithm withNThreads(int nThreads) {
        this.nThreads = nThreads;
        return this;
    }

    private DecompositionPrimesAlgorithm() {}

    @Override
    public int[] getAllPrimes(int[] basePrimes, Utils.PrimeNumber[] numbers) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Callable<Object>> calls = new ArrayList<>(nThreads);

        this.nThreads = basePrimes.length < nThreads ? basePrimes.length : nThreads;
        int range = basePrimes.length / nThreads;

        for (int i = 0; i < nThreads - 1; i++) {
            int localStart = i * range;
            int localEnd = localStart + range;
            calls.add( () -> Utils.findPrimes(Arrays.copyOfRange(basePrimes, localStart, localEnd),
                    numbers));
        }
        int localStart = (nThreads - 1) * range;
        int localEnd = numbers.length;
        calls.add( () -> Utils.findPrimes(Arrays.copyOfRange(basePrimes, localStart, localEnd),
                numbers));

        try {
            executorService.invokeAll(calls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        return Utils.getPrimes(numbers);
    }


}
