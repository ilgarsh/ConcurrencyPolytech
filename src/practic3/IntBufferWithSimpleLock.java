package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntBufferWithSimpleLock implements IntBuffer {

    private final Object lock = new Object();

    private int buffer;

    private boolean isEmptyBuffer = true;
    private boolean isFinish = false;
    private List<Integer> messages;
    private List<Integer> readMessages;

    private int i = 0;

    public IntBufferWithSimpleLock(int maxNumber) {
        messages = IntStream.range(0, maxNumber).boxed().collect(Collectors.toList());
        readMessages = new ArrayList<>(messages.size());
    }

    public void write() {
        while (i < messages.size()) {
            synchronized (lock) {
                if (isEmptyBuffer) {
                    buffer = messages.get(i++);
                    isEmptyBuffer = false;
                }
            }
        }
        isFinish = true;
    }

    public void read() {
        while (!isFinish) {
            synchronized (lock) {
                if (!isEmptyBuffer) {
                    readMessages.add(buffer);
                    isEmptyBuffer = true;
                }
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
