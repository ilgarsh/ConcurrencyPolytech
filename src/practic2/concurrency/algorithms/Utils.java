package practic2.concurrency.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static int[] findPrimes(
            int[] basePrimes,
            PrimeNumber[] numbers, int start, int end) {

        for (int i = start; i < end; i++) {
            for (Integer basePrime : basePrimes) {
                if (numbers[i].isPrime() && isNotPrime(numbers[i].getValue(), basePrime)) {
                    numbers[i].setPrime(false);
                    break;
                }
            }
        }

        return getPrimes(numbers);
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

    private static boolean isNotPrime(int number, int divider) {
        return number % divider == 0 && number != divider;
    }

    public static int[] concat(int[] first, int[] second) {
        int[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

}
