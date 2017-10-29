package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntBufferWithEvents implements IntBuffer {
    private int buffer;

    private volatile AutoResetEvent eventFullBuffer = new AutoResetEvent(false);
    private volatile AutoResetEvent eventEmptyBuffer = new AutoResetEvent(true);

    private boolean isFinish = false;
    private List<Integer> messages;
    private List<Integer> readMessages;

    private volatile int i = 0;

    public IntBufferWithEvents(int maxNumber) {
        messages = IntStream.range(0, maxNumber).boxed().collect(Collectors.toList());
        readMessages = new ArrayList<>(messages.size());
    }

    @Override
    public void write() {
        while (i < messages.size()) {
            try {
                eventEmptyBuffer.waitOne(100);
                buffer = messages.get(i++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                eventFullBuffer.set();
            }
        }
        isFinish = true;
    }

    @Override
    public void read() {
        while (!isFinish) {
            try {
                eventFullBuffer.waitOne(100);
                readMessages.add(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                eventEmptyBuffer.set();
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
