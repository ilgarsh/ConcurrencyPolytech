package practic2.concurrency.algorithms;

import java.util.Arrays;

public class Utils {
    public static class PrimeNumber {
        private int value;
        private boolean isPrime;

        PrimeNumber(int value, boolean isPrime) {
            this.value = value;
            this.isPrime = isPrime;
        }

        public int getValue() {
            return value;
        }

        boolean isPrime() {
            return isPrime;
        }

        public void setPrime(boolean prime) {
            isPrime = prime;
        }
    }

    public static PrimeNumber[] getNumbers(int numbers) {
        PrimeNumber[] arrNumbers = new PrimeNumber[numbers - 2];
        for (int i=0, k = 2; k < numbers; i++, k++) {
            arrNumbers[i] = new PrimeNumber(k, true);
        }
        return arrNumbers;
    }


    public static int[] findPrimes(int[] basePrimes, PrimeNumber[] numbers) {
        return findPrimes(basePrimes, numbers, 0, numbers.length);
    }

    static int[] findPrimes(
            int[] basePrimes,
            PrimeNumber[] numbers, int start, int end) {

        for (int i = start; i < end; i++) {
            int j = 0;
            int currentPrime = basePrimes[j];
            while (j < basePrimes.length && currentPrime < numbers[i].getValue()) {
                if (numbers[i].getValue() % currentPrime == 0) {
                    numbers[i].setPrime(false);
                    break;
                }
                currentPrime = basePrimes[j++];
            }
        }
        return Utils.getPrimes(numbers);
    }

    public static int[] getPrimes(PrimeNumber[] numbers) {
        int[] primes = new int[numbers.length];
        int primesSize = 0;
        for (PrimeNumber number : numbers) {
            if (number.isPrime()) {
                primes[primesSize] = number.getValue();
                primesSize++;
            }
        }
        primes = Arrays.copyOf(primes, primesSize);
        return primes;
    }

    public static int[] concat(int[] first, int[] second) {
        int[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

}
