package sumatorio

import scala.annotation.tailrec

class SumatorioGeneral extends App {
  @tailrec
  final def potencias2(x: Int, acum: Int = 1) : Int =
    if x == 0 then acum else potencias2(x-1, 2*acum)

  @tailrec
  final def sumatorioEnteros(inf: Int, sup: Int, acum: Int = 0) : Int =
    if inf > sup then acum
    else sumatorioEnteros(inf+1, sup, acum + inf)

  @tailrec
  final def sumatorioCuadrados(inf: Int, sup: Int, acum: Int = 0) : Int =
    if inf > sup then acum
    else sumatorioCuadrados(inf+1, sup, acum + (inf*inf))

  final def sumatorioPotencias2(inf: Int, sup: Int, acum: Int = 0): Int =
    if inf > sup then acum
    else sumatorioCuadrados(inf + 1, sup, acum + potencias2(inf))

  def sumatorio(inf : Int, sup : Int, acum : Int = 0)(f : Int => Int) : Int =
    if inf > sup then acum
    else sumatorio(inf + 1, sup, acum + f(inf))(f)

  val sumatorio1_100 = sumatorio(1, 100)
  val sum1_100 = sumatorio1_100(x => x)
  val sum2_1_100 = sumatorio1_100(x => x*x)
  val sumpot2_1_100 = sumatorio1_100(x => potencias2(x))

}
