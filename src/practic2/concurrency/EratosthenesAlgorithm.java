package practic2.concurrency;

import practic2.concurrency.algorithms.Utils;

class EratosthenesAlgorithm {

    static int[] getBasePrimes(Utils.PrimeNumber[] numbers) {
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

        return Utils.getPrimes(numbers);
    }

    static int[] getAllPrimes(Utils.PrimeNumber[] numbers) {
        int[] basePrimes = getBasePrimes(numbers);
        int[] otherPrimes = Utils.findPrimes(basePrimes, numbers);
        return Utils.concat(basePrimes, otherPrimes);
    }
}
