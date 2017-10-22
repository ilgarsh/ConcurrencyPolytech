package practic2.concurrency.algorithms;

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
        Thread[] threads = new Thread[nThreads];

        int start = (int) Math.sqrt(numbers.length);
        int range = ( numbers.length - start ) / nThreads;
        for (int i = 0; i < nThreads - 1; i++) {
            int localStart = i * range;
            int localEnd = localStart + range;
            threads[i] = new Thread( () -> Utils.findPrimes(basePrimes, numbers, localStart, localEnd));
            threads[i].start();
        }
        int localStart = (nThreads - 1) * range;
        int localEnd = numbers.length;
        threads[nThreads - 1] = new Thread( () -> Utils.findPrimes(basePrimes, numbers, localStart, localEnd));
        threads[nThreads - 1].start();

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return Utils.getPrimes(numbers);
    }
}
