package practic2.concurrency;

import practic2.concurrency.algorithms.DecompositionNumbersAlgorithm;
import practic2.concurrency.algorithms.DecompositionPrimesAlgorithm;
import practic2.concurrency.algorithms.ThreadPoolAlgorithm;

public class Main {
    public static void main(String[] args) {
        FinderPrimes finderPrimes = FinderPrimes.INSTANCE;

        int[] primes1 = finderPrimes
                .find(100, DecompositionNumbersAlgorithm.INSTANCE.withNThreads(10));

        for (int prime :
                primes1) {
            System.out.println(prime);
        }

        int[] primes2 = finderPrimes
                .find(100, DecompositionPrimesAlgorithm.INSTANCE.withNThreads(10));

        for (int prime :
                primes2) {
            System.out.println(prime);
        }

        int[] primes3 = finderPrimes.find(100, ThreadPoolAlgorithm.INSTANCE);
        for (int prime :
                primes3) {
            System.out.println(prime);
        }
    }
}
