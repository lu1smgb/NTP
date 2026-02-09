package modelo

/**
 * clase case Ubicacion para codificar las posiciones en
 * el tablero de la pieza base del bloque
 * coordenada x: posicion en eje vertical (filas)
 * coordenada y: posicion en eje horizontal (columnas)
 * se comienza por el 0
 */
final case class Ubicacion(x: Int, y: Int) :
   /**
    * cambio de posicion en el eje X, desplazando d
    * posiciones
    * @param d
    * @return
    */
   def desplazarX(d: Int) = Ubicacion(x + d, y)

   /**
    * cambio de posicion en el eje Y, desplazando d
    * @param d
    * @return
    */
   def desplazarY(d: Int) = Ubicacion(x, y + d)

   /**
    * comparacion de objetos
    * @param obj
    * @return
    */
   override def equals(obj: Any): Boolean =
      if !obj.isInstanceOf[Ubicacion] then false
      else
         val otra = obj.asInstanceOf[Ubicacion]
         x == otra.x && y == otra.y
   end equals
   
end Ubicacion