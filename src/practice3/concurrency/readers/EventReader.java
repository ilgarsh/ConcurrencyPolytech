package practice3.concurrency.readers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Reader;

import java.util.ArrayList;
import java.util.List;

public class EventReader implements Reader {
    private List<Integer> readMessages = new ArrayList<>();

    @Override
    public void read() {
        while (!GlobalBuffer.isFinish) {
            try {
                GlobalBuffer.eventFull.waitOne();
                if (GlobalBuffer.isFinish) {
                    GlobalBuffer.eventFull.set();
                    break;
                }
                readMessages.add(GlobalBuffer.buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                GlobalBuffer.eventEmpty.set();
            }
        }
    }

    @Override
    public List<Integer> getReadMessages() {
        return readMessages;
    }
}
