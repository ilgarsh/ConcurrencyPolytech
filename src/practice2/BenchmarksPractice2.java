package practice2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import practice2.concurrency.EratosthenesAlgorithm;
import practice2.concurrency.FinderPrimes;
import practice2.concurrency.algorithms.DecompositionNumbersAlgorithm;
import practice2.concurrency.algorithms.DecompositionPrimesAlgorithm;
import practice2.concurrency.algorithms.SequentialSearchAlgorithm;
import practice2.concurrency.algorithms.ThreadPoolAlgorithm;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BenchmarksPractice2 {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int[] m1_DecompositionNumbersAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10000, DecompositionNumbersAlgorithm.INSTANCE.withNThreads(3));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int[] m2_DecompositionPrimesAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10000, DecompositionPrimesAlgorithm.INSTANCE.withNThreads(3));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int[] m3_ThreadPoolAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10000, ThreadPoolAlgorithm.INSTANCE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int[] m4_SequentialSearchAlgorithm() {
        return FinderPrimes.INSTANCE
                .find(10000, SequentialSearchAlgorithm.INSTANCE.withNThreads(3));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int[] m5_StreamAPI() {
        return IntStream.rangeClosed(2, 10000)
               .filter(i -> IntStream.rangeClosed(2, (int)Math.sqrt(i))
                       .allMatch(j -> i%j != 0)).toArray();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int[] StandartAlgorithm() {
        return EratosthenesAlgorithm.getAllPrimes(10000);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchmarksPractice2.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .jvmArgs("-ea")
                .build();

        new Runner(options).run();
    }
}
