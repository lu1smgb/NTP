package buscador

import enumerados.Movimiento
import modelo.Modelo

trait Buscador(val modelo : Modelo) :
   def buscar() : LazyList[List[Movimiento]]
end Buscador
   
