package modelo


import enumerados.Contenido

import scala.io.Source

/**
 * definicion del tipo de la funcion que se usa
 * para determinar si una posicion esta en el tablero
 */
type FuncionPosicionValida = Ubicacion => Boolean

/**
 * trait para definir los elementos basicos de un juego:
 * salida, meta, funcion para el terreno y bloque inicial
 * posicionado en la salida y en vertical
 */
case class Terreno(filas : Int, columnas : Int, salida : Ubicacion,
                   meta : Ubicacion, contenido : Array[Array[Contenido]]) :
   /**
    * metodo para comprobar si una posicion es valida
    * @param pos
    * @return
    */
   def esPosicionValida(bloque: Bloque): Boolean =
      // se obtiene la ubicacion de las dos piezas
      val base = bloque.ubicacion
      val segunda = bloque.calcularPosicionSecundaria

      // para acceder hace falta que las coordenadas de ambas
      // ubicaciones sean validas y que no tengan como contenido
      // el vacio
      if (base.x >= 0 && base.y >= 0 && segunda.x >= 0 && segunda.y >= 0 &&
         base.x < filas && base.y < columnas && segunda.x < filas &&
         segunda.y < columnas) {
         // se comprueba si el contenido de la posicion es distinto
         // de -
         contenido(base.x)(base.y) != Contenido.VACIO &&
         contenido(segunda.x)(segunda.y) != Contenido.VACIO
      }
      else
         false
   end esPosicionValida
   
end Terreno

/**
 * objeto compañero para creacion de objetos
 */
object Terreno:
   /**
    * metodo para crear el terreno del juego
    * @param nombreArchivo
    * @return
    */
   def crear(nombreArchivo : String) : Terreno =
      val cadenas: List[String] = leerArchivo(nombreArchivo)
      val contenido: Array[Array[Contenido]] = vectorTerreno(cadenas)
      val salida: Ubicacion = buscarCaracter(Contenido.SALIDA, contenido)
      val meta: Ubicacion = buscarCaracter(Contenido.LLEGADA, contenido)

      // se crea y devuelve el objeto de clase Terreno
      Terreno(contenido.size, contenido(0).size, salida, meta, contenido)
   end crear

   /**
    * dato miembro para almacenar el contenido del terreno. Se parte de la
    * cadena terreno, se particiona para separar las lineas y posteriormente
    * los caracteres de cada linea- La notacion str: _* hace que el constructor
    * del vector considere cada elemento como un argumento separado y no de
    * forma conjunta. De esta forma, tenemos un vector para cada linea y su
    * contenido esta formado por caracteres y no por cadenas
    */
   def vectorTerreno(cadenas : List[String]): Array[Array[Contenido]] =
      val res = cadenas.map(cadena => cadena.map(caracter => {
         val contenido : Contenido = caracter match {
            case 'S' => Contenido.SALIDA
            case 'T' => Contenido.LLEGADA
            case '-' => Contenido.VACIO
            case 'o' => Contenido.SUELO
         }
         contenido
      }).toArray).toArray
      res
   end vectorTerreno

   /**
    * funcion que devuelve la posicion del caracter buscado (pasado como
    * primer argumento) en el terreno representado por el vector pasado
    * como segundo argumento. Se puede asumir que el caracter buscado
    * solo aparece una vez en la descripcion del terreno.
    * Ayuda: pueden usarse las funciones indexWhere e indexOf de la
    * clase Vector
    */
   def buscarCaracter(c: Contenido, contenido: Array[Array[Contenido]]): Ubicacion = {
      // se busca en el vector con la definicion del terreno el caracter
      // pasado como argumento
      val x = contenido.indexWhere(_.indexOf(c) != -1)
      val y = contenido(x).indexOf(c)

      // compone posicion con estas coordenadas, que se devuelve como
      // resultado de su procesamiento
      Ubicacion(x, y)
   }

   /**
    * obtiene el contenido de una posicion determinada
    * @param x
    * @param y
    * @param contenido
    * @return
    */
   def obtenerContenido(x : Int, y : Int, contenido : Array[Array[Contenido]]) : Contenido =
      contenido(x)(y)
   
   /**
    * metodo privado para leer el contenido del archivo
    *
    * @param nombreArchivo
    * @return
    */
   def leerArchivo(nombreArchivo: String): List[String] =
      Source.fromFile(nombreArchivo).getLines.toList
end Terreno 