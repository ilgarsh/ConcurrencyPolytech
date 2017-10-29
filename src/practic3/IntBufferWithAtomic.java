package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntBufferWithAtomic implements IntBuffer {
    private int buffer;

    private AtomicBoolean isEmptyBuffer;
    private boolean isFinish = false;
    private List<Integer> messages;
    private List<Integer> readMessages;

    private volatile int i = 0;

    public IntBufferWithAtomic(int maxNumber) {
        messages = IntStream.range(0, maxNumber).boxed().collect(Collectors.toList());
        readMessages = new ArrayList<>(messages.size());
        isEmptyBuffer = new AtomicBoolean(true);
    }

    @Override
    public void write() {
        while (i < messages.size()) {
            if (isEmptyBuffer.get()) {
                buffer = messages.get(i++);
                isEmptyBuffer.compareAndSet(true, false);
            }
        }
        isFinish = true;
    }

    @Override
    public void read() {
        while (!isFinish) {
            if (!isEmptyBuffer.get()) {
                readMessages.add(buffer);
                isEmptyBuffer.set(true);
            }
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
