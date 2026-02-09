import ordenacion.BusquedaRecursiva
import org.scalacheck.Gen.{choose, listOfN}
import org.scalacheck.Prop.{AnyOperators, forAll, propBoolean}
import org.scalacheck.{Gen, Properties}

class BusquedaTest extends Properties("Busqueda Recursiva") {
  val genList : Gen[List[Int]] = listOfN(1000, choose[Int](0, 5000))
  property("busqueda binaria") =
    forAll(genList) {
      xs => (xs.size > 10) ==> {
        val serie = xs.distinct.sorted.toArray
        val objetivo = Gen.choose(20, 100).sample.getOrElse(0)
        val indiceScala = serie.indexOf(objetivo)
        val indiceBB = BusquedaRecursiva().busquedaBinaria(serie, objetivo)((x: Int, y: Int) => x < y)
        indiceScala ?= indiceBB
      }
    }
}
