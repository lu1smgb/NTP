package controlador

import enumerados.Movimiento
import enumerados.Movimiento.*
import modelo.Modelo

/**
 * definicion de las acciones posibles de la pieza
 */
object Acciones :

   /**
    * metodo para mover la pieza aplicando una de las acciones posibles
    * @param modelo
    * @param movimiento
    * @param generarDialogo
    */
   def mover(modelo : Modelo, movimiento : Movimiento, generarDialogo : (Boolean) => Unit) =
      movimiento match
         case ARRIBA => moverArriba(modelo)
         case ABAJO => moverAbajo(modelo)
         case IZQUIERDA => moverIzquierda(modelo)
         case DERECHA => moverDerecha(modelo)

      // se comprueba si el resultado lleva a posicion no valida
      // y se devuelve su valor
      val valido = modelo.estado.terreno.esPosicionValida(modelo.estado.bloque)
      generarDialogo(valido)
   end mover

   /**
    * metodo de movimiento arriba
    * @param modelo
    */
   def moverArriba(modelo : Modelo) =
      modelo.moverArriba
   end moverArriba

   /**
    * metodo de movimiento abajo
    * @param modelo
    */
   def moverAbajo(modelo: Modelo) =
      modelo.moverAbajo
   end moverAbajo

   /**
    * metodo de movimiento izquierda
    * @param modelo
    */
   def moverIzquierda(modelo : Modelo) =
      modelo.moverIzquierda
   end moverIzquierda

   /**
    * metodo de movimiento a derecha
    * @param modelo
    */
   def moverDerecha(modelo: Modelo) =
      modelo.moverDerecha
   end moverDerecha

end Acciones
