package graficos.sistemasL

import cats.effect.unsafe.implicits.global
import doodle.image.*
import doodle.image.syntax.*
import doodle.image.syntax.all.ImageOps
import doodle.java2d.*
import doodle.syntax.angle.AngleIntOps
import doodle.turtle.*
import doodle.turtle.Instruction.*

object BigH extends App:
   // se define el axioma
   val axioma = "[F]--[F]"
   val reglas = List(('F', "I[+F][-F]"))

   // se crea sistema
   val sistema = LSystem(axioma, reglas, 90, 90, 200, 0.65)

   // se genera el resultado de iterar 1 vez
   val instrucciones = sistema.generar(10)

   // se visualiza el resultado
   val imagen = Turtle.draw(instrucciones)
   imagen.scale(1.5, 1.5).draw()
end BigH
