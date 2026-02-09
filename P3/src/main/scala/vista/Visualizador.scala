package vista


import javax.swing.JPanel
import java.awt.Dimension

trait Visualizador:
   /**
    * obtiene el panel usado para la visualizacion
    * @return
    */
   def obtenerPanel : JPanel
   
   /**
    * metodo para visualizar el contenido del modelo
    */
   def visualizar(valida : Boolean) : Unit
   
   /**
    * metodo para obtener la dimension del visualizador
    * @return
    */
   def obtenerDimension : Dimension
   
end Visualizador   
