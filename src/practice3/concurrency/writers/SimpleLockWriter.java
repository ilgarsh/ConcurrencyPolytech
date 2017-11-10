package practice3.concurrency.writers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Writer;

import java.util.List;

public class SimpleLockWriter implements Writer {
    final private List<Integer> myMessages;
    public SimpleLockWriter(List<Integer> myMessages) {
        this.myMessages = myMessages;
    }

    @Override
    public void write() {
        int i = 0;
        while (i < myMessages.size()) {
            synchronized (GlobalBuffer.lock) {
                if (GlobalBuffer.isEmpty) {
                    try {
                        GlobalBuffer.buffer = myMessages.get(i++);
                    } catch (IndexOutOfBoundsException ignored) {}
                    GlobalBuffer.isEmpty = false;
                }
            }
        }
    }

    @Override
    public List<Integer> getMessages() {
        return myMessages;
    }
}
