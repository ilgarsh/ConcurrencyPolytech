package practice3.concurrency.writers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Writer;

import java.util.List;

public class NotSyncWriter implements Writer {
    final private List<Integer> myMessages;
    public NotSyncWriter(List<Integer> myMessages) {
        this.myMessages = myMessages;
    }

    @Override
    public void write() {
        int i = 0;
        while (i < myMessages.size()) {
            if (GlobalBuffer.isEmpty) {
                GlobalBuffer.buffer = myMessages.get(i++);
                GlobalBuffer.isEmpty = false;
            }
        }
    }

    @Override
    public List<Integer> getMessages() {
        return myMessages;
    }
}
