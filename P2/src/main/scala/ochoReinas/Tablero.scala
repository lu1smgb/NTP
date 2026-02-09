package ochoReinas

import scala.annotation.tailrec

/**
 * clase para representar celdas del tablero
 *
 * @param fila
 * @param columna
 */
case class Celda(fila : Int, columna : Int)

/**
 * objeto con metodos para determinar conflicto entre celdas
 */
object Conflicto :

   /**
    * comprueba si hay conflicto entre dos celdas
    * @param celda1
    * @param celda2
    * @return
    */
   private def conflictoCeldas(celda1 : Celda, celda2 : Celda) : Boolean =
      celda1.fila == celda2.fila ||
         celda1.columna == celda2.columna ||
         Math.abs(celda1.fila - celda2.fila) ==
            Math.abs(celda1.columna - celda2.columna)

   /**
    * determina si se genera conflicto al agregar una nueva reina
    * en la celda indicada
    * @param celda
    * @param tablero
    * @return
    */
   def conflictoCeldaTablero(celda : Celda, tablero : Tablero) : Boolean =
      tablero.contenido.exists(celdaTablero => conflictoCeldas(celda, celdaTablero))
end Conflicto

/**
 * clase para representar el tablero
 * @param dimension numero de filas y columnas
 * @param contenido contenido del tablero, solo de las
 *                  celdas ocupadas
 */
class Tablero(val dimension : Int, val contenido : List[Celda]) :

   /**
    * se agrega nueva reina al tablero y se genera un tablero
    * nuevo
    * @param fila
    * @param columna
    * @return
    */
   def agregarReina(fila : Int, columna : Int) =
      new Tablero(dimension, Celda(fila, columna) :: contenido)

   /**
    * metodo to string
    * @return
    */
   override def toString : String =
      (0 until dimension).map(fila =>
         // se obtienen las celdas asoaciadas a la fila
         toStringFila(contenido.filter(celda => celda.fila == fila))
      ).mkString("\n")

   /**
    * muestra el contenido de una fila
    * @param celdas
    * @return
    */
   private def toStringFila(celdas: List[Celda]) : String =
      (0 until dimension).map(columna => {
         celdas.find(_.columna == columna) match
            case None => " 0 "
            case _ => " X "
      }).mkString
end Tablero
