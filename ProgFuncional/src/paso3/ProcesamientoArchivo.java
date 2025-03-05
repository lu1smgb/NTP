package paso3;

import java.util.List;

/**
 * clase para mostrar ejemplos relacionados con el procesamiento
 * de los datos almacenados en un archivo
 */
public class ProcesamientoArchivo {
   /**
    * permite almacenar el contenido de datos de un archivo
    */
   private List<String> contenido;

   /**
    * constructor de la clase
    * @param nombre
    */
   public ProcesamientoArchivo(String nombre){
      // se almacena en contenido todo lo leido del archivo
      contenido = leerArchivo(nombre);
   }

   /**
    * por implementar
    * @param nombre
    * @return
    */
   private List<String> leerArchivo(String nombre){
      return null;
   }

   /**
    * metodo main para pruebas
    * @param args
    */
   public static void main(String args[]){
      // se crea objeto de la clase
      ProcesamientoArchivo objeto = new ProcesamientoArchivo("./data/fuenteovejuna.txt");
   }
}
