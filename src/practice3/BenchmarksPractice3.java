package practice3;


import practice3.concurrency.ReadWriteBuffer;
import practice3.concurrency.Reader;
import practice3.concurrency.Writer;
import practice3.concurrency.readers.*;
import practice3.concurrency.writers.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarksPractice3 {

    public static void main(String[] args) {
        int nWriters = 10;
        int nReaders = 10;
        int value = 100;

        System.out.println(getWorkTimeNotSync(nWriters, nReaders, value));
        System.out.println(getWorkTimeSimpleLock(nWriters, nReaders, value));
        System.out.println(getWorkTimeEvents(nWriters, nReaders, value));
        System.out.println(getWorkTimeAtomic(nWriters, nReaders, value));
        System.out.println(getWorkTimeSemaphore(nWriters, nReaders, value));
    }

    private static long getWorkTimeNotSync(int nWriters, int nReaders, int nWriterMessages) {
        Writer[] writers = new Writer[nWriters];

        for (int i = 0, k = 0; i < writers.length; i++, k+=nWriterMessages) {
            writers[i] = new NotSyncWriter(
                    IntStream.range(k, k + nWriterMessages)
                            .boxed()
                            .collect(Collectors.toList()));
        }

        Reader[] readers = new Reader[nReaders];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new NotSyncReader();
        }

        ReadWriteBuffer readWriteBuffer = new ReadWriteBuffer(writers, readers);

        return readWriteBuffer.getWorkTime();
    }

    private static long getWorkTimeSimpleLock(int nWriters, int nReaders, int nWriterMessages) {
        Writer[] writers = new Writer[nWriters];

        for (int i = 0, k = 0; i < writers.length; i++, k+=nWriterMessages) {
            writers[i] = new SimpleLockWriter(
                    IntStream.range(k, k + nWriterMessages)
                            .boxed()
                            .collect(Collectors.toList()));
        }

        Reader[] readers = new Reader[nReaders];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new SimpleLockReader();
        }

        ReadWriteBuffer readWriteBuffer = new ReadWriteBuffer(writers, readers);

        return readWriteBuffer.getWorkTime();
    }

    private static long getWorkTimeEvents(int nWriters, int nReaders, int nWriterMessages) {
        Writer[] writers = new Writer[nWriters];

        for (int i = 0, k = 0; i < writers.length; i++, k+=nWriterMessages) {
            writers[i] = new EventWriter(
                    IntStream.range(k, k + nWriterMessages)
                            .boxed()
                            .collect(Collectors.toList()));
        }

        Reader[] readers = new Reader[nReaders];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new EventReader();
        }

        ReadWriteBuffer readWriteBuffer = new ReadWriteBuffer(writers, readers);

        return readWriteBuffer.getWorkTime();
    }

    private static long getWorkTimeAtomic(int nWriters, int nReaders, int nWriterMessages) {
        Writer[] writers = new Writer[nWriters];

        for (int i = 0, k = 0; i < writers.length; i++, k+=nWriterMessages) {
            writers[i] = new AtomicWriter(
                    IntStream.range(k, k + nWriterMessages)
                            .boxed()
                            .collect(Collectors.toList()));
        }

        Reader[] readers = new Reader[nReaders];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new AtomicReader();
        }

        ReadWriteBuffer readWriteBuffer = new ReadWriteBuffer(writers, readers);

        return readWriteBuffer.getWorkTime();
    }

    private static long getWorkTimeSemaphore(int nWriters, int nReaders, int nWriterMessages) {
        Writer[] writers = new Writer[nWriters];

        for (int i = 0, k = 0; i < writers.length; i++, k+=nWriterMessages) {
            writers[i] = new SemaphoreWriter(
                    IntStream.range(k, k + nWriterMessages)
                            .boxed()
                            .collect(Collectors.toList()));
        }

        Reader[] readers = new Reader[nReaders];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new SemaphoreReader();
        }

        ReadWriteBuffer readWriteBuffer = new ReadWriteBuffer(writers, readers);

        return readWriteBuffer.getWorkTime();
    }
}
