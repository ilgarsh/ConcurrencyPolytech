package practice1.streamapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static int start = 1;

    public static void main(String[] args) {
        int capacity = 10;
        final double value = 0.33;

        Collection<Double> collection = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            collection.add(Math.E);
        }

        //parallel handle with easy operation
        collection.parallelStream()
                .map(aDouble -> aDouble = Math.pow(aDouble, value))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // parallel handle with hard operation
        List<Double> results = new ArrayList<>(capacity);
        collection
                .parallelStream()
                .forEachOrdered(
                        aDouble -> {
                            for (int i = 0; i < start; i++) {
                                aDouble += Math.pow(aDouble, value);
                            }
                            start++;
                            results.add(aDouble);
                        });
        results.forEach(System.out::println);
    }
}
