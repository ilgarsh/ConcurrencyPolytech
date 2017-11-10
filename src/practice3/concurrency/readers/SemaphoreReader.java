package practice3.concurrency.readers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Reader;

import java.util.ArrayList;
import java.util.List;

public class SemaphoreReader implements Reader {
    private List<Integer> readMessages = new ArrayList<>();

    @Override
    public void read() {
        while (!GlobalBuffer.isFinish) {
            GlobalBuffer.readerSemaphore.acquireUninterruptibly();
            if (GlobalBuffer.isFinish) break;
            readMessages.add(GlobalBuffer.buffer);
            GlobalBuffer.writerSemaphore.release();
        }
    }

    @Override
    public List<Integer> getReadMessages() {
        return readMessages;
    }
}
