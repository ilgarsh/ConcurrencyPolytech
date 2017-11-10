package practice2.concurrency;

import practice2.concurrency.algorithms.DecompositionNumbersAlgorithm;
import practice2.concurrency.algorithms.DecompositionPrimesAlgorithm;
import practice2.concurrency.algorithms.SequentialSearchAlgorithm;
import practice2.concurrency.algorithms.ThreadPoolAlgorithm;

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

        int[] primes4 = finderPrimes.find(100, SequentialSearchAlgorithm.INSTANCE.withNThreads(5));
        for (int prime :
                primes4) {
            System.out.println(prime);
        }

        int[] primes5 = EratosthenesAlgorithm.getAllPrimes(100);
        for (int prime :
                primes5) {
            System.out.println(prime);
        }
    }
}
