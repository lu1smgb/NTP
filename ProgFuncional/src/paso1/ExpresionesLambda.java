package paso1;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;

/**
 * clase para aprender la funcionalidad relacionada con las
 * expresiones lambda
 */
public class ExpresionesLambda {
   /**
    * metodo main para pruebas
    * @param args
    */
   public static void main(String args[]){
      
      // * Operadores
      IntBinaryOperator op1 = (int x, int y) -> { return x * y; };
      int resultado = op1.applyAsInt(2, 3);
      System.out.println("Resultado 1: " + resultado);
      IntBinaryOperator op2 = new IntBinaryOperator() {
         @Override
         public int applyAsInt(int left, int right) {
            return left * right;
         }
      };
      resultado = op2.applyAsInt(2, 3);
      System.out.println("Resultado 2: " + resultado);
      // cambiamos funcionalidad de segunda forma
      op2 = (int x, int y) -> { return x*x*y*y; };
      resultado = op2.applyAsInt(2, 3);
      System.out.println("Resultado 3: " + resultado);

      // * Funciones
      BiFunction<Double, Double, Double> f1 = (Double x, Double y) -> { return x / y; };
      Double resultadoDouble = f1.apply(7.d, 2.d);
      System.out.println("Resultado 4: " + resultadoDouble);

      // * Consumidor
      Consumer<Double> consumidor = (Double x) -> { System.err.println("Consumiendo: " + x); };
      consumidor.accept(resultadoDouble);

      // * Runnables (sin argumentos)
      Runnable errorMsg = () -> {System.err.println("ERROR: (bromita)");};
      errorMsg.run();

      // * Simplificacion
      BiFunction<Double, Double, Double> simplificado = (Double x, Double y) -> Math.pow(x,y);
   }
}
