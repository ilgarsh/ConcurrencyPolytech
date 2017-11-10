package practice3.concurrency.readers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Reader;

import java.util.ArrayList;
import java.util.List;

public class SimpleLockReader implements Reader {
    private List<Integer> readMessages = new ArrayList<>();

    @Override
    public void read() {
        while (!GlobalBuffer.isFinish) {
            synchronized (GlobalBuffer.lock) {
                if (!GlobalBuffer.isEmpty) {
                    readMessages.add(GlobalBuffer.buffer);
                    GlobalBuffer.isEmpty = true;
                }
            }
        }
    }

    @Override
    public List<Integer> getReadMessages() {
        return readMessages;
    }
}
