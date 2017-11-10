package practice3.concurrency.writers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Writer;

import java.util.List;

public class SemaphoreWriter implements Writer {
    final private List<Integer> myMessages;
    public SemaphoreWriter(List<Integer> myMessages) {
        this.myMessages = myMessages;
    }

    @Override
    public void write() {
        int i = 0;
        while (i < myMessages.size()) {
            GlobalBuffer.writerSemaphore.acquireUninterruptibly();
            try {
                GlobalBuffer.buffer = myMessages.get(i++);
                GlobalBuffer.readerSemaphore.release();
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }

    @Override
    public List<Integer> getMessages() {
        return myMessages;
    }
}
