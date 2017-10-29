package practic3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntBufferWithoutSynchronized implements IntBuffer{

    private int buffer;

    //у JVM для каждого потока есть свой стек, где он может кешировать различные переменные.
    //следовательно, меняя значение isEmptyBuffer, не гарантируется, что он поменяется в другом потоке.
    //volatile запрещает данное кеширование.
    private volatile boolean isEmptyBuffer = true;
    private boolean isFinish = false;
    private List<Integer> messages;
    private List<Integer> readMessages;

    private int i = 0;

    public IntBufferWithoutSynchronized(int maxNumber) {
        messages = IntStream.range(0, maxNumber).boxed().collect(Collectors.toList());
        readMessages = new ArrayList<>(messages.size());
    }

    @Override
    public void write() {
        while (i < messages.size()) {
            if (isEmptyBuffer) {
                buffer = messages.get(i++);
                isEmptyBuffer = false;
            }
        }
        isFinish = true;
    }

    @Override
    public void read() {
        while (!isFinish) {
            if (!isEmptyBuffer) {
                readMessages.add(buffer);
                isEmptyBuffer = true;
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
