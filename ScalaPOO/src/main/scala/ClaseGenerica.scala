class ClaseGenerica[A](lista: List[A]) extends Iterable[A]:
  override def iterator: Iterator[A] = lista.iterator

  // Dos maneras diferentes de implementarlo
  // Con sus ventajas y desventajas
  def tam1: Int = lista.length
  val tam2: Int = lista.length

end ClaseGenerica

object PruebaGenerica extends App:
  val lista1 = ClaseGenerica(List("Hola", "Adios", "Mundo"))
  val lista2 = ClaseGenerica[Int](List(1,2,3,4,5))
end PruebaGenerica