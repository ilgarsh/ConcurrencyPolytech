package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class IntBufferWithoutSynchronized {

    private int buffer;

    //у JVM для каждого потока есть свой стек, где он может кешировать различные переменные.
    //следовательно, меняя значение isEmptyBuffer, не гарантируется, что он поменяется в другом потоке.
    //volatile запрещает данное кеширование.
    private volatile boolean isEmptyBuffer = true;
    private boolean isFinish = false;
    private int[] messages;
    private List<Integer> readMessages;

    private int i = 0;

    private IntBufferWithoutSynchronized(int maxNumber) {
        messages = IntStream.range(0, maxNumber).toArray();
        readMessages = new ArrayList<>(messages.length);
    }

    private void write() {
        while (i < messages.length) {
            if (isEmptyBuffer) {
                buffer = messages[i++];
                isEmptyBuffer = false;
            }
        }
        isFinish = true;
    }

    private void read() {
        while (!isFinish) {
            if (!isEmptyBuffer) {
                readMessages.add(buffer);
                isEmptyBuffer = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntBufferWithoutSynchronized intBuffer = new IntBufferWithoutSynchronized(10000);
        ExecutorService service = Executors.newCachedThreadPool();
        int nWriters = 10;
        for (int i = 0; i < nWriters; i++) {
            service.submit(intBuffer::write);
        }
        int nReaders = 10;
        for (int i = 0; i < nReaders; i++) {
            service.submit(intBuffer::read);
        }

        service.shutdown();

        System.out.println(Utils.getMissingAndRepeatingNumbers(
                intBuffer.messages, intBuffer.readMessages)[0]);

        //из-за volatile - количество повторяющихся элементов = 0
        System.out.println(Utils.getMissingAndRepeatingNumbers(
                intBuffer.messages, intBuffer.readMessages)[1]);
    }
}
