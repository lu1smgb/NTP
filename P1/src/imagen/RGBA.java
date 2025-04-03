package imagen;

/**
 * clase para aportar funciones de conversion entre
 * definiciones de colores, componentes, etc. Se trata de
 * funciones estaticas que se agrupan en una clase
 */
public interface RGBA {

   /**
    * obtiene una componente a partir de un valor de color
    *
    * @param color    color del que obtener el componente
    * @param objetivo componente objetivo
    * @return valor de la compoente seleccionada
    */
   static int obtenerComponente(int color, ComponentesRGBA objetivo) {
      int valor = 0;
      switch (objetivo) {
         case ROJO:
            valor = obtenerRojo(color);
            break;
         case VERDE:
            valor = obtenerVerde(color);
            break;
         case AZUL:
            valor = obtenerAzul(color);
            break;
         case ALFA:
            valor = obtenerAlfa(color);
            break;
      }

      // se devuelve el valor
      return valor;
   }

   /**
    * metodo de mezcla a partir de las componentes rojo, verde
    * y azul, asumiendo valor 1 para alfa
    *
    * @param rojo  componente rojo
    * @param verde componente verde
    * @param azul  componente azul
    * @return indice de color calculado
    */
   static int convertirRGBIndice(int rojo, int verde, int azul) {
      // se limitan todas las componentes y se componen
      // sus valores para generar el valor rgb completo
      int calfa = limitar(256, 0, 255) << 24;
      int crojo = limitar(rojo, 0, 255) << 16;
      int cverde = limitar(verde, 0, 255) << 8;
      int cazul = limitar(azul, 0, 255) << 0;

      // se devuelve la composicion
      return calfa | crojo | cverde | cazul;
   }

   /**
    * dado un valor de color devuelve su componente alpha
    *
    * @param color color objetivo
    * @return valor de la componente alpha
    */
   private static int obtenerAlfa(int color) {
      return ((0xff000000 & color) >>> 24);
   }

   /**
    * a partir de un color devuelve el valor de la
    * componente roja
    *
    * @param color color objetivo
    * @return valor de la componente roja
    */
   private static int obtenerRojo(int color) {
      return ((0x00ff0000 & color) >>> 16);
   }


   /**
    * a partir de un color devuelve el valor de la
    * componente verde
    *
    * @param color color objetivo
    * @return valor de la componente roja
    */
   private static int obtenerVerde(int color) {
      return ((0x0000ff00 & color) >>> 8);
   }

   /**
    * a partir de un color devuelve el valor de la
    * componente azul
    *
    * @param color color del que extraer el valor del componente
    * @return valor extraido
    */
   static int obtenerAzul(int color) {
      return ((0x0000ff & color) >>> 0);
   }

   /**
    * limita los valores para asegurar estan en el rango indicado
    *
    * @param valor  valor a limitar
    * @param minimo minimo del intervalo considerado
    * @param maximo maximo del intervalo considerado
    * @return valor limitado y ajustado al intervalo
    */
   private static int limitar(int valor, int minimo, int maximo) {
      int salida = valor;
      if (valor < minimo) {
         salida = minimo;
      } else {
         if (valor > maximo) {
            salida = maximo;
         }
      }

      // se devuelve el valor de salida
      return salida;
   }
}
