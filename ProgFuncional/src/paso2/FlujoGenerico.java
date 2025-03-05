package paso2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
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
      generador.setSeed(0);

      // se crean los contenedores de objetos
      enteros = new ArrayList<>();
      cadenas = new ArrayList<>();

      // se agregan enteros a la lista: valores entre
      // -100 y 100
      for(int i=0; i < numeroEnteros; i++){
         enteros.add(generador.nextInt() % 101);
      }

      // se agregan ahora algunas cadenas
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
      this.enteros.forEach(x -> System.out.println(x));
      // this.enteros.forEach(System.out::println);
   }

   private int sumarFuncional() {

      // sum() se aplica a IntStream, conversion realizada por un mapToInt que no hace nada
      return this.enteros.stream().mapToInt(x -> x).sum();

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

   }
}
