package ochoReinas

/**
 * objeto para probar el buscador de soluciones
 */
object Principal extends App :
   val buscador = new Buscador(8)
   val solucion = buscador.resolver
   println(solucion)
end Principal

// el resultado al ejecutar deberia ser

// X  0  0  0  0  0  0  0
// 0  0  0  0  X  0  0  0
// 0  0  0  0  0  0  0  X
// 0  0  0  0  0  X  0  0
// 0  0  X  0  0  0  0  0
// 0  0  0  0  0  0  X  0
// 0  X  0  0  0  0  0  0
// 0  0  0  X  0  0  0  0 
