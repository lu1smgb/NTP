package graficos.sistemasL

import cats.effect.unsafe.implicits.global
import doodle.image.*
import doodle.image.syntax.*
import doodle.image.syntax.all.ImageOps
import doodle.java2d.*
import doodle.syntax.angle.AngleIntOps
import doodle.turtle.*
import doodle.turtle.Instruction.*

object Planta2 extends App:
   // se define el axioma
   val axioma = "F"
   val reglas = List(('F', "FF-[-F+F+F]+[+F-F-F]"))

   // se crea sistema
   val sistema = LSystem(axioma, reglas, 90, 22.5, 20, 1.0)

   // se genera el resultado de iterar 1 vez
   val instrucciones = sistema.generar(4)

   // se visualiza el resultado
   val imagen = Turtle.draw(instrucciones)
   imagen.scale(0.5, 0.5).draw()
end Planta2
