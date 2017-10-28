package practic3;

import java.util.List;

public class Utils {
    public static int[] getMissingAndRepeatingNumbers(int[] numbers, List<Integer> whereToLook) {
        int countMissing = 0;
        int countExist = 0;
        for (int number : numbers) {
            if (!whereToLook.contains(number)) {
                countMissing++;
            } else {
                countExist++;
            }
        }
        return new int[]{countMissing, countExist + countMissing - numbers.length};
    }
}
