package practic3;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReadWriteBuffer {

    private int missingMessages;
    private int repeatingMessages;
    private List<Integer> messages;
    private List<Integer> sourceMessages;

    public ReadWriteBuffer(int nReaders, int nWriters, IntBuffer intBuffer) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < nWriters; i++) {
            service.submit(intBuffer::write);
        }
        for (int i = 0; i < nReaders; i++) {
            service.submit(intBuffer::read);
        }
        service.shutdown();
        try {
            while (!service.awaitTermination(24L, TimeUnit.HOURS)) {}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        missingMessages = -1;
        repeatingMessages = 0;
        messages = intBuffer.getReadMessages();
        sourceMessages = intBuffer.getSourceMessages();
    }

    public List<Integer> getMessages() {
        return messages;
    }

    public int getMissingMessages() {
        missingMessages = 0;
        for (Integer message : sourceMessages) {
            if (!messages.contains(message)) {
                missingMessages++;
            }
        }
        return missingMessages;
    }

    public int getRepeatingMessages() {
        missingMessages = missingMessages == -1 ? getMissingMessages() : missingMessages;
        for (Integer message : messages) {
            if (sourceMessages.contains(message)) {
                repeatingMessages++;
            }
        }
        return missingMessages + repeatingMessages - sourceMessages.size();
    }
}
