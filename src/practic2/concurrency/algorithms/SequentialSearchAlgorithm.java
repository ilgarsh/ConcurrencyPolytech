package practic2.concurrency.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SequentialSearchAlgorithm implements FinderAlgorithm {

    private int nThreads = 5;

    private static int currentIndex = 0;

    public static final SequentialSearchAlgorithm INSTANCE = new SequentialSearchAlgorithm();

    public SequentialSearchAlgorithm withNThreads(int nThreads) {
        this.nThreads = nThreads;
        return this;
    }

    @Override
    public int[] getAllPrimes(int[] basePrimes, Utils.PrimeNumber[] numbers) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Callable<Boolean>> calls = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            calls.add( () -> search(basePrimes, numbers));
        }

        try {
            executorService.invokeAll(calls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        return Utils.getPrimes(numbers);
    }

    private boolean search(int[] basePrimes, Utils.PrimeNumber[] numbers) {
        while (true) {

            int currentPrime;

            synchronized ("lock") {
                if (currentIndex >= basePrimes.length) {
                    break;
                }
                currentPrime = basePrimes[currentIndex++];
            }

            for (Utils.PrimeNumber number : numbers) {
                if (Utils.isNotPrime(number.getValue(), currentPrime)) {
                    number.setPrime(false);
                }
            }
        }
        return true;
    }
}
