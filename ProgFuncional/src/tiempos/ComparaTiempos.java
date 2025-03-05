package tiempos;

import org.openjdk.jmh.annotations.*;
// import org.openjdk.jmh.runner.Runner;
// import org.openjdk.jmh.runner.options.Options;
// import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS)
public class ComparaTiempos {
   @Param({"1000", "50000", "500000"})
   static int listSize;

   static FlujoEnteros objeto = null;

   @Setup(Level.Trial)
   public void setUp() {
      objeto = new FlujoEnteros(listSize);
   }

   @Benchmark
   @OutputTimeUnit(TimeUnit.MILLISECONDS)
   @BenchmarkMode(Mode.AverageTime)
   public void sumaCuadradosReduce() {
      objeto.sumarFuncionalCuadradosReduce();
   }

   @Benchmark
   @OutputTimeUnit(TimeUnit.MILLISECONDS)
   @BenchmarkMode(Mode.AverageTime)
   public void sumaCuadradosMap() {
      objeto.sumarFuncionalCuadradosMap();
   }

   @Benchmark
   @OutputTimeUnit(TimeUnit.MILLISECONDS)
   @BenchmarkMode(Mode.AverageTime)
   public void sumaCuadradosMapParalelo() {
      objeto.sumarFuncionalCuadradosMapParalelo();
   }

   @Benchmark
   @OutputTimeUnit(TimeUnit.MILLISECONDS)
   @BenchmarkMode(Mode.AverageTime)
   public void sumaCuadradosImperativo() {
      objeto.sumarCuadradosImperativo();
   }
}
