package graficos.sistemasL

import cats.effect.unsafe.implicits.global
import doodle.image.*
import doodle.image.syntax.*
import doodle.image.syntax.all.ImageOps
import doodle.java2d.*
import doodle.syntax.angle.AngleIntOps
import doodle.turtle.*
import doodle.turtle.Instruction.*
import doodle.core.*

object EjemploGuion extends App:
   // se define el axioma
   val axioma = "F"
   val reglas = List(('F', "F+F--F+F"))

   // se crea sistema
   val sistema = LSystem(axioma, reglas, 0, 60, 100, 0.5)

   // se genera el resultado de iterar 1 vez
   val instrucciones = sistema.generar(3)

   // se visualiza el resultado
   val imagen = Turtle.draw(instrucciones)
   imagen.scale(0.5, 0.5).strokeColor(Color.royalBlue).strokeWidth(5).draw()
end EjemploGuion
