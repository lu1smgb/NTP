package imagen;

/**
 * representa puntos en un espacio tridimensional
 */
public class Pixel implements Comparable<Pixel> {
   /**
    * componente roja del color
    */
   private final int rojo;

   /**
    * componente verde del color
    */
   private final int verde;

   /**
    * componente azul del color
    */
   private final int azul;

   /**
    * indice completo del color, componiendo las componentes,
    * incluido el componente alpha no almacenado
    */
   private final int indiceColor;

   /**
    * Constructor de objeto
    *
    * @param rojo  componente de color rojo
    * @param verde componente de color verde
    * @param azul  componente de color azul
    */
   public Pixel(int rojo, int verde, int azul) {
      this.rojo = rojo;
      this.verde = verde;
      this.azul = azul;
      this.indiceColor = RGBA.convertirRGBIndice(rojo, verde, azul);
   }

   /**
    * constructor que permite crear un objeto a partir de un
    * valor de color
    *
    * @param color valor indice del color, que se descompondra
    *              en sus componentes rojo, verde y azul
    */
   public Pixel(int color) {
      this.rojo = RGBA.obtenerComponente(color, ComponentesRGBA.ROJO);
      this.verde = RGBA.obtenerComponente(color, ComponentesRGBA.VERDE);
      this.azul = RGBA.obtenerComponente(color, ComponentesRGBA.AZUL);
      this.indiceColor = color;
   }

   /**
    * obtiene la componente objetivo
    *
    * @param objetivo componente de interes
    * @return valor del componente seleccionado
    */
   public int obtenerComponente(ComponentesRGBA objetivo) {
      int valor = 0;
      switch (objetivo) {
         case ROJO:
            valor = rojo;
            break;
         case VERDE:
            valor = verde;
            break;
         case AZUL:
            valor = azul;
            break;
         case ALFA:
            valor = 0;
            break;
      }

      // se devuelve el valor seleccionado
      return valor;
   }

   /**
    * devuelve el indice de color asociado
    *
    * @return indice del color
    */
   public int obtenerIndiceColor() {
      return indiceColor;
   }

   /**
    * calcula la distancia cuadratica de este punto y
    * el punto pasado como argumento
    *
    * @param otro pixel con el que medir la distancia
    * @return valor de distancia
    */
   public double distanciaCuadratica(Pixel otro) {
      return Math.pow(rojo - otro.rojo, 2) +
         Math.pow(verde - otro.verde, 2) +
         Math.pow(azul - otro.azul, 2);
   }

   /**
    * metodo para mostrar el contenido del objeto por pantalla
    *
    * @return cadena con la informacion del objeto
    */
   public String toString() {
      return " r: " + rojo + " g: " + verde + " b: " + azul + " indice: " + indiceColor;
   }

   /**
    * realiza la comparacion de dos objetos de la clase pixel,
    * para permitir el agrupamiento con las caracteristicas de
    * programacion funcional
    *
    * @param otro the object to be compared.
    * @return resultado de la comparacion
    */
   @Override
   public int compareTo(Pixel otro) {
      return indiceColor - otro.indiceColor;
   }

   /**
    * metodo de comparacion para almacenar los pixels en el mapa
    * @param pixel
    * @return
    */
   @Override
   public boolean equals(Object pixel) {
      boolean resultado = false;
      if(pixel instanceof Pixel) {
         Pixel objetoPixel = (Pixel) pixel;
         resultado = (indiceColor == objetoPixel.indiceColor);
      }

      // se deuelve el valor de resultado
      return resultado;
   }

   /**
    * devuelve como codigo hash el indice del color
    * @return
    */
   public int hashCode() {
      return indiceColor;
   }
}
