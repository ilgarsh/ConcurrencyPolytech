package practic2.streamapi;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        IntStream.rangeClosed(2, 100)
                .filter(i -> IntStream.rangeClosed(2, (int)Math.sqrt(i))
                        .allMatch(j -> i%j != 0))
                .forEach(System.out::println);


    }
}
