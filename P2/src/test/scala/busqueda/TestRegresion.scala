package busqueda

import org.scalameter.{Bench, Key, Persistor, Warmer, config}
import org.scalameter.api.{Context, Gen}

object TestRegresion extends Bench.OfflineRegressionReport :

   // se define la configuracion estandar de las pruebas
   override def defaultConfig = Context(
      Key.exec.maxWarmupRuns := 5,
      Key.exec.benchRuns := 20,
      Key.verbose := true
   )

   // se define el numero de busquedas a realizar en cada coleccion
   val numeroAccesos = 50

   // se definen los tamaños de las colecciones a considerar: desde 500
   // hasta 5000, con saltos de 500: 1000, 1500, 2000, ...., 5000
   val tams = Gen.range("size")(1000, 5000, 500)

   // se generan las colecciones y el conjunto de indices a buscar: se
   // produce una tupla con la coleccion y los indides sobre los que
   // se hara la busqueda. colecciones es un generador de tupas de la
   // forma:
   // - lista sobre la que se buscara
   // - conjunto de indices de los valores a buscar
   val colecciones: Gen[(List[Int], List[Int])] = for {tam <- tams}
      yield (
         Seq.fill(tam)(util.Random.nextInt(2*tam)).sorted.toList,
         (0 until numeroAccesos).map(_ => util.Random.nextInt(100)).toList
      )

   // se define la funcion mayor para poder realizar la busqueda
   val mayor = (x: Int, y: Int) => x > y

   /**
    * prueba usando el metodo de busqueda binaria: puede implementarse
    * para comparar con los metodos de busqueda a saltos
    */
   performance of "Busquedas en secuencias - fibonacci" in {
      // generacion de la curva para busqueda binaria
      using(colecciones) curve "binaria" in {
         xs => {
            for (indice <- xs._2) {
               val res = BusquedaRecursiva.busquedaBinaria[Int](xs._1, indice)(mayor)
            }
         }
      }

//      // generacion de la curva para busqueda a saltos
//      using(colecciones) curve "saltos" in {
//         xs => {
//            for (indice <- xs._2)
//               BusquedaRecursiva.busquedaSaltos(xs._1, indice)(mayor)
//         }
//      }

      // generacion de la curva para busqueda Fibonacci
      using(colecciones) curve "fibonacci" in {
         xs => {
            for (indice <- xs._2)
               BusquedaRecursiva.busquedaFibonacci(xs._1, indice)(mayor)
         }
      }
   }
end TestRegresion


