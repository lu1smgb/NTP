package modelo


import enumerados.Movimiento
import modelo.{Bloque, Terreno}

/**
 * clase para definir estado del juego
 */
case class Estado(bloque : Bloque, terreno : Terreno) :
   /**
    * comprueba si el estado supone la resolucion
    * del juego
    * @return
    */
   def resuelto : Boolean =
      // se comprueba si la pieza esta en la meta y de pie
      bloque.enPieEnPosicion(terreno.meta)
   end resuelto

   /**
    * metodo de comparacion de estados
    * @param obj
    * @return
    */
   override def equals(obj: Any): Boolean =
      // si no es instancia de Estado, entonces se devuelve
      // falso
      if !obj.isInstanceOf[Estado] then false
      else
         // se recupera la instancia en la referencia otro
         val otro = obj.asInstanceOf[Estado]

         // se comprueba la igualdad de piezas
         bloque == otro.bloque
   end equals
   
   /**
    * ofrece una lista con las piezas que se obtendrian
    * aplicando los movimientos posibles a la pieza actual
    * siempre y cuando la pieza sea valida con respecto al
    * terreno que se indique
    *
    * @return
    */
   def obtenerVecinos : List[Estado] =
      val derecha = Estado(bloque.moverDerecha, terreno)
      val izquierda = Estado(bloque.moverIzquierda, terreno)
      val arriba = Estado(bloque.moverArriba, terreno)
      val abajo = Estado(bloque.moverAbajo, terreno)

      // se genera la lista de estados vecinos
      List(derecha, izquierda, arriba, abajo).
         filter(estado => terreno.esPosicionValida(estado.bloque))
   end obtenerVecinos
   
   /**
    * metodo toString
    *
    * @return
    */
   override def toString: String =
      "[" + bloque.toString + "]"
   end toString   
end Estado
