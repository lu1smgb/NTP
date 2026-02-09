package ordenacion

import scala.annotation.tailrec

class BusquedaRecursiva extends App {

  def busquedaBinaria[A](coleccion: Array[A], objetivo: A)(criterio: (A, A) => Boolean) = {

    @tailrec
    def go(izquierda: Int, derecha: Int): Int =
      if izquierda > derecha then -1
      else
        val centro = (izquierda + derecha) / 2
        val pivote = coleccion(centro)
        if !(criterio(pivote, objetivo) && criterio(objetivo, pivote)) then centro
        else if criterio(pivote, objetivo) then go(centro + 1, derecha)
        else go(izquierda, centro - 1)

    go(0, coleccion.length-1)

  }

  def busquedaSecuencial[A](coleccion: Array[A], objetivo: A)(criterio: (A, A) => Boolean) = {

    @tailrec
    def go(indice: Int): Int =
      if indice > coleccion.length-1 then -1
      else
        val resultado = !criterio(coleccion(indice), objetivo) && !criterio(objetivo, coleccion(indice))
        if resultado then indice
        else go(indice+1)

    go(0)

  }

}
