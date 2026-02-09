import java.util.Date

abstract class GestorEventos {
  def ejecutar: Unit
}

class Aplicacion {
  var gestor: GestorEventos = null
  def registrarGestor(gestor: GestorEventos): Unit = {
    this.gestor = gestor
  }
  def notificar = gestor.ejecutar
}

class GestorEspecifico extends GestorEventos {
  override def ejecutar() = {
    println(s"Ejecuta gestor especifico ${new Date()}")
  }
}

// COMPAÑERO: CLASE ABSTRACTA Y OBJETO
// AQUI SE PUEDEN GUARDAR METODOS ESTATICOS
// MIENTRAS QUE EN LA ANTERIOR SE PUEDEN GUARDAR METODOS OR.OBJ
object GestorEventos extends App {
  val ap = new Aplicacion
  val g1 = new GestorEspecifico
  ap.registrarGestor(g1)
  ap.registrarGestor(new GestorEventos {
    override def ejecutar: Unit = println("Ejecuta gestor anonimo")
  })
}

