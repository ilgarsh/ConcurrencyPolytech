package practic1.concurrency;

public class Main {

    public static void main(String[] args) {
        final int capacity = 100_000;
        final int nThreads = 5;
        final double value = 0.33;

        Vector<Double> vector = new Vector<>(capacity);
        vector.initVector(Math.E);

        ParallelHandler<Double> parallelHandler = new ParallelHandler<>();
        CircularParallelHandler<Double> circularParallelHandler = new CircularParallelHandler<>();

        //sequential handle with easy operation
        vector.changeElements((o, i) -> Math.pow(o, value));

        //parallel handle with east operation
        Vector<Double> result1 = parallelHandler.handle(vector, (o, i) -> Math.pow(o, value), nThreads);

        //circular parallel handle with easy operation
        Vector<Double> result2 = circularParallelHandler.handle(vector, (o, i) -> Math.pow(o, value), nThreads);

        //parallel handle with hard operation
        Vector<Double> result3 = parallelHandler.handle(vector, (aDouble, integer) -> {
            for (int i = 0; i < integer; i++) {
                aDouble += Math.pow(aDouble, value);
            }
            return aDouble;
        }, nThreads);

        //circular parallel handle with hard operation
        Vector<Double> result4 = circularParallelHandler.handle(vector, (aDouble, integer) -> {
            for (int i = 0; i < integer; i++) {
                aDouble += Math.pow(aDouble, value);
            }
            return aDouble;
        }, nThreads);

        
    }
}
