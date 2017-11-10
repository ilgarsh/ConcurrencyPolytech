package practice2.concurrency.algorithms;

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
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread( () -> search(basePrimes, numbers));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return Utils.getPrimes(numbers);
    }

    private void search(int[] basePrimes, Utils.PrimeNumber[] numbers) {
        while (true) {

            int currentPrime;
            synchronized ("lock") {
                if (currentIndex >= basePrimes.length) {
                    break;
                }
                currentPrime = basePrimes[currentIndex++];
            }

            int start = basePrimes[basePrimes.length - 1] - 1;
            for (int i = start; i < numbers.length; i++) {
                if (numbers[i].isPrime() && numbers[i].getValue() % currentPrime == 0) {
                    numbers[i].setPrime(false);
                }
            }
        }
    }
}
