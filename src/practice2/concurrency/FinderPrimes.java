package practice2.concurrency;

import practice2.concurrency.algorithms.Utils;
import practice2.concurrency.algorithms.FinderAlgorithm;

public class FinderPrimes {
    public static final FinderPrimes INSTANCE = new FinderPrimes();

    public int[] find(int numbers, FinderAlgorithm nextAlgorithm) {

        Utils.PrimeNumber[] numberList = Utils.getNumbers(numbers);

        assert numberList != null;
        int[] basePrimes = EratosthenesAlgorithm.getBasePrimes(numberList);

        return nextAlgorithm.getAllPrimes(basePrimes, numberList);
    }
}
