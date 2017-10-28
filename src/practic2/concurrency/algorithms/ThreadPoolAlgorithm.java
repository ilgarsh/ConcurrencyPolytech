package practic2.concurrency.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolAlgorithm implements FinderAlgorithm {

    public static final ThreadPoolAlgorithm INSTANCE = new ThreadPoolAlgorithm();

    private ThreadPoolAlgorithm(){}

    @Override
    public int[] getAllPrimes(int[] basePrimes, Utils.PrimeNumber[] numbers) {
        ExecutorService executorService = Executors.newWorkStealingPool();
        List<Callable<Object>> calls = new ArrayList<>(basePrimes.length);
        for (int basePrime : basePrimes) {
            executorService.submit( () -> Utils.findPrimes(new int[]{basePrime} , numbers));
        }
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(24L, TimeUnit.HOURS)) {}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Utils.getPrimes(numbers);
    }
}