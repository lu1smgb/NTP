package vista


import enumerados.Contenido
import enumerados.Contenido.*
import enumerados.PosicionBloque.*
import modelo.{Bloque, Modelo, Ubicacion}
import vista.Visualizador

import java.awt.{Dimension, *}
import javax.swing.{JButton, JPanel}

/**
 * clase para el visualizador grafico del juego
 * @param modelo
 */
class VisualizadorGrafico(val modelo : Modelo) extends JPanel with Visualizador:

   /**
    * dato miembro para el panel en que ubicar las celdas
    * del circuito. Se crea sobre la marcha
    */
   val panelCircuito : JPanel = new JPanel()

   // se registra el visualizador como observador
   this.modelo.registrar(this)

   // se llama al metodo de visualizacion: inicialmente el bloque
   // esta en posicion valida
   visualizar(true)

   /**
    * da acceso al panel del circuito
    *  @return
    */
   override def obtenerPanel: JPanel =
      panelCircuito

   /**
    * metodo de visualizacion
    */
   override def visualizar(valida : Boolean) =
         // se limpian los componentes del panel
         panelCircuito.removeAll()

         // se fija el layout
         //panelCircuito.setLayout(new GridLayout(modelo.estado.terreno.filas,
         //                  modelo.estado.terreno.columnas, 1, 1))
         val layout = new GridBagLayout
         panelCircuito.setLayout(layout)

         // se obtiene la posicion de los bloques primario y secundario
         val bloque: Bloque = modelo.estado.bloque
         val base: Ubicacion = bloque.ubicacion

         // se determina la posicion para ver la ubicacion del bloque
         // secundario
         val secundaria = bloque.posicionBloque match
            case PIE => Ubicacion(base.x, base.y)
            case TUMBADAX => Ubicacion(base.x + 1, base.y)
            case TUMBADAY => Ubicacion(base.x, base.y + 1)

         // se obtienen las dimensiones del tablero
         val filas = modelo.estado.terreno.filas
         val columnas = modelo.estado.terreno.columnas

         // se compone el contenido de acuerdo al terreno
         val posicionado = new GridBagConstraints()
         (0 until filas).foreach(i => {
            (0 until columnas).foreach(j => {
               val boton = if ((base.x == i && base.y == j) ||
                  (secundaria.x == i && secundaria.y == j)) then
                  crearBotonCelda(PIEZA)
               else
                  crearBotonCelda(modelo.obtenerContenido(i, j))

               // se agrega el boton al panel
               posicionado.gridx = j
               posicionado.gridy = i
               //posicionado.gridwidth = 1
               posicionado.fill = GridBagConstraints.HORIZONTAL;
               // diferencia LINUX - MAC: en MAC la separacion
               // requiere Insets(1, 1, 0, 0). EN linux aparece
               // por defecto y poner los valores 1, 1 hace que
               // la separacion entre los botones sea muy grande
               posicionado.insets = new Insets(0, 0, 0, 0)
               panelCircuito.add(boton, posicionado)
            })
         })
         posicionado.insets = new Insets(0, 0, 0, 0)
         layout.setConstraints(panelCircuito, posicionado)

         // se fija el color de fondo
         val colorFondo = new Color(255, 255, 153)
         panelCircuito.setBackground(colorFondo)

         // se produce el repintado
         panelCircuito.revalidate()
   end visualizar

   /**
    * obtiene la dimension de la celda
    *  @return
    */
   override def obtenerDimension: Dimension =
      val filas = modelo.estado.terreno.filas
      val columnas = modelo.estado.terreno.columnas
      val tamCelda = ImagenesJuego.dimensionCelda.height
      new Dimension(columnas * tamCelda, filas * tamCelda)

   /**
    * metodo para crear el boton para una celda, de acuerdo con
    * el contenido pasado como argumento
    * @param contenido
    * @return
    */
   private def crearBotonCelda(contenido : Contenido) =
      // se crea el boton
      val boton = new JButton()

      // el icono asociado depende del contenido
      contenido match
         case PIEZA => boton.setIcon(ImagenesJuego.imagenPieza)
         case SALIDA => boton.setIcon(ImagenesJuego.imagenSalida)
         case SUELO => boton.setIcon(ImagenesJuego.imagenSuelo)
         case VACIO => boton.setIcon(ImagenesJuego.imagenVacio)
         case LLEGADA => boton.setIcon(ImagenesJuego.imagenLlegada)

      // se asigna el tamaño deseado
      boton.setSize(ImagenesJuego.dimensionCelda)
      boton.setPreferredSize(ImagenesJuego.dimensionCelda)
      boton.setMaximumSize(ImagenesJuego.dimensionCelda)

      // deshabilita la seleccion de estos botones
      boton.setRolloverEnabled(false)
      boton.setContentAreaFilled(false)

      // se devuelve el boton
      boton
   end crearBotonCelda

end VisualizadorGrafico


