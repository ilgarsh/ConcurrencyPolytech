package practice3.concurrency;

import java.util.ArrayList;
import java.util.List;

public class ReadWriteBuffer {
    private long workTime;

    private Writer[] writers;
    private Reader[] readers;

    private List<Integer> allMessages = new ArrayList<>();
    private List<Integer> readMessages = new ArrayList<>();

    public ReadWriteBuffer(Writer[] writers, Reader[] readers) {

        this.writers = writers;
        this.readers = readers;
        workTime = System.currentTimeMillis();
        start();
        workTime = Math.abs(workTime - System.currentTimeMillis());
        GlobalBuffer.reset();
    }

    private void start() {
        Thread[] writerThreads = new Thread[writers.length];
        Thread[] readerThreads = new Thread[readers.length];
        for (int i = 0; i < writers.length; i++) {
            allMessages.addAll(writers[i].getMessages());
            writerThreads[i] = new Thread(writers[i]::write);
            writerThreads[i].start();
        }
        for (int i = 0; i < readers.length; i++) {
            readerThreads[i] = new Thread(readers[i]::read);
            readerThreads[i].start();
        }
        for (int i = 0; i < writers.length; i++) {
            try {
                writerThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //for NotSync
        GlobalBuffer.isFinish = true;

        //For Event
        GlobalBuffer.eventFull.set();

        //For Semaphore
        GlobalBuffer.readerSemaphore.release(readers.length);

        for (int i = 0; i < readers.length; i++) {
            try {
                readerThreads[i].join();
                readMessages.addAll(readers[i].getReadMessages());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    List<Integer> getAllMessages() {
        return allMessages;
    }

    List<Integer> getReadMessages() {
        return readMessages;
    }

    public long getWorkTime() {
        return workTime;
    }
}
