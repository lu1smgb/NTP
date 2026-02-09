package ochoReinas

import scala.annotation.tailrec
import scala.language.postfixOps

/**
 * clase para buscar la solucion al problema de ubicacion
 * de las reinas en el tablero con la dimension indicada
 * @param dimension
 */
class Buscador (val dimension : Int) {
   /**
    * metodo de resolucion: devuelve un tablero con las reinas
    * ubicadas en las posiciones indicadas
    * NOTA: observar los metodos disponibles en las clases Tablero
    * y Conflicto
    *
    * @return
    */

   private var contadorSoluciones = 0

   def resolver: Tablero = {

      def generarNivelTableros(t: Tablero, fila: Int): List[Tablero] = {
         if (fila >= dimension) {
           List[Tablero](t)
         }
         else {
           (0 until dimension).map(columna => Celda(fila, columna))
             .filter(celda => !Conflicto.conflictoCeldaTablero(celda, t))
             .flatMap { celda =>
               generarNivelTableros(t.agregarReina(celda.fila, celda.columna), fila + 1)
             }.toList
         }
      }

      val r = generarNivelTableros(Tablero(dimension, List[Celda]()), 0)
      if (r.nonEmpty) r.head
      else throw new Exception(s"No hay soluciones para el problema de ${dimension} reinas")

   }
}