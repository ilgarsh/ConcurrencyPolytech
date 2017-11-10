package practice3.concurrency;

import practice3.concurrency.readers.NotSyncReader;
import practice3.concurrency.writers.NotSyncWriter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Writer[] writers = new Writer[3];

        for (int i = 0, k = 0; i < writers.length; i++, k+=100) {
            writers[i] = new NotSyncWriter(
                    IntStream.range(k, k + 100)
                            .boxed()
                            .collect(Collectors.toList()));
        }
        Reader[] readers = new Reader[5];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new NotSyncReader();
        }

        ReadWriteBuffer readWriteBuffer = new ReadWriteBuffer(writers, readers);
        List<Integer> allMessages = readWriteBuffer.getAllMessages();
        List<Integer> readMessages = readWriteBuffer.getReadMessages();

        System.out.println(
                allMessages.stream()
                .filter(integer -> !readMessages.contains(integer))
                .count());

        System.out.println(
                readMessages.stream()
                        .filter(integer -> Collections.frequency(readMessages, integer) > 1)
                        .count());

        System.out.println(readWriteBuffer.getWorkTime());

    }

}
