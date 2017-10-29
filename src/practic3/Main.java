package practic3;

public class Main {
    public static void main(String[] args) {

        int maxNumber = 10_000;
        int nReaders = 100, nWriters = 100;

        //Without sync
        ReadWriteBuffer buffer = new ReadWriteBuffer(nReaders, nWriters,
                new IntBufferWithoutSynchronized(maxNumber));

        System.out.println("Without sync: miss = " +
                buffer.getMissingMessages() + ", repeat = " + buffer.getRepeatingMessages());

        //Simple lock
        buffer = new ReadWriteBuffer(nReaders, nWriters,
                new IntBufferWithSimpleLock(maxNumber));

        System.out.println("Simple lock: miss = " +
                buffer.getMissingMessages() + ", repeat = " + buffer.getRepeatingMessages());

        //Events - problem with repeating messages
        buffer = new ReadWriteBuffer(nReaders, nWriters,
                new IntBufferWithEvents(maxNumber));

        System.out.println("Events: miss = " +
                buffer.getMissingMessages() + ", repeat = " + buffer.getRepeatingMessages());


        //Semaphore - problem with deadlock
        buffer = new ReadWriteBuffer(1, 1,
                new IntBufferWithSemaphore(maxNumber));

        System.out.println("Semaphore: miss = " +
                buffer.getMissingMessages() + ", repeat = " + buffer.getRepeatingMessages());
    }
}
