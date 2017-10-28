package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class IntBufferWithEvents {
    private int buffer;

    private volatile AutoResetEvent eventFullBuffer = new AutoResetEvent(false);
    private volatile AutoResetEvent eventEmptyBuffer = new AutoResetEvent(true);

    private boolean isFinish = false;
    private int[] messages;
    private List<Integer> readMessages;

    private volatile int i = 0;

    private IntBufferWithEvents(int maxNumber) {
        messages = IntStream.range(0, maxNumber).toArray();
        readMessages = new ArrayList<>(messages.length);
    }

    private void write() throws InterruptedException {
        while (i < messages.length) {
            eventEmptyBuffer.waitOne(100);
            buffer = messages[i++];
            eventFullBuffer.set();
        }
        isFinish = true;
    }

    private void read() throws InterruptedException {
        while (!isFinish) {
            eventFullBuffer.waitOne(100);
            readMessages.add(buffer);
            eventEmptyBuffer.set();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntBufferWithEvents intBuffer = new IntBufferWithEvents(10000);
        ExecutorService service = Executors.newCachedThreadPool();
        int nWriters = 100;
        for (int i = 0; i < nWriters; i++) {
            service.submit(() -> {
                try {
                    intBuffer.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        int nReaders = 100;
        for (int i = 0; i < nReaders; i++) {
            service.submit(() -> {
                try {
                    intBuffer.write();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();

        while (!service.awaitTermination(24L, TimeUnit.HOURS)) {}

        System.out.println(Utils.getMissingAndRepeatingNumbers(
                intBuffer.messages, intBuffer.readMessages)[0]);

        System.out.println(Utils.getMissingAndRepeatingNumbers(
                intBuffer.messages, intBuffer.readMessages)[1]);
    }
}
