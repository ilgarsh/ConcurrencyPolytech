package practice3.concurrency.writers;

import practice3.concurrency.GlobalBuffer;
import practice3.concurrency.Writer;

import java.util.List;

public class AtomicWriter implements Writer {
    final private List<Integer> myMessages;
    public AtomicWriter(List<Integer> myMessages) {
        this.myMessages = myMessages;
    }

    @Override
    public void write() {
        int i = 0;
        while (i < myMessages.size()) {
            if (GlobalBuffer.atomicEmpty.compareAndSet(true, false)) {
                GlobalBuffer.buffer = myMessages.get(i++);
                GlobalBuffer.atomicFull.lazySet(true);
            }
        }
    }

    @Override
    public List<Integer> getMessages() {
        return myMessages;
    }
}
