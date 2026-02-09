package controlador

import enumerados.Movimiento
import modelo.Modelo

import java.lang.Thread.sleep

/**
 * clase para animar el movimiento de la pieza en el tablero
 * @param movimientos
 * @param modelo
 */
class HebraAnimacion(val movimientos : List[Movimiento], val modelo: Modelo,
                     val generarDialogos: (Boolean)=>Unit) extends Runnable :
   private var finalizada = false

   /**
    * metodo de generacion de la simulacion de movimientos
    */
   override def run(): Unit = {
      (0 until movimientos.length).foreach(index =>
         synchronized {
            Acciones.mover(modelo, movimientos(index), generarDialogos)
            this.wait(1000)
         }
      )

      // se pone a true el falg de finalizacion
      finalizada = true
   }

   /**
    * indica si la tarea esta finalizada
    * @return
    */
   def estaFinalizada = finalizada

end HebraAnimacion

