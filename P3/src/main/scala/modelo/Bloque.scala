package modelo


import enumerados.PosicionBloque.*
import enumerados.{Movimiento, PosicionBloque}
import modelo.FuncionPosicionValida

import scala.::

/**
 * tipo para tupla (accion - movimiento)
 */
type Accion = (Bloque, Movimiento)

/**
 * definicion de clase Bloque
 *
 * @param ubicacion posicion de la celda base
 * @param posicionBloque situacion de la pieza: pie, tumbadaXAR, tumbadaXAB,
 *                 tumbadaY, tumbadaYD
 */
case class Bloque(ubicacion : Ubicacion, posicionBloque : PosicionBloque) :
   /**
    * metodo para tratar el movimiento de la pieza
    * @param movimiento
    * @return
    */
   def mover(movimiento: Movimiento) : Bloque =
      // tratamiento del movimiento
      movimiento match
         case Movimiento.IZQUIERDA => moverIzquierda
         case Movimiento.DERECHA => moverDerecha
         case Movimiento.ARRIBA => moverArriba
         case Movimiento.ABAJO => moverAbajo
         case Movimiento.NULO => this
   end mover

   /**
    * metodo para realizar el movimiento de la pieza a
    * la izquierda
    * @return pieza resultante
    */
   def moverIzquierda : Bloque =
      val bloque = posicionBloque match
         case PosicionBloque.PIE =>
            // (x, y)(x,y) -> (x,y-2)(x,y-1)
            // la pieza de referencia es la mas a la izquierda
            Bloque(ubicacion.desplazarY(-2), PosicionBloque.TUMBADAY)

         case PosicionBloque.TUMBADAX =>
            // (x, y)(x+1,y) -> (x, y-1, x+1, y-1)
            // la pieza de referencia es siempre la ubicada
            // mas arriba
            Bloque(ubicacion.desplazarY(-1), PosicionBloque.TUMBADAX)

         case PosicionBloque.TUMBADAY =>
            // (x, y)(x, y+1) -> (x, y-1)(x, y-1)
            Bloque(ubicacion.desplazarY(-1), PosicionBloque.PIE)

      // se devuelve el bloque creado
      bloque
   end moverIzquierda

   /**
    * metodo para realizar el movimiento de la pieza a
    * la derecha
    *
    * @return pieza resultante
    */
   def moverDerecha: Bloque =
      val bloque = posicionBloque match
         case PosicionBloque.PIE =>
            // (x, y)(x, y) -> (x, y+1)(x, y+2)
            // pieza de referencia mas a la izquierda
            Bloque(ubicacion.desplazarY(+1), PosicionBloque.TUMBADAY)

         case PosicionBloque.TUMBADAX =>
            // (x,y)(x+1,y) -> (x,y+1)(x+1,y+1)
            Bloque(ubicacion.desplazarY(+1), PosicionBloque.TUMBADAX)

         case PosicionBloque.TUMBADAY =>
            // (x,y)(x,y+1) -> (x,y+2)(x,y+2)
            Bloque(ubicacion.desplazarY(+2), PosicionBloque.PIE)

      // se devuelve el bloque creado
      bloque
   end moverDerecha

   /**
    * metodo para realizar el movimiento de la pieza
    * hacia arriba
    *
    * @return pieza resultante
    */
   def moverArriba: Bloque =
      val bloque = posicionBloque match
         case PosicionBloque.PIE =>
            // (x,y)(x,y) -> (x-2,y)(x-1,y)
            // la pieza de referencia es la superior
            Bloque(ubicacion.desplazarX(-2), PosicionBloque.TUMBADAX)

         case PosicionBloque.TUMBADAX =>
            // (x,y)(x+1,y) -> (x-1,y)(x-1,y)
            Bloque(ubicacion.desplazarX(-1), PosicionBloque.PIE)

         case PosicionBloque.TUMBADAY =>
            // (x,y)(x,y+1) -> (x-1,y)(x-1,y+1)
            // pieza de referencia mas a la izquierda
            Bloque(ubicacion.desplazarX(-1), PosicionBloque.TUMBADAY)

      // se devuelve el bloque creado
      bloque
   end moverArriba

   /**
    * metodo para realizar el movimiento de la pieza
    * hacia abajo
    *
    * @return pieza resultante
    */
   def moverAbajo: Bloque =
      val bloque = posicionBloque match
         case PosicionBloque.PIE =>
            // (x,y)(x,y) -> (x+1,y)(x+2,y)
            // pieza de referencia: la situada mas arriba
            Bloque(ubicacion.desplazarX(+1), PosicionBloque.TUMBADAX)

         case PosicionBloque.TUMBADAX =>
            // (x,y)(x+1,y) -> (x+2,y)(x+2,y)
            Bloque(ubicacion.desplazarX(+2), PosicionBloque.PIE)

         case PosicionBloque.TUMBADAY =>
            // (x,y)(x,y+1) -> (x+1,y)(x+1,y+1)
            // pieza de referencia: la mas izquierda
            Bloque(ubicacion.desplazarX(+1), PosicionBloque.TUMBADAY)

      // se devuelve el bloque creado
      bloque
   end moverAbajo

   /**
    * comprueba si una pieza esta de pie y ocupa una celda
    * determinada
    * @param celda
    * @return
    */
   def enPieEnPosicion(otra : Ubicacion): Boolean =
      if posicionBloque != PosicionBloque.PIE then false
      else ubicacion.x == otra.x && ubicacion.y == otra.y
   end enPieEnPosicion

   /**
    * se devuelve la posicion del bloque secundario
    * @return
    */
   def calcularPosicionSecundaria : Ubicacion = {
      // la siguiente posicion depende del atributo posicion: siempre
      // se asume que la ubicacion del bloque base tiene el menor valor
      // posible
      posicionBloque match
         case PIE => ubicacion
         case TUMBADAY => Ubicacion(ubicacion.x, ubicacion.y + 1)
         case TUMBADAX => Ubicacion(ubicacion.x + 1, ubicacion.y)
   }
   end calcularPosicionSecundaria

   /**
    * se obtienen los vecinos generados al realizar todos los movimientos
    * posibles, sean o no validos
    * @return
    */
   def obtenerVecinos : List[Accion] = {
      (moverArriba, Movimiento.ARRIBA) :: (moverAbajo, Movimiento.ABAJO) :: 
         (moverIzquierda, Movimiento.IZQUIERDA) :: 
         (moverDerecha, Movimiento.DERECHA) :: List()
   }

   /**
    * metodo de comparacion de piezas
    * @param obj
    * @return
    */
   override def equals(obj: Any): Boolean =
      if !obj.isInstanceOf[Bloque] then false
      else
         val otra = obj.asInstanceOf[Bloque]
         // se comprueba la igualdad de las celdas y de
         // la posicion
         ubicacion == otra.ubicacion && posicionBloque == otra.posicionBloque
   end equals

   /**
    * metodo toString
    * @return
    */
   override def toString: String =
      s"[(${ubicacion.x} - ${ubicacion.y}) - ${posicionBloque}] "
   end toString

   /**
    * funcion auxiliar para determinar si la pieza
    * es valida
     * @param validar
    * @return
    */
   def esValida(validar : FuncionPosicionValida) : Boolean =
      // se comprueba que las celdas que ocupa la pieza
      // sean validas
      val valida = validar(ubicacion)

      // se devuelve el valor de valida
      valida
   end esValida

end Bloque

