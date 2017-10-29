package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntBufferWithSemaphore implements IntBuffer {
    private volatile int buffer;

    private Semaphore writerSemaphore = new Semaphore(1);
    private Semaphore readerSemaphore = new Semaphore(0);

    private volatile boolean isFinish = false;
    private List<Integer> messages;
    private List<Integer> readMessages;

    private int i = 0;

    public IntBufferWithSemaphore(int maxNumber) {
        messages = IntStream.range(0, maxNumber).boxed().collect(Collectors.toList());
        readMessages = new ArrayList<>(messages.size());
    }

    @Override
    public void write() {
        while (i < messages.size()) {
            writerSemaphore.acquireUninterruptibly();
            buffer = messages.get(i++);
            readerSemaphore.release();
        }
        isFinish = true;
    }

    @Override
    public void read() {
        while (!isFinish) {
            readerSemaphore.acquireUninterruptibly();
            readMessages.add(buffer);
            writerSemaphore.release();
        }
    }

    @Override
    public List<Integer> getReadMessages() {
        return readMessages;
    }

    @Override
    public List<Integer> getSourceMessages() {
        return messages;
    }
}
