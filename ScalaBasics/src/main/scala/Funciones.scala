object Funciones extends App {

  def saludar : Unit = println("Hola!")
  saludar

  def multiplicar(x: Double, y: Double) = x*y
  val res = multiplicar(34.7, 93.2)
  println("multiplicar: " + res)
  
}
