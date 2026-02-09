package busqueda

import scala.annotation.tailrec

object BusquedaRecursiva:

   /**
    * Metodo de busqueda binaria
    *
    * @param coleccion
    * @param aBuscar
    * @param esMayor
    * @tparam A
    * @return
    */
   def busquedaBinaria[A](coleccion: List[A], aBuscar: A)
                         (esMayor: (A, A) => Boolean): Int =
      @tailrec
      def go(izquierda: Int, derecha: Int): Int =
         // caso base: se cruzan los marcadores a derecha
         // e izquierda
         if izquierda > derecha then -1
         else
            // caso inductivo: se busca el nuevo intervalo
            // en que se hara la busqueda
            val centro = (izquierda + derecha) / 2

            // se obtiene el valor central
            val vcentral = coleccion(centro)

            // se determina la relacion de orden entre el valor
            // centrar el el valor a buscar. Si el valor central
            // es mayor, toca buscar a la izquierda. Si es menor,
            // hay que buscar a la derecha
            if (esMayor(vcentral, aBuscar)) then
               go(izquierda, centro - 1)
            else
               // puede darse la igualdad o que sea menor. Si no
               // se cumple que es mayor cambiando los valores de
               // sitio, hay igualdad
               if (!esMayor(aBuscar, vcentral)) then
                  // se trata del valor buscado
                  centro
               else
                  // se busca a la derecha
                  go(centro + 1, derecha)

      // inicio del proceso
      go(0, coleccion.size)
   end busquedaBinaria


   /**
    * Busqueda secuencial generica: metodo auxiliar para la busqueda a saltos
    *
    * @param coleccion
    * @param aBuscar
    * @param esMayor
    * @tparam A
    * @return
    */
   private def busquedaLineal[A](coleccion: List[A], aBuscar: A)
                                (esMayor: (A, A) => Boolean): Int = ???
      
   /**
    * Metodo de busqueda a saltos
    *
    * @param coleccion
    * @param aBuscar
    * @param esMayor
    * @tparam A
    * @return
    */
   def busquedaSaltos[A](coleccion: List[A], aBuscar: A)
                        (esMayor: (A, A) => Boolean): Int = ???


   /**
    * se genera el menor numero de Finonacci mayor que el
    * valor pasado como argumento y se devuelve tanto dicho
    * numero como los dos anteriores. Metodo auxiliar para la
    * busqueda mediante fibonacci
    *
    * @param n
    */
   private def generarFibonacci(n: Int): (Int, Int, Int) = {
      @tailrec
      def generarTupla(fk: Int, fk1: Int): (Int, Int, Int) = {
         val fk2 = fk + fk1
         if (fk2 > n) (fk, fk1, fk2)
         else generarTupla(fk1, fk2)
      }
      generarTupla(0, 1)
   }
   
   /**
    * metodo de busqueda guiada por la serie de Fibonacci
    *
    * @param coleccion
    * @param aBuscar
    * @param esMayor
    * @tparam A
    * @return
    */
   def busquedaFibonacci[A](coleccion: List[A], aBuscar: A)
                           (esMayor: (A, A) => Boolean): Int = {

      val lv = coleccion.length

      @tailrec
      def busquedaBloque(f0: Int, f1: Int, inicio: Int): Int = {
         val indice = Math.min(inicio+f0, lv-1)
         val lvmenos1 = lv - 1
         if (f0 == 0 || f1 == 0) {
            // Es el ultimo elemento el valor buscado? Si no, no se ha encontrado el elemento
            if !esMayor(aBuscar, coleccion(lvmenos1)) && !esMayor(coleccion(lvmenos1), aBuscar) then lvmenos1
            else -1
         }
         else if !esMayor(aBuscar, coleccion(indice)) && !esMayor(coleccion(indice), aBuscar) then {
            // Se ha encontrado el elemento
            indice
         }
         else if esMayor(aBuscar, coleccion(indice)) then {
            // Busqueda en bloque inferior
            busquedaBloque(f1 - f0, f0, indice)
         }
         else {
            // Busqueda en bloque superior
            busquedaBloque(f0 - (f1 - f0), f1 - f0, inicio)
         }
      }

      val f: (Int, Int, Int) = generarFibonacci(lv)
      println(f._1 + " " + f._2 + " " + f._3)
      busquedaBloque(f._1, f._2, -1)
   }

end BusquedaRecursiva

/**
 * objeto para pruebas sencillas
 */
object Prueba extends App{
   val lista = List(12, 17, 20, 25, 30, 35, 40, 41, 45, 47, 48, 52, 53, 55, 59, 60, 64, 66, 67,
      72, 73, 78, 81, 82, 83, 95) // Deberia ser 7
   val lista2 = List(0)
   val lista3 = List(16, 24, 33, 41, 43, 44, 48, 49, 50, 51, 52, 62, 64, 65, 67, 70, 79, 84, 89, 95, 98) // Deberia ser 3
   val resultado = BusquedaRecursiva.busquedaFibonacci(lista, 41)((x,y) => x > y)
   println(resultado)
}