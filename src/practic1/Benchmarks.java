package practic1;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import practic1.concurrency.CircularParallelHandler;
import practic1.concurrency.ParallelHandler;
import practic1.concurrency.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class Benchmarks {

    private int capacity;
    private int nThreads;
    private double value;
    private Vector<Double> vector;
    private CircularParallelHandler<Double> circularParallelHandler;
    private ParallelHandler<Double> parallelHandler;
    private Collection<Double> collection;
    private List<Double> results;
    private int start;

    @Setup
    public void prepare() {
        capacity = 10_000;
        nThreads = 7;
        value = 0.33;
        vector = new Vector<>(capacity);
        collection = new ArrayList<>(capacity);
        parallelHandler = new ParallelHandler<>();
        circularParallelHandler = new CircularParallelHandler<>();
        results = new ArrayList<>(capacity);
    }

    @Setup(Level.Iteration)
    public void init() {
        start = 0;
        vector.initVector(Math.E);
        collection.clear();
        for (int i = 0; i < capacity; i++) {
            collection.add(Math.E);
        }
        results.clear();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean m1_easy_sequential() {
        return vector.changeElements((d, o) -> d = Math.pow(d, value));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Vector<Double> m2_easy_parallel() {
        return parallelHandler.handle(vector, (d, o) -> d = Math.pow(d, value), nThreads);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Vector<Double> m3_easy_circular() {
        return circularParallelHandler.handle(vector, (d, o) -> d = Math.pow(d, value), nThreads);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean m4_easy_stream() {
        collection.parallelStream()
                .forEach(d -> {
                    d = Math.pow(d, value);
                    results.add(d);
                });
        return true;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean m1_hard_sequential() {
        return vector.changeElements((aDouble, integer) -> {
            for (int i = 0; i < integer; i++) {
                aDouble += Math.pow(aDouble, value);
            }
            return aDouble;
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Vector<Double> m2_hard_parallel() {
        return parallelHandler.handle(vector, (aDouble, integer) -> {
            for (int i = 0; i < integer; i++) {
                aDouble += Math.pow(aDouble, value);
            }
            return aDouble;
        }, nThreads);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Vector<Double> m3_hard_circular() {
        return circularParallelHandler.handle(vector, (aDouble, integer) -> {
            for (int i = 0; i < integer; i++) {
                aDouble += Math.pow(aDouble, value);
            }
            return aDouble;
        }, nThreads);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean m4_hard_stream() {
        collection
                .parallelStream()
                .forEachOrdered(
                        aDouble -> {
                            for (int i = 0; i < start; i++) {
                                aDouble += Math.pow(aDouble, value);
                            }
                            start++;
                            results.add(aDouble);
                        });
        return true;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(Benchmarks.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .jvmArgs("-ea")
                .build();

        new Runner(options).run();
    }
}
