package cambios

import scala.annotation.tailrec
import scala.language.postfixOps

/**
 * clase para representar cambios
 *
 * @param aDevolver indica la cantidad total a devolver
 * @param cambio   mapa con (clave: tipo de moneda ,
 *                 valor: numero de monedas de ese tipo)
 */
case class Cambio(aDevolver: Int, cambio: Map[Int, Int]) :
   /**
    * calcula el total representado por el cambio
    * @return
    */
   def totalCambio = cambio.map(entry => entry._1 * entry._2).sum

   /**
    * metodo que calcula la cantidad restante por devolver
    *
    * @return
    */
   def restante = aDevolver - totalCambio

   /**
    * se agrega una unidad mas a la moneda cuyo valor
    * se pasa como argumento. Se devuelve un nuevo objeto
    *
    * @param moneda
    */
   def agregarMoneda(moneda: Int): Cambio =
      if (cambio.contains(moneda)) then
         new Cambio(aDevolver, cambio + (moneda -> (cambio(moneda) + 1)))
      else
         new Cambio(aDevolver, cambio + (moneda -> 1))
   end agregarMoneda

   /**
    * metodo toString
    *
    * @return
    */
   override def toString: String =
      "Cambio(cantidad: " + aDevolver + " " +
         " cambio: " + cambio.mkString(" | ") + "  total: " + totalCambio + ")"
   end toString

end Cambio

/**
 * objeto para implementar los metodos de contador de cambios
 */
object ContadorCambios :
   /**
    * version inicial del contador
    *
    * @param cantidad
    * @param monedas
    * @return
    */
   def listarCambiosPosibles(cantidad: Int, monedas: List[Int]): Int =
      @tailrec
      def cambiosPendientes(pendientes: List[(Int, List[Int])], acumulador: Int): Int = {
         pendientes match {
            case Nil => acumulador
            case (0, _) :: resto => cambiosPendientes(resto, acumulador + 1) // No queda cantidad por devolver
            case (n, Nil) :: resto => cambiosPendientes(resto, acumulador) // No quedan monedas
            case (n, m :: ms) :: resto =>
               if (n < 0) {
                  cambiosPendientes(resto, acumulador) // Nos pasamos de la cantidad restante
               }
               else {
                  // Dos posibilidades: se usa o no se usa
                  cambiosPendientes((n-m, m::ms) :: (n, ms) :: resto, acumulador)
               }
         }
      }
      cambiosPendientes(List((cantidad, monedas)), 0)
   end listarCambiosPosibles

   /**
    * version mejorada para devolver los cambios posibles
    * @param cantidad
    * @param monedas
    * @return
    */
   def listarCambiosPosiblesV2(cantidad : Int, monedas : List[Int]) : Set[Cambio] = ???
end ContadorCambios

/**
 * objeto para pruebas simples sobre contador de cambios
 */
object Prueba extends App :
   val cantidad = 12
   val monedas = List(10, 5, 2, 1)
   val c1 = ContadorCambios.listarCambiosPosibles(cantidad, monedas)
   println("contador de cambios: " + c1)

   val cambios = ContadorCambios.listarCambiosPosiblesV2(cantidad, monedas)
   println("cambios detectados: " + cambios.size)
   println(cambios.mkString("\n"))
end Prueba
