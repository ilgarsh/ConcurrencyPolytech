package practic2.concurrency;

import practic2.concurrency.algorithms.Utils;

import java.util.Arrays;

public class EratosthenesAlgorithm {

    static int[] getBasePrimes(Utils.PrimeNumber[] numbers) { ;
        int end = (int) Math.sqrt(numbers.length);
        for (int i = 0; i < end; i++) {

            Utils.PrimeNumber currentNumber = numbers[i];
            for (int j = 0; j < i; j++) {

                if (currentNumber.getValue() % numbers[j].getValue() == 0) {
                    currentNumber.setPrime(false);
                    break;
                }
            }
        }

        return Arrays.copyOfRange(Utils.getPrimes(numbers), 0, end);
    }

    public static int[] getAllPrimes(int number) {
        Utils.PrimeNumber[] primeNumbers = Utils.getNumbers(number);

        for (int i = 0; i < primeNumbers.length; i++) {

            Utils.PrimeNumber currentNumber = primeNumbers[i];
            for (int j = 0; j < i; j++) {

                if (currentNumber.getValue() % primeNumbers[j].getValue() == 0) {
                    currentNumber.setPrime(false);
                    break;
                }
            }
        }

        return Utils.getPrimes(primeNumbers);
    }

    static int[] getAllPrimes(Utils.PrimeNumber[] numbers) {
        int[] basePrimes = getBasePrimes(numbers);
        int[] otherPrimes = Utils.findPrimes(basePrimes, numbers);
        return Utils.concat(basePrimes, otherPrimes);
    }
}
