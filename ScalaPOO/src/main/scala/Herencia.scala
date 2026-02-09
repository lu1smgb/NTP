class A:
  override def toString: String = "Clase A"
  def saludo: String = "Saludos desde A: " + getClass.getName
end A

class B extends A

class C extends B:
  override def toString: String = "Clase C <- " + super.toString // super -> A
  override def saludo: String = "Saludos desde C: " + getClass.getName + " <- " + super.saludo
end C

class CocheH(val marca: String, var enUso: Boolean):
  def reservar(flag: Boolean) =
    enUso = flag
  end reservar
end CocheH

class Renault(val color: String, enUso : Boolean) extends CocheH("Renault", enUso)

object PruebaHerencia extends App {
  val objA = new A
  val objB = new B
  val objC = new C
  println(objA.saludo)
  println(objB.saludo)
  println(objC.saludo)

  val lista = List(objA, objB, objC)
  val saluda = lista.map(obj => obj.saludo)
}
