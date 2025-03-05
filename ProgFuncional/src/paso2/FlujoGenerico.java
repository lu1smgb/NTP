package paso2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlujoGenerico {
   /**
    * dato miembro para manejar datos de enteros
    */
   private List<Integer> enteros;

   /**
    * dato miembro para manejar datos de tipo cadena
    */
   private List<String> cadenas;

   /**
    * constructor de la clase para dotar de contenido
    * a las colecciones almacenadas en los atributos
    * @param numeroEnteros numero de datos en la coleccion
    */
   public FlujoGenerico(int numeroEnteros){
      Random generador = new Random();
      generador.setSeed(1);

      // se agregan enteros a la lista: valores entre
      // -100 y 100
      //// for(int i=0; i < numeroEnteros; i++){
      ////    enteros.add(generador.nextInt() % 101);
      //// }
      enteros = IntStream.range(0, 10).boxed().map(x -> generador.nextInt() % 101).toList();

      cadenas = new ArrayList<>();
      cadenas.add("Rojo");
      cadenas.add("Naranja");
      cadenas.add("Amarillo");
      cadenas.add("Verde");
      cadenas.add("azul");
      cadenas.add("indigo");
      cadenas.add("Violeta");
      cadenas.add("magenta");
   }

   private void listarImperativo() {
      for (Integer num: this.enteros) {
         System.out.println(num);
      }
      for (String s: this.cadenas) {
         System.out.println(s);
      }
   }

   // * Flujos
   private void listarFuncional() {

      // Convertimos la lista en flujo
      Stream<Integer> flujoEnteros = this.enteros.stream();
      // Definimos las expresiones
      Consumer<Integer> listar = x -> System.out.println(x);

      // Procesamos el flujo (operacion terminal, el flujo se vacia)
      flujoEnteros.forEach(listar);
   }

   // Version unitaria 1 de listarFuncional
   private void listarFuncionalUnitario() {
      enteros.forEach(x -> System.out.println(x));
      // this.enteros.forEach(System.out::println);
   }

   private int sumarFuncional() {

      // sum() se aplica a IntStream, conversion realizada por un mapToInt que no hace nada
      return enteros.stream()
         .mapToInt(x -> x).sum();

   }

   private int sumarFuncionalReduce() {

      // Es una especie de iterador
      // 'x' es el acumulador e 'y' es el valor a agregar
      // El primer argumento de reduce() es el resultado por defecto
      // Este resultado cambiara con el valor de retorno de la expresion lambda
      return enteros.stream()
         .reduce(0, (x, y) -> x+y);

   }

   private long sumarCuadradosReduce() {
      return enteros.stream()
         .reduce(0, (x, y) -> x+y*y);
   }

   private long sumarCuadradosMap() {
      return enteros.stream()
         .mapToLong(x -> x*x)
         .sum();
   }

   private long obtenerMaximo() {
      return enteros.stream()
         .reduce(Integer.MIN_VALUE, (x,y) -> {
            int max = x;
            if (max < y) max = y;
            return max;
         });
   }

   public List<Double> divisionPorMil() {
      // return enteros.stream().map(x -> x/1000.0).collect(Collectors.toList());
      //! toList solo esta disponible en map (clase Stream<>)
      return enteros.stream()
         .map(x -> x/1000.0)
         .toList();
   }

   public List<Integer> filtrarPares() {
      return enteros.stream()
         .filter(x -> x % 2 == 0)
         .toList();
   }

   public List<Integer> filtrarParesNoRepetidos() {
      // 'distinct' elimina elementos repetidos
      return enteros.stream()
         .filter(x -> x % 2 == 0)
         .distinct()
         .toList();
   }

   public List<Double> procesarRango() {
      // Lista de valores Integer
      IntStream intRange = IntStream.rangeClosed(0, 100);
      // Con 'boxed' puedo transformar un stream dedicado a uno generico
      Stream<Integer> boxed = intRange.boxed();
      Stream<Double> doubleRange = boxed.map(x -> x*0.1);
      return doubleRange.toList();
   }

   public void conversionArrays() {
      int array[] = {1,2,3,4,5};
      IntStream s1 = Arrays.stream(array);
      IntStream s2 = IntStream.of(array);
   }

   public List<Integer> paresUnicosOrdenados() {
      return enteros.stream()
         .distinct()
         .filter(x -> x%2==0)
         .sorted()
         .toList();
   }

   public List<String> pasarMayusculas() {
      return cadenas.stream()
         .map(x -> x.toUpperCase())
         .toList();
   }

   public void listarCadenas() {
      cadenas.forEach(System.out::println);
   }

   public List<String> filtrarYOrdenarCadenas() {
      return cadenas.stream()
         .filter(x -> x.compareToIgnoreCase("m") > 0)
         .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
         .toList();
   }

   /**
    * metodo main para pruebas
    * @param args
    */
   public static void main(String args[]){
      // se crea objeto FlujoGenerico: jugaremos con una coleccion de
      // 100 enteros
      FlujoGenerico flujo = new FlujoGenerico(10);
      flujo.listarFuncionalUnitario();
      System.out.println("Suma: " + flujo.sumarFuncional());
      System.out.println("Maximo: " + flujo.obtenerMaximo());
      flujo.enteros = flujo.paresUnicosOrdenados();
      flujo.listarFuncionalUnitario();
      // flujo.cadenas = flujo.pasarMayusculas();
      // flujo.listarCadenas();
      flujo.cadenas = flujo.filtrarYOrdenarCadenas();
      flujo.listarCadenas();

   }
}
