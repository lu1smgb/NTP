package controlador

import buscador.{Buscador, BuscadorFuerzaBruta}
import enumerados.Contenido.*
import enumerados.Movimiento
import enumerados.Movimiento.*
import enumerados.PosicionBloque.*
import modelo.{Bloque, Estado, Modelo, Terreno}
import vista.{ImagenesJuego, Visualizador, VisualizadorGrafico}

import java.awt.{BorderLayout, Color, Dimension, GridBagConstraints, GridBagLayout, Insets}
import java.awt.event.{ActionEvent, ActionListener, KeyEvent, KeyListener}
import javax.swing.*

/**
 * clase para controlar el juego
 */
class JuegoBloque extends JFrame :

   // se da nombre al marco
   setTitle("Juego bloques")

   // se definen los paneles usados en la aplicacion
   var panelPrincipal : JPanel = null
   var panelCircuito : JPanel = null
   var panelControles : JPanel = null
   var panelMovimientos : JPanel = null
   var modelo : Modelo = null
   var visualizador : Visualizador = null
   var botonCarga : JButton = null
   var botonSalir : JButton = null
   var botonEvaluacion : JButton = null
   var botonEjecucion : JButton = null
   var botonIzquierda : JButton = null
   var botonDerecha : JButton = null
   var botonArriba : JButton = null
   var botonAbajo : JButton = null

   // almacen de las soluciones del circuito generadas por busqueda
   var soluciones : LazyList[List[Movimiento]]= null

   // dimension para los botones
   val dimensionBoton = new Dimension(150, 30);

   // creacion del panel principal: el argumento null indica
   // que debe cargarse el circuito por defecto
   crearPanelPrincipal(null)

   /**
    * se agrega el gestor de eventos de teclado
    */
   addKeyListener(new KeyListener(){
      /**
      * metodo de control de pulsacion de tecla
      * @param e
      */
      override def keyPressed(e: KeyEvent): Unit =
         val movimiento = e.getKeyCode match
            case KeyEvent.VK_DOWN => Movimiento.ABAJO
            case KeyEvent.VK_UP => Movimiento.ARRIBA
            case KeyEvent.VK_LEFT => Movimiento.IZQUIERDA
            case KeyEvent.VK_RIGHT => Movimiento.DERECHA

         // se determina la validez del movimiento
         val valido = Acciones.mover(modelo, movimiento, generarDialogosInformativos)
      end keyPressed

      /**
      * metodo de control de liberacion de tecla
      * @param e
      */
      override def keyReleased(e: KeyEvent): Unit = ()

      /**
      * metodo de control de pulsacion
      * @param e
      */
      override def keyTyped(e : KeyEvent) = ()
   })

   /**
    * metodo de creacion del panel principal
    * @return
    */
   def crearPanelPrincipal(nombre : String) =
      // creacion del panel principal
      panelPrincipal = new JPanel
      panelPrincipal.setLayout(new BorderLayout())

      // se inicializan los elementos del juego
      inicializarJuego(nombre)

      // se agrega el panel de circuito al panel principal
      panelPrincipal.add(visualizador.obtenerPanel, BorderLayout.WEST)

      // se crea el panel de controles
      crearPanelControles

      // se dimensiona el tamaño del panel en funcion del
      // panel del circuito
      val dimensionPanelCircuito = visualizador.obtenerDimension
      val dimensionPanelControles = panelControles.getSize
      val altoFinal : Int = dimensionPanelCircuito.height.max(dimensionPanelControles.height)
      val anchoFinal : Int = dimensionPanelCircuito.width + dimensionPanelControles.width

      //val dimension = new Dimension(dimensionPanel.width + 200,
      //   dimensionPanel.height + 50)
      val dimension = new Dimension(anchoFinal, altoFinal + 50)
      panelPrincipal.setSize(dimension)
      panelPrincipal.setPreferredSize(dimension)

      // se agrega al marco
      add(panelPrincipal)
      setSize(dimension)
      setVisible(true)
      setFocusable(true)
      setResizable(false)
   end crearPanelPrincipal

   /**
    * creacion del panel de controles para los modos de funcionamiento
    * @param panelPadre
    */
   private def crearPanelControles =
      // se crea el panel
      panelControles = new JPanel()

      // se asigna el gestor de posicionado
      panelControles.setLayout(new GridBagLayout)

      // se crean los controles para el modo de funcionamiento
      crearControlesJuego(0, 0)

      // se crea el separador superior
      crearSeparador(3, 0, 3)

      // se crean los controles de movimiento
      crearControlesMovimiento(4, 0)

      // se crea el separador
      crearSeparador(7, 0, 3)

      // se crea el control para la salida
      crearControlesSalida(8, 0)

      // se asigna el tamaño preferido
      val dimension = new Dimension(panelMovimientos.getWidth,
         4 * dimensionBoton.height + 90 + panelMovimientos.getHeight)

      //panelControles.setPreferredSize(new Dimension(200, 30))
      //panelControles.setSize(new Dimension(200, 30))
      panelControles.setPreferredSize(dimension)
      panelControles.setSize(dimension)

      // se agrega el panel de controles al principal
      panelPrincipal.add(panelControles, BorderLayout.EAST)
   end crearPanelControles

   /**
    * metodo para creacion de los botones de control del juego
    * @param panel
    * @param fila
    * @param columna
    */
   private def crearControlesJuego(fila : Int, columna : Int) : Unit =
      val posicionado = new GridBagConstraints()
      // creacion del boton para la carga del circuito
      botonCarga = new JButton("Cargar circuito")
      botonCarga.setPreferredSize(dimensionBoton)
      botonCarga.setSize(dimensionBoton)
      botonCarga.setMinimumSize(dimensionBoton)

      // se posiciona en el panel
      posicionado.gridx = columna
      posicionado.gridy = fila
      posicionado.gridwidth = 3
      posicionado.insets = new Insets(2, 5, 5, 2)
      panelControles.add(botonCarga, posicionado)

      // se agrega el gestor de eventos
      botonCarga.addActionListener(new ActionListener {
         override def actionPerformed(e: ActionEvent): Unit =
            val selector = new JFileChooser("./data/circuitos/")
            if (selector.showOpenDialog(JuegoBloque.this) == JFileChooser.APPROVE_OPTION) {
               val seleccion = selector.getSelectedFile().getPath()
               reiniciar(seleccion)
            }
      })

      // se crea el boton para evaluacion
      botonEvaluacion = new JButton("Evaluar")
      botonEvaluacion.setPreferredSize(dimensionBoton)
      botonEvaluacion.setSize(dimensionBoton)
      botonEvaluacion.setMinimumSize(dimensionBoton)

      // se posiciona en el panel
      posicionado.gridx = columna
      posicionado.gridy = fila + 1
      posicionado.gridwidth = 3
      posicionado.insets = new Insets(2, 5, 5, 2)
      panelControles.add(botonEvaluacion, posicionado)

      // se agrega el gestor de eventos
      botonEvaluacion.addActionListener(new ActionListener{
         override def actionPerformed(actionEvent: ActionEvent): Unit =
            evaluarCircuito
      })

      // se crea el boton para ejecutar un plan
      botonEjecucion = new JButton("Ejecutar plan")
      botonEjecucion.setPreferredSize(dimensionBoton)
      botonEjecucion.setSize(dimensionBoton)
      botonEjecucion.setMinimumSize(dimensionBoton)
      botonEjecucion.setEnabled(false)

      // se posiciona en el panel
      posicionado.gridx = columna
      posicionado.gridy = fila + 2
      posicionado.gridwidth = 3
      posicionado.insets = new Insets(2, 5, 5, 2)
      panelControles.add(botonEjecucion, posicionado)

      // se asigna el controlador de eventos
      botonEjecucion.addActionListener(new ActionListener{
         override def actionPerformed(actionEvent: ActionEvent): Unit =
            animarSolucion
      })
   end crearControlesJuego

   /**
    * metodo privado para creacion de separador
    * @param panel
    * @param fila
    * @param columna
    * @param ancho
    */
   private def crearSeparador(fila: Int, columna: Int, ancho: Int) =
      // se crea el separador
      val separador = new JLabel("")

      // para fijar las restricciones de posicionado
      val c = new GridBagConstraints()

      // se posiciona y se ubica en el panel
      separador.setBorder(BorderFactory.createLineBorder(Color.blue, 1))
      c.fill = GridBagConstraints.HORIZONTAL
      c.gridx = columna
      c.gridy = fila
      c.gridwidth = ancho;
      c.insets = new Insets(0, 0, 0, 0);
      panelControles.add(separador, c);
   end crearSeparador

   /**
    * panel de control de movimientos de la pieza
    * @param panel
    * @param fila
    * @param columna
    */
   private def crearControlesMovimiento(fila: Int, columna: Int) =
      // se crea el panel de control de movimiento
      panelMovimientos = new JPanel()

      // se asigna el gestor de posicionado
      panelMovimientos.setLayout(new GridBagLayout)

      // se crean los botones de la primera fila
      crearBotonInsensitivo(panelMovimientos, 2, 2, false)
      botonArriba = crearBotonMover(panelMovimientos, ARRIBA, 2, 3)
      crearBotonInsensitivo(panelMovimientos, 2, 4, false)

      // se crean los botones de la segunda fila
      botonIzquierda = crearBotonMover(panelMovimientos, IZQUIERDA, 3, 2)
      crearBotonInsensitivo(panelMovimientos, 3, 3, true)
      botonDerecha = crearBotonMover(panelMovimientos, DERECHA, 3, 4)

      // se crean los botones de la tercera fila
      crearBotonInsensitivo(panelMovimientos, 4, 2, false)
      botonAbajo = crearBotonMover(panelMovimientos, ABAJO, 4, 3)
      crearBotonInsensitivo(panelMovimientos, 4, 4, false)

      // se fija el dimensionado del panel
      val dimension = new Dimension(ImagenesJuego.dimensionCelda.width * 5,
         ImagenesJuego.dimensionCelda.height * 5)
      panelMovimientos.setSize(dimension)
      panelMovimientos.setPreferredSize(dimension)
      panelMovimientos.setMaximumSize(dimension)

      // se fija el color de fondo
      val colorFondo = new Color(220, 220, 220)

      // se posiciona el panel
      val posicionado = new GridBagConstraints()
      posicionado.gridx = columna
      posicionado.gridy = fila
      posicionado.insets = new Insets(5, 5, 5, 5)
      panelMovimientos.setBackground(colorFondo)

      // se agrega al panel de controles
      panelControles.add(panelMovimientos, posicionado)
   end crearControlesMovimiento

   /**
    * metodo de creacion de boton para los movimientos
    * @param panel
    * @param movimiento
    * @param fila
    * @param columna
    */
   def crearBotonMover(panel: JPanel, movimiento: Movimiento, fila: Int, columna: Int) : JButton =
      // creacion del boton
      val boton = new JButton()

      // se elige la imagen asociada
      val imagen =
         movimiento match
            case ARRIBA => ImagenesJuego.imagenArriba
            case ABAJO => ImagenesJuego.imagenAbajo
            case IZQUIERDA => ImagenesJuego.imagenIzquierda
            case DERECHA => ImagenesJuego.imagenDerecha

      // asignacion del icono
      boton.setIcon(imagen)

      // se fijan las dimensiones
      boton.setSize(ImagenesJuego.dimensionCelda)
      boton.setPreferredSize(ImagenesJuego.dimensionCelda)
      boton.setMaximumSize(ImagenesJuego.dimensionCelda)

      // se agrega el gestor de eventos
      boton.addActionListener(new ActionListener:
         override def actionPerformed(e: ActionEvent): Unit =
            // se determina la validez del movimiento
            val valido = Acciones.mover(modelo, movimiento, generarDialogosInformativos)
            // println("movimiento valido: " + valido)
         end actionPerformed
      )

      // se fija en el panel
      val posicionado = new GridBagConstraints()
      //posicionado.fill = GridBagConstraints.VERTICAL
      posicionado.gridx = columna
      posicionado.gridy = fila
      posicionado.gridwidth = 1
      panel.add(boton, posicionado)

      // se devuelve el boton
      boton
   end crearBotonMover

   /**
    * creacion de boton no sensitivo de relleno para los controles de
    * movimiento de la pieza
    * @param panel
    * @param fila
    * @param columna
    * @param centro
    */
   private def crearBotonInsensitivo(panel : JPanel, fila : Int, columna : Int, centro : Boolean) =
      // se crea el boton
      val boton = new JButton()

      // se crea objeto para gestionar posicionado
      val posicionado = new GridBagConstraints()

      // el icono depende de si esta en el centro o es vacio
      if(centro){
         boton.setIcon(ImagenesJuego.imagenCentro)
      }
      else{
         boton.setIcon(ImagenesJuego.imagenParada)
         boton.setVisible(false)
      }

      // se fijan las dimension
      val dimensionNueva = new Dimension(ImagenesJuego.dimensionCelda.width + 5,
         ImagenesJuego.dimensionCelda.height + 5)
      boton.setSize(dimensionNueva)
      boton.setPreferredSize(dimensionNueva)

      // se deshabilita
      boton.setEnabled(false)

      // se fijan las restricciones de posicionado
      posicionado.fill = GridBagConstraints.VERTICAL
      posicionado.gridx = columna
      posicionado.gridy = fila
      posicionado.gridwidth = 1

      // se agrega al panel
      panel.add(boton, posicionado)
   end crearBotonInsensitivo

   /**
    * metodo de inicializacion de los elementos del juego
    * @param inicial
    * @param nombreArchivo
    * @return
    */
   private def inicializarJuego(nombreArchivo : String) : Unit =
      // se asigna valor a nombre en funcion del parametro inicial
      val nombre = if(nombreArchivo == null){
         "./data/circuitos/circuito1.map"
      }
      else{
         nombreArchivo
      }

      // se crea el terreno
      val terreno = Terreno.crear(nombre)

      // se obtiene la ubicacion de la celda de salida
      val celdaInicio = Terreno.buscarCaracter(SALIDA, terreno.contenido)

      // se crea el bloque para la salida
      val bloque = new Bloque(celdaInicio, PIE)

      // se crea el estado inicial
      val estadoInicial = new Estado(bloque, terreno)

      // se crea el modelo
      modelo = Modelo(estadoInicial)

      // se crea el visualizador
      visualizador = VisualizadorGrafico(modelo)
   end inicializarJuego

   /**
    * creacion de los controles para la salida del programa
    * @param panel
    * @param fila
    * @param columna
    */
   private def crearControlesSalida(fila: Int, columna: Int) =
      // se crea el boton para salida
      botonSalir = new JButton("Salir")

      // se fija su tamaño
      botonSalir.setPreferredSize(dimensionBoton)
      botonSalir.setSize(dimensionBoton)
      botonSalir.setMinimumSize(dimensionBoton)

      // se agrega el gestor de eventos
      botonSalir.addActionListener(new ActionListener:
         override def actionPerformed(e: ActionEvent): Unit =
            System.exit(0)
      )

      // se controla su posicionado
      val posicionado = new GridBagConstraints()
      posicionado.gridx = columna
      posicionado.gridy = fila
      posicionado.gridwidth = 3
      posicionado.insets = new Insets(5, 5, 5, 5)
      panelControles.add(botonSalir, posicionado)
   end crearControlesSalida

   /**
    * metodo de reinicio del panel de juego
    * @param nombre
    */
   private def reiniciar(nombre : String) : Unit =
      // el panel principal actual se haec invisible
      panelPrincipal.removeAll()
      panelPrincipal.setVisible(false)

      // se elimina todos los elementos del panel de circuito
      visualizador.obtenerPanel.removeAll()

      // se repinta
      repaint()

      // se llama al metodo que crea los controles
      crearPanelPrincipal(nombre)

      // se repinta de nuevo
      repaint()
   end reiniciar

   /**
    * metodo de generacion de dialogos formativos
    * @param valido
    */
   private def generarDialogosInformativos(valido : Boolean) =
      // se trata el caso de movimiento no valido
      if (!valido) {
         val opciones: Array[Object] = Array("Sí", "No")
         val opcionDefecto = opciones(1)
         val respuesta = JOptionPane.showOptionDialog(
            JuegoBloque.this,
            "Movimiento no valido: ¿desea abandonar el juego?",
            "Question",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opcionDefecto
         )

         // si la respuesta es si, se abandona el juego
         if (respuesta == 0) {
            System.exit(0)
         }
         else{
            // se reinicia el juego
            reiniciar(null)
         }
      }
      else {
         // se comprueba si el bloque esta en la posicion de destino
         if(modelo.estado.resuelto){
            val opciones: Array[Object] = Array("Sí", "No")
            val opcionDefecto = opciones(1)
            val respuesta = JOptionPane.showOptionDialog(
               JuegoBloque.this,
               "Alcanzada la celda objetivo, ¿abandonar juego?",
               "Question",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE,
               null,
               opciones,
               opcionDefecto
            )

            // si la respuesta es si, se abandona el juego
            if (respuesta == 0) {
               System.exit(0)
            }
            else{
               // se reinicia el juego
               reiniciar(null)

               // se activan los botones de movimiento
               activarBotones
            }
         }
      }
   end generarDialogosInformativos

   /**
    * metodo para evaluar el circuito mediante un procedimiento de busqueda
     */
   private def evaluarCircuito =
      // se crea el buscador
      val buscador : Buscador = new BuscadorFuerzaBruta(modelo)

      // se llama al metodo de busqueda
      soluciones = buscador.buscar()

      // se muestra por pantalla la primera de las soluciones
      val solucion = soluciones.head.reverse

      // se hace que el boton de ejecucion de plan sea sensitivo
      botonEjecucion.setEnabled(true)
   end evaluarCircuito

   /**
    * metodo para animar la ejecucion de la solucion obtenida
    */
   private def animarSolucion =
      // se desactivan los botones de movimiento
      desactivarBotones

      // se van ejecutando las ordenes de la solucion
      val animador = new HebraAnimacion(soluciones.head.reverse, modelo, generarDialogosInformativos)
      val hebra = new Thread(animador)
      hebra.start()

      // al final estamos en la meta
      generarDialogosInformativos(true)
   end animarSolucion

   /**
    * se desactivan los botones de movimiento
    */
   def desactivarBotones : Unit =
      botonArriba.setEnabled(false)
      botonAbajo.setEnabled(false)
      botonDerecha.setEnabled(false)
      botonIzquierda.setEnabled(false)
      botonCarga.setEnabled(false)
      botonEvaluacion.setEnabled(false)
      botonSalir.setEnabled(false)
   end desactivarBotones

   /**
    * se activan los botones de movimiento
    */
   def activarBotones : Unit =
      botonArriba.setEnabled(true)
      botonAbajo.setEnabled(true)
      botonDerecha.setEnabled(true)
      botonIzquierda.setEnabled(true)
      botonCarga.setEnabled(true)
      botonEvaluacion.setEnabled(true)
      botonSalir.setEnabled(true)
   end activarBotones

end JuegoBloque
