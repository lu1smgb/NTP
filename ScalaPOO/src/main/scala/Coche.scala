import java.time.LocalDate

abstract class Coche(val fechaCompra: LocalDate, val automatico: Boolean):
  override def toString: String = s"Fecha: $fechaCompra Automatico: $automatico"
  def color: String // Metodo abstracto
end Coche

class MiniRojo(fecha: LocalDate, automatico: Boolean) extends Coche(LocalDate.now, true):
  def color: String = "Rojo"
end MiniRojo

object PruebaCoche extends App:
  val obj1 = new MiniRojo(LocalDate.now, true)
  println(obj1)
end PruebaCoche
