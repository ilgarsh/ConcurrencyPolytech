package practice3.concurrency.writers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Writer;

import java.util.List;

public class EventWriter implements Writer {
    final private List<Integer> myMessages;
    public EventWriter(List<Integer> myMessages) {
        this.myMessages = myMessages;
    }

    @Override
    public void write() {
        int i = 0;
        while (i < myMessages.size()) {
            try {
                GlobalBuffer.eventEmpty.waitOne();
                GlobalBuffer.buffer = myMessages.get(i++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                GlobalBuffer.eventFull.set();
            }
        }
    }

    @Override
    public List<Integer> getMessages() {
        return myMessages;
    }
}
