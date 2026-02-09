package buscador

import enumerados.Movimiento
import enumerados.PosicionBloque.PIE
import modelo.{Bloque, Modelo, Ubicacion}

/**
 * tipo para representar la situacion actual del bloque junto con los
 * movimientos que se realizaron prebiamente
 */
type Secuencia = (Bloque, List[Movimiento])

/**
 * tipo para representar las secuencias consideradas
 */
type Historial = LazyList[Secuencia]

/**
 * clase para buscar la solucion al laberinto mediante fuerza bruta
 */
class BuscadorFuerzaBruta(modelo : Modelo) extends Buscador(modelo) :

   /**
    * metodo principal de busqueda
    *
    * @return
    */
   override def buscar(): LazyList[List[Movimiento]] =
      // crea bloque inicial desde la salida
      val celdaSalida: Ubicacion = modelo.estado.terreno.salida
      val bloqueInicial: Bloque = Bloque(celdaSalida, PIE)
      val celdaMeta = modelo.estado.terreno.meta
      val bloqueMeta = Bloque(celdaMeta, PIE)
      val inicio = LazyList((bloqueInicial, List()))

      // se crea un conjunto con los bloques usados durante la
      // secuencia, inicialmente vacio y se llama a la funcion
      // de generacion con una lista compuesta unicamente por
      // la tupla del bloque inicial y 0 movimientos
      val secuenciasSolucion = generar(inicio, Set())

      // se ordenan por longitud de secuencia y se devuelven ordenadas
      val soluciones = secuenciasSolucion.filter(secuencia => secuencia._1 == bloqueMeta).sortBy(_._2.length).map(_._2)
      println(s"Se han encontrado ${soluciones.length} soluciones")
      soluciones.foreach(solucion => println(solucion))
      soluciones
   end buscar

   /**
    * metodo de generacion de secuencias
    * @param secuencias Secuencias a tener en cuenta para la generacion de nuevas secuencias
    * @param explorados Posiciones del mapa ya exploradas
    * @return
    * Soluciones (tomadas a mano):
    * IZQ ARRIBA DER ABAJO IZQ ABAJO ABAJO ABAJO ABAJO IZQ IZQ ABAJO ABAJO ABAJO DER DER DER ABAJO IZQ
    * ABAJO IZQ ABAJO ABAJO ABAJO IZQ ABAJO DER ARRIBA IZQ IZQ ABAJO ABAJO ABAJO DER DER ABAJO ABAJO DER ARRIBA IZQ
    */
   def generar(secuencias : Historial , explorados : Set[Bloque]): Historial = {

      if secuencias.isEmpty then {
         println("Ya no hay mas secuencias para evaluar")
         return secuencias
      }

      val secuenciasNuevas = secuencias.flatMap(secuencia => soloNuevos(obtenerVecinos(secuencia), explorados)).to(LazyList)
      val exploradosNuevos = secuenciasNuevas.map(secuencia => secuencia._1).to(Set)
      val exploradosSiguiente = explorados.union(exploradosNuevos)

      println(s"Secuencias consideradas: ${secuenciasNuevas.length} ; Nodos explorados: ${exploradosSiguiente.toList.length}")
      secuencias#:::generar(secuenciasNuevas, exploradosSiguiente)

   }
   end generar

   /**
    * genera las secuencias aplicando al bloque de la secuencia todos los
    * movimientos posibles
    * @param secuencia
    * @return
    */
   def obtenerVecinos(secuencia : Secuencia) : Historial =

      val bloqueSecuencia: Bloque = secuencia._1
      val planSecuencia: List[Movimiento] = secuencia._2

      val vecinos = bloqueSecuencia.obtenerVecinos
      val secuenciasVecinos = vecinos.map(vecino => {
         val bloqueVecino : Bloque = vecino._1
         val movimientoVecino : Movimiento = vecino._2
         val planVecino : List[Movimiento] = movimientoVecino::planSecuencia
         (bloqueVecino, planVecino)
      }).to(LazyList)
      secuenciasVecinos

   end obtenerVecinos

   /**
    * filtra las tuplas de bloques y listas de movimiento para quedarse
    * con aquellas en que el bloque no haya sido ya visitado
    * Tambien es necesario que filtre las posiciones invalidas
    * @param secuencias
    * @param explorados
    * @return
    */
   def soloNuevos(secuencias : Historial, explorados : Set[Bloque]): Historial =

      secuencias.filter(secuencia => {
         val bloqueSecuencia = secuencia._1
         !explorados.contains(bloqueSecuencia) && modelo.estado.terreno.esPosicionValida(bloqueSecuencia)
      })

   end soloNuevos
   
end BuscadorFuerzaBruta

