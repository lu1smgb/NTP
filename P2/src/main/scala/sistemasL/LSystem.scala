package graficos.sistemasL

import cats.effect.unsafe.implicits.global
import doodle.image.*
import doodle.image.syntax.*
import doodle.image.syntax.all.ImageOps
import doodle.java2d.*
import doodle.syntax.all.AngleDoubleOps
import doodle.syntax.angle.AngleIntOps
import doodle.turtle.*
import doodle.turtle.Instruction.{NoOp, branch, forward, turn}

/**
 * tipo para simplificar la escritura de reglas
 */
type Regla = (Char, String)

/**
 * clase para modelizar sistemas tipo L
 * @param axioma
 * @param reglas
 * @param anguloInicial angulo a girar como primera instruccion
 * @param angulo angulo para operaciones de bifuracion
 * @param medida medida a avanzar
 * @param factor factor a aplicar a medida en cada nivel de bifurcacion
 */
class LSystem (val axioma : String, val reglas : List[Regla], val anguloInicial : Double,
               val angulo : Double, val medida : Double, val factor : Double):

   /**
    * metodo de generacion de las instrucciones por aplicacion de n pasos
    * de aplicacion de reglas
    * @param n
    * @return
    */
   def generar(n: Int): List[Instruction] = 
      // se itera para generar la cadena aplicando la gramatica
      val cadena: String = generarCadena(n)

      // se transforma en instrucciones
      generarInstrucciones(cadena)
   end generar

   /**
    * metodo privado para iterar el sistema un determinado numero
    * de veces
    *
    * @param n
    * @param cadena
    * @return
    */
   private def generarCadena(n: Int, cadena: String = axioma): String = ???
   end generarCadena
   
   /**
    * metodo de generacion V2, mas simple, donde se generan las operaciones
    * directamente en lugar del procesamiento complejo
    *
    * @param codigos
    * @return
    */
   private def generarInstrucciones(cadena : String): List[Instruction] = ???
   end generarInstrucciones

   /**
    * determina si un caracter se corresponde con una operacion simple
    *
    * @param caracter
    * @return
    */
   private def esOperacionSimple(caracter: Char): Boolean =
      if (caracter != '[' && caracter != ']') then true
      else false

   /**
    * metodo para crearInstruccion a partir de un caracter
    *
    * @param caracter
    * @return
    */
   def crearInstruccion(caracter: Char, medida: Double): Instruction =
      caracter match
         case 'F' => forward(medida)
         case 'I' => forward(medida)
         case '+' => turn(angulo.degrees)
         case '-' => turn(-angulo.degrees)
         case _ => NoOp
end LSystem
