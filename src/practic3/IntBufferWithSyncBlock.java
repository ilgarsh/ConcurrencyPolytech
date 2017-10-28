package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class IntBufferWithSyncBlock {

    private final Object lock = new Object();

    private int buffer;

    private boolean isEmptyBuffer = true;
    private boolean isFinish = false;
    private int[] messages;
    private List<Integer> readMessages;

    private int i = 0;

    private IntBufferWithSyncBlock(int maxNumber) {
        messages = IntStream.range(0, maxNumber).toArray();
        readMessages = new ArrayList<>(messages.length);
    }

    private void write() {
        while (i < messages.length) {
            synchronized (lock) {
                if (isEmptyBuffer) {
                    buffer = messages[i++];
                    isEmptyBuffer = false;
                }
            }
        }
        isFinish = true;
    }

    private void read() {
        while (!isFinish) {
            synchronized (lock) {
                if (!isEmptyBuffer) {
                    readMessages.add(buffer);
                    isEmptyBuffer = true;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntBufferWithSyncBlock intBuffer = new IntBufferWithSyncBlock(10000);
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
