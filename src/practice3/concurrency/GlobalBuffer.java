package practice3.concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalBuffer {
    public static volatile int buffer;

    public static volatile boolean isEmpty = true;
    public static volatile boolean isFinish = false;

    public static volatile AutoResetEvent eventEmpty = new AutoResetEvent(true);
    public static volatile AutoResetEvent eventFull = new AutoResetEvent(false);

    public static final Object lock = new Object();

    public static volatile AtomicBoolean atomicEmpty = new AtomicBoolean(true);
    public static volatile AtomicBoolean atomicFull = new AtomicBoolean(false);

    public static volatile Semaphore writerSemaphore = new Semaphore(1);
    public static volatile Semaphore readerSemaphore = new Semaphore(0);

    static void reset() {
        isEmpty = true;
        isFinish = false;
        eventEmpty = new AutoResetEvent(true);
        eventFull = new AutoResetEvent(false);
        atomicEmpty = new AtomicBoolean(true);
        atomicFull = new AtomicBoolean(false);
        writerSemaphore = new Semaphore(1);
        readerSemaphore = new Semaphore(0);
    }

}
