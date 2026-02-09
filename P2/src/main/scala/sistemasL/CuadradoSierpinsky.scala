package graficos.sistemasL

import doodle.turtle.Turtle
import cats.effect.unsafe.implicits.global
import doodle.image.*
import doodle.image.syntax.*
import doodle.image.syntax.all.ImageOps
import doodle.java2d.*
import doodle.syntax.angle.AngleIntOps
import doodle.turtle.*
import doodle.turtle.Instruction.*

object CuadradoSierpinsky extends App:
   // se define el axioma
   val axioma = "F-F-F-F"
   val reglas = List(('F', "FF[-F-F-F]F"))

   // se crea sistema
   val sistema = LSystem(axioma, reglas, 90, 90, 20, 1)

   // se genera el resultado de iterar 1 vez
   val instrucciones = sistema.generar(4)

   // se visualiza el resultado
   val imagen = Turtle.draw(instrucciones)
   imagen.scale(0.5, 0.5).draw()
end CuadradoSierpinsky