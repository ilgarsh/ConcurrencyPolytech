package practic2.concurrency;

import practic2.concurrency.algorithms.Utils;
import practic2.concurrency.algorithms.FinderAlgorithm;

class FinderPrimes {
    static final FinderPrimes INSTANCE = new FinderPrimes();

      int[] find(int numbers) {

        Utils.PrimeNumber[] numberList = Utils.getNumbers(numbers);

        return EratosthenesAlgorithm.getAllPrimes(numberList);
    }

    int[] find(int numbers, FinderAlgorithm nextAlgorithm) {

        Utils.PrimeNumber[] numberList = Utils.getNumbers(numbers);

        assert numberList != null;
        int[] basePrimes = EratosthenesAlgorithm.getBasePrimes(numberList);

        return nextAlgorithm.getAllPrimes(basePrimes, numberList);
    }
}
