package vista


import java.awt.Dimension
import javax.swing.ImageIcon

/**
 * objeto para almacenar las imagenes del juego
 */
object ImagenesJuego :

   /**
    * imagen para la posicion de salida (azul)
    */
   lazy val imagenSalida: ImageIcon = new ImageIcon("./data/celdas/salida.png");

   /**
    * imagen para la posicion de llegada (verde)
    */
   lazy val imagenLlegada: ImageIcon = new ImageIcon("./data/celdas/llegada.png");

   /**
    * imagen para las celdas con bloques (morado)
    */
   lazy val imagenPieza: ImageIcon = new ImageIcon("./data/celdas/pieza.png");

   /**
    * imagen para las posiciones con suelo (gris)
    */
   lazy val imagenSuelo: ImageIcon = new ImageIcon("./data/celdas/suelo.png");

   /**
    * imagen para las posiciones vacias (negro)
    */
   lazy val imagenVacio: ImageIcon = new ImageIcon("./data/celdas/vacio.png");

   /**
    * imagen para el control de movimiento a izqda
    */
   lazy val imagenIzquierda: ImageIcon = new ImageIcon("./data/controles/izquierda.png");

   /**
    * imagen para el control de movimiento a derecha
    */
   lazy val imagenDerecha: ImageIcon = new ImageIcon("./data/controles/derecha.png");

   /**
    * imagen para el control de mopvimiento arriba
    */
   lazy val imagenArriba: ImageIcon = new ImageIcon("./data/controles/arriba.png");

   /**
    * imagen para el control de movimiento abajo
    */
   lazy val imagenAbajo: ImageIcon = new ImageIcon("./data/controles/abajo.png");

   /**
    * imagen para el boton del centro: insensitivo
    */
   lazy val imagenCentro: ImageIcon = new ImageIcon("./data/controles/centro.png");

   /**
    * imagen para los botones de los bordes, de
    * relleno, insensitivos
    */
   lazy val imagenParada: ImageIcon = new ImageIcon("./data/controles/parada.png");

   /**
    * dimension de las imagenes: siempre es el mismo
    * para todas ellas
    */
   lazy val dimensionCelda: Dimension =
         new Dimension(imagenSalida.getIconWidth, imagenSalida.getIconHeight);

end ImagenesJuego

