package graficos.sistemasL

import cats.effect.unsafe.implicits.global
import doodle.image.*
import doodle.image.syntax.*
import doodle.image.syntax.all.ImageOps
import doodle.java2d.*
import doodle.syntax.angle.AngleIntOps
import doodle.turtle.Instruction.*
import doodle.turtle.*

object CurvaTriangularKoch extends App:
   // se define el axioma
   val axioma = "F"
   val reglas = List(('F', "F+F--F+F"))

   // se crea sistema
   val sistema = LSystem(axioma, reglas, 0, 60, 10, 0.65)

   // se genera el resultado de iterar 1 vez
   val instrucciones = sistema.generar(5)

   // se visualiza el resultado
   val imagen = Turtle.draw(instrucciones)
   imagen.scale(0.5, 0.5).draw()
end CurvaTriangularKoch
