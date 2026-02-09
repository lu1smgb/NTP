package busqueda

import org.scalacheck.Gen.{choose, listOfN, listOf}
import org.scalacheck.Prop.{?=, AnyOperators, forAll, propBoolean}
import org.scalacheck.{Gen, Properties}

/**
 * objeto para comprobar el funcionamiento de la busqueda de Fibonacci
 */
object BusquedaCheck extends Properties("Busqueda recursiva") :
   // generacion aleatoria de listas
   val genList: Gen[List[Int]] = listOfN(1000, choose[Int](0, 5000))

   // se define una propiedad busqueda de Fibinacci
   property("comprobacion de busqueda de fibonacci") =
      forAll(genList) {
         xs => (xs.size > 10) ==> {
            val serie = xs.distinct.sorted
            // Elegimos de la lista el valor a buscar
            val valorABuscar = Gen.choose(20, 100).sample.getOrElse(0)
            //println("valor a buscar: " + valorABuscar)

            // se usa la busqueda ofrecida por Scala
            val index = serie.indexOf(valorABuscar)

            val resultado = BusquedaRecursiva.busquedaFibonacci(serie, valorABuscar)
                ((x: Int, y: Int) => x > y)
            //println("indice ok: " + index + " resultado: " + resultado)
            index ?= resultado
         }
      }

   // se define una propiedad para busqueda a saltos
//   property("comprobacion de busqueda a saltos") =
//      forAll(genList) {
//         xs =>
//            (xs.size > 10) ==> {
//               val serie = xs.distinct.sorted
//               // Elegimos de la lista el valor a buscar
//               val valorABuscar = Gen.choose(0, 100).sample.getOrElse(0)
//
//               // se usa la busqueda ofrecida por Scala
//               val index = serie.indexOf(valorABuscar)
//
//               val resultado = BusquedaRecursiva.busquedaSaltos(serie, valorABuscar)
//                  ((x: Int, y: Int) => x > y)
//               index ?= resultado
//            }
//      }

   // propiedad para busqueda binaria
   property("comprobacion de busqueda binaria") =
      forAll(genList) {
         xs =>
            (xs.size > 10) ==> {
               val serie = xs.distinct.sorted
               // Elegimos de la lista el valor a buscar
               val valorABuscar = Gen.choose(0, 100).sample.getOrElse(0)

               // se usa la busqueda ofrecida por Scala
               val index = serie.indexOf(valorABuscar)

               val resultado =
                  BusquedaRecursiva.busquedaBinaria(serie, valorABuscar)((x: Int, y: Int) => x > y)
               index ?= resultado
            }
      }

end BusquedaCheck

