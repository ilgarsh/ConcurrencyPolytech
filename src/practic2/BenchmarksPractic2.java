package practic2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import practic2.concurrency.EratosthenesAlgorithm;
import practic2.concurrency.FinderPrimes;
import practic2.concurrency.algorithms.DecompositionNumbersAlgorithm;
import practic2.concurrency.algorithms.DecompositionPrimesAlgorithm;
import practic2.concurrency.algorithms.SequentialSearchAlgorithm;
import practic2.concurrency.algorithms.ThreadPoolAlgorithm;

import java.util.concurrent.TimeUnit;

public class BenchmarksPractic2 {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int[] m1_DecompositionNumbersAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10_000, DecompositionNumbersAlgorithm.INSTANCE.withNThreads(10));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int[] m2_DecompositionPrimesAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10_000, DecompositionPrimesAlgorithm.INSTANCE.withNThreads(10));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int[] m3_ThreadPoolAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10_000, ThreadPoolAlgorithm.INSTANCE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int[] m4_SequentialSearchAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10_000, SequentialSearchAlgorithm.INSTANCE.withNThreads(10));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int[] StandartAlgorithm() {
        return EratosthenesAlgorithm.getAllPrimes(10_000);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(practic2.BenchmarksPractic2.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .jvmArgs("-ea")
                .build();

        new Runner(options).run();
    }
}
