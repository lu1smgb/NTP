class Usuario {
  val nombre = "Admin"
  val prompt = s">$nombre"

  override def toString: String = s"Usuario($nombre) Prompt($prompt)"

  // El cuerpo de la clase es un constructor
  println("Creado nuevo usuario")
}

class Usuario2(nombreUsuario: String):
  val nombre = nombreUsuario
  val prompt = s">$nombre"
end Usuario2

class Usuario3(val nombre: String):
  val prompt = s">$nombre"
end Usuario3

class Usuario4(val nombre: String):
  def this() =
    this("admin") // Valor por defecto
  end this
end Usuario4

object PruebaUsuario extends App:
  val obj1 = new Usuario
  println(obj1)

  val esAnyRef = obj1.isInstanceOf[AnyRef]
  println("Es AnyRef: " + esAnyRef)

  val obj2 = new Usuario2("Luis")
  val obj3 = Usuario2("Miguel") // Se puede prescindir de 'new'

  val lista = List(Usuario3("Jose"), Usuario3("Manuel"))

  val obj4 = Usuario4()
  println(obj4.nombre)
end PruebaUsuario
