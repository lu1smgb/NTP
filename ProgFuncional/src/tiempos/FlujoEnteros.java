package tiempos;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class FlujoEnteros {
   // dato miembro para almacenar coleccion de valores
   private int[] valores;

   /**
    * Constructor de la clase
    */
   public FlujoEnteros(int  numeroValores){
      valores = new int[numeroValores];
      Random generador = new Random();
      generador.setSeed(0);
      // bucle de generacion
      for(int i=0; i < numeroValores; i++){
         valores[i] = generador.nextInt() % 101;
      }
   }

   /**
    * suma de valores al cuadrado con aproximacion reduce
    * @return
    */
   public double sumarFuncionalCuadradosReduce(){
      return Arrays.stream(valores).mapToDouble(x -> x).reduce(0, (x,y) -> x + y*y);
   }

   /**
    * suma de cuadrados mediante map + sum
    * @return
    */
   public double sumarFuncionalCuadradosMap() {
      return Arrays.stream(valores).mapToDouble(x -> Math.pow(Math.log(x), 2)).sum();
   }

   /**
    * suma de cuadrados con paralelismo + map + sum
    * @return
    */
   public double sumarFuncionalCuadradosMapParalelo() {
      return 0;
   }

   /**
    * version imperativa de la suma
    * @return
    */
   public double sumarCuadradosImperativo(){
      double suma = 0;
      for(int i=0; i < valores.length; i++){
         suma += Math.log(valores[i]*valores[i]);
      }
      return suma;
   }
}
