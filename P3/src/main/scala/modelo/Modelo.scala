package modelo


import vista.Visualizador

import scala.collection.mutable.ArrayBuffer

/**
 * clase para representar el modelo del juego: se crea unicamente
 * para no modificar nada en la clase Estado y no agregar ahi los
 * observadores
 * @param estado
 */
case class Modelo(var estado : Estado) :
   /**
    * array para almacenar los observadores
    */
   val observadores : ArrayBuffer[Visualizador] = ArrayBuffer[Visualizador]()

   /**
    * metodo de registro de un nuevo observador
    * @param observador
    * @return
    */
   def registrar(observador : Visualizador) =
      observadores += observador
   end registrar

   /**
    * metodo de notificacion de los observadores
    */
   def notificar = observadores.foreach(
      observador => {
         val valido = estado.terreno.esPosicionValida(estado.bloque)
         observador.visualizar(valido)
      })

   /**
    * metodo para obtener el contenido de una posicion
    * determinada
    * @param fila
    * @param columna
    */
   def obtenerContenido(fila : Int, columna: Int) =
      estado.terreno.contenido(fila)(columna)
   end obtenerContenido

   /**
    * se produce el movimiento arriba del bloque
     */
   def moverArriba: Unit =
      estado = Estado(estado.bloque.moverArriba, estado.terreno)
      notificar
   end moverArriba

   /**
    * se produce el movimiento abajo del bloque
    * @return
    */
   def moverAbajo =
      estado = Estado(estado.bloque.moverAbajo, estado.terreno)
      notificar
   end moverAbajo

   /**
    * se produce el movimiento a la izquierda
    * @return
    */
   def moverIzquierda =
      estado = Estado(estado.bloque.moverIzquierda, estado.terreno)
      notificar
   end moverIzquierda

   /**
    * se produce el movimiento a la derecha
    * @return
    */
   def moverDerecha =
      estado = Estado(estado.bloque.moverDerecha, estado.terreno)
      notificar
   end moverDerecha
   
end Modelo