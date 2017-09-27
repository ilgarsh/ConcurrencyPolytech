package practic1;

public class Main {
    public static void main(String[] args) {
        Vector<Double> vector = new Vector<>(100);
        vector.initVector((double) 3);

        ParallelHandler<Double> parallelHandler = new ParallelHandler<>();
        CircularParallelHandler<Double> circularParallelHandler = new CircularParallelHandler<>();

        //sequential handle with easy operation
        vector.changeElements((o, i) -> Math.pow(o, 2));

        //parallel handle with east operation
        Vector<Double> result1 = parallelHandler.handle(vector, (o, i) -> Math.pow(o, 2), 5);

        //circular parallel handle with easy operation
        Vector<Double> result2 = circularParallelHandler.handle(vector, (o, i) -> Math.pow(o, 2), 5);

        //parallel handle with hard operation
        Vector<Double> result3 = parallelHandler.handle(vector, (aDouble, integer) -> {
            for (int i = 0; i < integer; i++) {
                aDouble += Math.pow(aDouble, 0.33);
            }
            return aDouble;
        }, 5);

        //circular parallel handle with hard operation
        Vector<Double> result4 = circularParallelHandler.handle(vector, (aDouble, integer) -> {
            for (int i = 0; i < integer; i++) {
                aDouble += Math.pow(aDouble, 0.33);
            }
            return aDouble;
        }, 5);
    }
}
