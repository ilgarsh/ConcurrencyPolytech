package practice3.concurrency.readers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Reader;

import java.util.ArrayList;
import java.util.List;

public class AtomicReader implements Reader {
    private List<Integer> readMessages = new ArrayList<>();

    @Override
    public void read() {
        while (!GlobalBuffer.isFinish) {
            if (GlobalBuffer.atomicFull.compareAndSet(true, false)) {
                readMessages.add(GlobalBuffer.buffer);
                GlobalBuffer.atomicEmpty.lazySet(true);
            }
        }
    }

    @Override
    public List<Integer> getReadMessages() {
        return readMessages;
    }
}
