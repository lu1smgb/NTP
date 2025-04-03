package visualizador;

import almacenpixels.TipoAlmacen;
import convergencia.EstrategiaConvergencia;
import convergencia.TipoConvergencia;
import imagen.Imagen;
import imagen.Pixel;
import kmedias.KMedias;
import seleccion.TipoSeleccion;
import utilidades.Utilidades;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * clase para proporcionar la interfaz de la aplicacion
 * de filtrado de imagenes
 */
public class AplicacionFiltrado extends JFrame{
    /**
     * ancho predeterminado del panel
     */
    private final static int anchoPanel = 420;

    /**
     * alto predeterminado del panel
     */
    private final static int altoPanel = 520;

    /**
     * alto predeterminado de la ventana de texto
     */
    private final static int altoTexto = 370;

    /**
     * ubicacion de la imagen en el panel
     */
    private CanvasImagen canvas = null;

    /**
     * almacena el tipo de almacen a usar, por defecto la lista
     * de colores
     */
    private TipoAlmacen tipoDefecto = TipoAlmacen.LISTAPIXELS;

    /**
     * botones para el control de almacen de datos
     */
    private JRadioButton listaColores = null;

    /**
     * boton de seleccion de inicializacion aleatoria
     */
    private JRadioButton muestreoAleatorio = null;

    /**
     * boton de seleccion de inicializacion mediante seleccion
     * uniforme en el rango de colores
     */
    private JRadioButton seleccionMasFrecuente = null;

    /**
     * boton de seleccion del criterio de parada por
     * numero de iteraciones
     */
    private JRadioButton iteraciones = null;

    /**
     * boton de seleccion del criterio de parada por
     * estabilidad
     */
    private JRadioButton estabilidad = null;

    /**
     * boton de aplicacion de las iteraciones del algoritmo
     */
    private JButton aplicarPaso = null;

    /**
     * campo para indicar el numero de colores a seleccionar
     */
    private JSpinner numeroColores = null;

    /**
     * campo para indicar el maximo numero permitido de iteraciones
     */
    private JSpinner maxIteraciones = null;

    /**
     * campo para indicar el umbral de estabilidad
     */
    private JSpinner umbralEstabilidad = null;

    /**
     * campo de texto para mostrar informacion sobre la aplicacion
     * del ruido
     */
    private JTextArea info = null;

    /**
     * objeto que realiza el agrupamiento
     */
    private KMedias kmedias;

    /**
     * constructor del marco de aplicacion
     */
    public AplicacionFiltrado() {
        // se asigna el título
        setTitle("Aplicación de K-means para filtrado de colores");

        // se asigna operacion de cierre por defecto
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // se asigna el layout
        setLayout(new BorderLayout());

        // se crea panel a la derecha para botones de seleccion
        // de opciones ejecucion, botones de ejecucion (panel de
        // controles) y texto con informacion del proceso
        JPanel panelDerecho = crearPanelOpciones();

        // se agrega al marco
        add(panelDerecho, BorderLayout.EAST);

        // se crean la opciones del menu
        JMenuBar barra = crearMenuOpciones();

        // se agrega la barra
        setJMenuBar(barra);

        // se crea canvas
        canvas = new CanvasImagen(tipoDefecto);

        // se informa de las propiedades de la imagen
        informarTexto();

        // se integra en el panel general mediante un panel con
        // barra deslizadora
        JScrollPane panelBarra = new JScrollPane(canvas);

        // se agrega al panel general, en el centro
        add(panelBarra, BorderLayout.CENTER);

        // se produce el dimensionado del marco
        dimensionar();

        // se hace visible
        setVisible(true);

        // se inicializa kmedias a null
        kmedias = null;
    }

    /**
     * metodo de creacion del panel de opciones. Consta de un panel
     * al que se adjuntan dos paneles adicionales: uno con los
     * controles de seleccion de acciones y otro con la ventana
     * en que se muestra la informacion asociada
     * @return panel con controles para acciones
     */
    private JPanel crearPanelOpciones() {
        // se crea panel a la derecha para botones de
        // opciones
        JPanel panelDerecho = new JPanel();

        // se asigna el borde
        panelDerecho.setBorder(BorderFactory.createEtchedBorder(
            EtchedBorder.LOWERED));

        // se asigna gestor de posicionado
        panelDerecho.setLayout(new BorderLayout());

        // se crean los controles para las opciones y se agregan
        // al panel
        JPanel panelControles = crearControles();
        panelDerecho.add(panelControles, BorderLayout.NORTH);

        // se crea la zona con informacion sobre la ejecucion
        JPanel infoPanel = new JPanel();

        info = new JTextArea();
        JScrollPane scroll = new JScrollPane(info);
        scroll.setPreferredSize(new Dimension(anchoPanel - 10, altoTexto + 100));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // se agregan ambos elementos al panel
        //infoPanel.add(info);
        infoPanel.add(scroll);

        // se agrega al panel
        panelDerecho.add(infoPanel, BorderLayout.SOUTH);

        // se devuelve el panel
        return panelDerecho;
    }

    /**
     * metodo para creacion del panel general de parametrizacion del
     * algoritmo de filtrado
     * @return panel de controles
     */
    private JPanel crearControles() {
        // creacion del panel
        JPanel panelControles = new JPanel();

        // se asigna gestor de posicionado: GridBagLayout. El contenido
        // del mismo sera:
        // |---------------------------------------/
        // | numero de colores | spinner seleccion |
        // |                 separador             |
        // |           modo de sel. de colores     |
        // | muestreo aleatorio |                  |
        // | pixels mas frec.   |                  |
        // |                 separador             |
        // |           criterio de parada          |
        // | iteraciones       | spinner           |
        // | estabildad        | spinner           |
        // |                 separador             |
        // | aplicar paso     | Salir              |
        // |---------------------------------------/
        panelControles.setLayout(new GridBagLayout());
        //panelControles.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

        // se crea un separador
        crearSeparador(panelControles, Color.blue, 3, 0, 2);

        // se crea el control para el numero de colores deseados
        crearPanelColores(panelControles, 4);

        // se crea un separador
        crearSeparador(panelControles, Color.red, 5, 0, 2);

        // se crea el panel para la seleccion de forma de inicializacion
        // (a partir de la fila 2)
        crearPanelInicializacion(panelControles, 6);

        // se crea un separador
        crearSeparador(panelControles, Color.red, 10, 0, 2);

        // se crea el panal para seleccion del modo de convergencia
        // los elementos ocupan a partir de la fila 7
        crearPanelConvergencia(panelControles, 11);

        // se crea un separador
        crearSeparador(panelControles, Color.red, 15, 0, 2);

        // se crean los botones de accion
        crearPanelAccion(panelControles, 16);

        // se devuelve el panel
        return panelControles;
    }

    /**
     * metodo para creacion del panel de control del
     * numero de colores a seleccionar
     * @param panel      panel donde se agregan los controles
     * @param filaInicio fila en que se ubicara el contro
     */
    private void crearPanelColores(JPanel panel, int filaInicio) {
        // se crea la etiqueta
        JLabel etiqueta = new JLabel("Número de colores");

        // se agrega al panel
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = filaInicio;
        c.insets = new Insets(10, 15, 0, 0);
        panel.add(etiqueta, c);

        // se crea el elemento de seleccion del valor
        numeroColores = new JSpinner(new SpinnerNumberModel(2, 2, 500, 1));

        // se agrega al panel
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = filaInicio;
        c.insets = new Insets(10, 15, 0, 0);
        panel.add(numeroColores, c);

        // se fila la dimension del campo de texto
        numeroColores.setPreferredSize(new Dimension(100, 30));
    }

    /**
     * metodo de creacion del panel donde se ubican los controles
     * para seleccionar el modo de inicializacion deseado
     * @param panel      panel donde se agregan los controles
     * @param filaInicio fila inicial
     */
    private void crearPanelInicializacion(JPanel panel, int filaInicio) {
        // se crea borde para el panel
        JLabel etiqueta = new JLabel("Modo de seleccion de colores");

        // se agrega la etiqueta al panel: en fila 2, columna 0
        // pero ocupando las dos columnas
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = filaInicio;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        panel.add(etiqueta, c);

        // crea grupo de botones
        ButtonGroup botones = new ButtonGroup();

        // creacion de los botones de opciones
        int fila = filaInicio + 1;
        for (TipoSeleccion modo : TipoSeleccion.values()) {
            // creacion del boton
            JRadioButton boton = null;
            switch (modo) {
                case SELECCION_ALEATORIA:
                    boton = new JRadioButton("Muestreo aleatorio");
                    boton.setSelected(true);
                    muestreoAleatorio = boton;
                    break;
                case SELECCION_MAS_FRECUENTE:
                    boton = new JRadioButton("Pixels mas frecuentes");
                    seleccionMasFrecuente = boton;
                    break;
            }

            // se agrega al grupo
            botones.add(boton);

            // se agrega al panel: en las filas 3, 4 y 5 y
            // columna 0
            GridBagConstraints bc = new GridBagConstraints();
            bc.fill = GridBagConstraints.HORIZONTAL;
            bc.gridx = 0;
            bc.gridy = fila;
            bc.insets = new Insets(10, 30, 0, 0);
            panel.add(boton, bc);

            // se incrementa el contador de fila
            fila = fila + 1;
        }
    }

    /**
     * metodo de creacion del panel con las opciones de convergencia
     * @param panel      panel donde se agregan los controles
     * @param filaInicio fila inicial
     */
    private void crearPanelConvergencia(JPanel panel, int filaInicio) {
        // se crea la etiqueta
        JLabel etiqueta = new JLabel("Criterio de convergencia");

        // se agrega la etiqueta al panel: fila 7, columna 0,
        // ocupando dos columnas
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = filaInicio;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        panel.add(etiqueta, c);

        // se crea grupo de botones
        ButtonGroup botones = new ButtonGroup();

        // se inicia el valor de la fila de inicio
        int fila = filaInicio + 1;

        // creacion de los botones de opciones
        for (TipoConvergencia modo : TipoConvergencia.values()) {

            // se crea el correspondiente selector de valores
            JSpinner selector = null;
            JRadioButton boton = null;
            switch (modo) {
                case ITERACIONES:
                    boton = new JRadioButton("Máx. iteraciones");
                    boton.setSelected(true);
                    iteraciones = boton;
                    selector = new JSpinner(new SpinnerNumberModel(10, 1, 500, 1));
                    maxIteraciones = selector;
                    break;
                case ESTABILIDAD:
                    boton = new JRadioButton("Estabilidad (% dist. max.)");
                    estabilidad = boton;
                    selector = new JSpinner(new SpinnerNumberModel(0.000001,
                        0.000001, 1.0, 0.000001));
                    JSpinner.NumberEditor edit = new JSpinner.NumberEditor(selector, "#.#######");
                    selector.setEditor(edit);
                    umbralEstabilidad = selector;
                    break;
            }

            // se agrega el boton al grupo
            botones.add(boton);

            // se agregan al panel el boton y el selection
            GridBagConstraints bc = new GridBagConstraints();
            bc.fill = GridBagConstraints.HORIZONTAL;
            bc.gridx = 0;
            bc.gridy = fila;
            bc.insets = new Insets(5, 30, 0, 0);
            panel.add(boton, bc);

            // se agrega el selector
            bc = new GridBagConstraints();
            bc.fill = GridBagConstraints.VERTICAL;
            bc.gridx = 1;
            bc.gridy = fila;
            bc.insets = new Insets(5, 15, 0, 0);
            panel.add(selector, bc);
            selector.setPreferredSize(new Dimension(100, 30));

            // se incrementa el contador de fila
            fila++;
        }
    }

    /**
     * metodo de creacion del panel con los botones de accion
     * @param panel panel donde se agregan los controles
     * @param fila  fila inicial
     */
    private void crearPanelAccion(JPanel panel, int fila) {
        // se crea el boton de compresion
        aplicarPaso = new JButton("Aplicar (paso)");
        aplicarPaso.setPreferredSize(new Dimension(50, 50));

        // se posiciona en el panel
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = fila;
        c.insets = new Insets(5, 10, 0, 0);
        panel.add(aplicarPaso, c);

        // se le asigna la funcion de accion asociada
        aplicarPaso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarPasoAgrupamiento();
            }
        });

        // se agrega el boton de recarga de imagen
        JButton recarga = new JButton("Salir");
        recarga.setPreferredSize(new Dimension(140, 50));

        // se posiciona
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = fila;
        c.insets = new Insets(5, 0, 0, 10);
        panel.add(recarga, c);

        // asigna la funcion de accion
        recarga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
    }

    /**
     * creacion del menu de opciones
     * @return menu creado
     */
    private JMenuBar crearMenuOpciones() {
        // se crea lka barra de menu
        JMenuBar barra = new JMenuBar();

        // se crea menu Archivo
        JMenu menuArchivo = new JMenu("Archivo");

        // se crean los elementos
        JMenuItem abrir = new JMenuItem("Abrir....");

        // se agrega la accion asociada
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se crea el selector de archivos
                JFileChooser selector = new JFileChooser("./data");
                if (selector.showOpenDialog(AplicacionFiltrado.this) == JFileChooser.APPROVE_OPTION) {
                    cargarArchivo(selector.getSelectedFile().getPath());
                }
            }
        });

        // se agrega al menu
        menuArchivo.add(abrir);

        // se crea la opcion de salvar
        JMenuItem salvar = new JMenuItem("Salvar....");

        // se agrega la opcion asociada
        salvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser selector = new JFileChooser("./data");
                if (selector.showSaveDialog(AplicacionFiltrado.this) == JFileChooser.APPROVE_OPTION) {
                    salvarArchivo(selector.getSelectedFile().getPath());
                }
            }
        });

        // se agrega la opcion al menu
        menuArchivo.add(salvar);

        // se crea la opcion de salir
        JMenuItem salir = new JMenuItem(("Salir...."));

        // se agrega la accion asociada
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // se agrega la opcion de salir
        menuArchivo.add(salir);

        // se agrega el menu de archivo a la barra
        barra.add(menuArchivo);

        // se devuelve la barra
        return barra;
    }

    /**
     * metodo de creacion de separador
     * @param panel   panel donde se agrega el separador
     * @param color   color para el separador
     * @param fila    fila de posicionado
     * @param columna columna de posicionado
     * @param ancho   ancho del separador
     */
    private void crearSeparador(JPanel panel, Color color, int fila, int columna, int ancho) {
        // se agrega separador
        JLabel separador1 = new JLabel("");

        // se agrega al panel
        separador1.setBorder(BorderFactory.createLineBorder(color, 1));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = columna;
        c.gridy = fila;
        c.gridwidth = ancho;
        c.insets = new Insets(10, 0, 0, 0);
        panel.add(separador1, c);
    }

    /**
     * metodo para ejecutar el filtrado mediante
     * aplicacion del algoritmo de las k-medias
     */
    private void ejecutarPasoAgrupamiento() {
        double tiempo = 0;
        double medidaConvergencia = 0;
        boolean detener = false;
        boolean paradaIteraciones = true;
        EstrategiaConvergencia detectorParada = null;

        // se determinan las condiciones de uso
        double umbral = 0.0;
        switch (obtenerModoConvergencia()) {
            case ESTABILIDAD:
                umbral = obtenerUmbralEstabilidad();
                paradaIteraciones = false;
                break;
            case ITERACIONES:
                umbral = obtenerMaximoIteraciones();
                break;
        }

        // la primera vez se crea lo necesario para ejecutar el algoritmo
        if (kmedias == null) {
            tiempo = System.currentTimeMillis();

            // se llama al metodo factoria para crear los objetos necesarios
            int k = obtenerNumeroColores();
            kmedias = KMedias.factoria(k, obtenerModoInicializacion(),
                obtenerModoConvergencia(), umbral,
                canvas.obtenerImagen());

            // se informa sobre los criterios usados
            informarTexto(k, obtenerModoInicializacion(), obtenerModoConvergencia());
        } else {
            // se considera la posible parada
            detener = kmedias.detener();

            // detectar si hay cambio en el detector de parada: en caso
            // afirmativo, se asigna de nuevo
            if (kmedias.obtenerTipoConvergencia() != obtenerModoConvergencia()) {
                // hay que erasignar el objeto usado para la deteccion de la parada
                kmedias.asignarDetectorParada(obtenerModoConvergencia(), umbral);
            }
        }

        // si no hay que detener, se prosigue
        if (!detener) {
            // se ejecuta una de las etapas del algoritmo
            Imagen nueva = kmedias.ejecutarEtapa();

            // se asigna la nueva imagen al canvas
            canvas.asignarImagen(nueva);
        } else {
            // se desactiva el boton de aplicacion
            aplicarPaso.setEnabled(false);
            aplicarPaso.setSelected(false);
        }

        // en cualquier caso se muestra informacion
        // se calcula la medida de convergencia
        medidaConvergencia = kmedias.obtenerMedidaConvergencia();

        // si hay parada, se informa
        if (detener) {
            informarTexto("alcanzada condicion de parada\n");

            // se anula el valor de kmedias
            kmedias = null;
        } else {
            // se muestra informacion sobre la evolucion del algoritmo
            informarTexto(paradaIteraciones, medidaConvergencia);
        }

        // se llama al metodo agrupar
        tiempo = System.currentTimeMillis() - tiempo;
    }

    /**
     * metodo para determinar el modo de inicializacion deseado
     * @return modo de inicializacion seleccionado
     */
    private TipoSeleccion obtenerModoInicializacion() {
        TipoSeleccion modo = null;
        if (muestreoAleatorio.isSelected()) {
            modo = TipoSeleccion.SELECCION_ALEATORIA;
        } else {
            if (seleccionMasFrecuente.isSelected()) {
                modo = TipoSeleccion.SELECCION_MAS_FRECUENTE;
            }
        }

        // se devuelve la estrategia seleccionada
        return modo;
    }

    /**
     * metodo para determinar el modo de convergencia seleccionado
     * @return modo de convergencia seleccionado
     */
    private TipoConvergencia obtenerModoConvergencia() {
        TipoConvergencia modo = null;
        if (iteraciones.isSelected()) {
            modo = TipoConvergencia.ITERACIONES;
        } else {
            if (estabilidad.isSelected()) {
                modo = TipoConvergencia.ESTABILIDAD;
            }
        }

        // se devuelve el modo usado para determinar la convergencia
        return modo;
    }

    /**
     * metodo de acceso al numero de colores seleccionado
     * @return contador seleccionado de colores
     */
    private int obtenerNumeroColores() {
        return Integer.parseInt(numeroColores.getValue().toString());
    }

    /**
     * metodo de acceso al maximo de iteraciones
     * @return maximo numero de iteraciones seleccionado
     */
    private int obtenerMaximoIteraciones() {
        return Integer.parseInt(maxIteraciones.getValue().toString());
    }

    /**
     * metodo de acceso al umbral de estabilidad
     * @return umbral para parada por estabilidad
     */
    private double obtenerUmbralEstabilidad() {
        return Double.parseDouble(umbralEstabilidad.getValue().toString());
    }

    /**
     * metodo de recarga de la imagen abierta mediante el menu o
     * cargada inicialmente
     */
    private void salir() {
        System.exit(0);
    }

    /**
     * metodo para cargar archivo
     * @param nombre nombre del archivo a cargar
     */
    private void cargarArchivo(String nombre) {
        canvas.cargarFichero(nombre, TipoAlmacen.LISTAPIXELS);

        // se vuelve a dimensionar
        dimensionar();

        // se muestra informacion en la ventana de texto
        informarTexto();

        // se vuelve a activar el boton de aplicar
        aplicarPaso.setEnabled(true);

        // el objeto kmedias se hace null
        kmedias = null;
    }

    /**
     * metodo para salvar archivo
     * @param nombre nombre del archivo a salvar
     */
    private void salvarArchivo(String nombre) {
        Utilidades.salvarImagen(canvas.obtenerImagen(), nombre);
    }

    /**
     * metodo de dimensionado de la ventana de la aplicacion
     */
    private void dimensionar() {
        // se obtiene la dimension del canvas con la
        // imagen
        Dimension dimension = canvas.getPreferredSize();

        // se asigna la dimension con la suma de los
        // anchos y el maximo de los altos
        int ancho = (int) dimension.getWidth() + anchoPanel + 10;
        int alto = altoPanel + altoTexto;
        if (dimension.getHeight() > (altoPanel + altoTexto)) {
            alto = (int) dimension.getHeight() + altoTexto + 60;
        }

        // se asigna el tamaño final
        setSize(new Dimension(ancho, alto));
    }

    /**
     * metodo para mostrar informacion por la ventana de texto
     */
    private void informarTexto() {
        // se recupera la imagen obtenida
        Imagen imagen = canvas.obtenerImagen();

        // se obtiene el nombre del archivo
        String nombreImagen = imagen.obtenerRuta();

        // se elimina la informacion de la ruta
        String nombreSolo = Utilidades.eliminarRuta(nombreImagen);

        // se recupera la informacion sobre el tipo de almacen
        TipoAlmacen tipo = imagen.obtenerTipo();

        // se recupera la informacion sobre las dimensiones de la
        // imagen
        int numeroFilas = imagen.obtenerNumeroFilas();
        int numeroColumnas = imagen.obtenerNumeroColumnas();

        // se obtiene el maximo numero de ocurrencias del pixel
        // mas frecuente
        Map.Entry<Pixel, Long> entradaMayorContador = imagen.obtenerDatosPixelMasFrecuente();

        info.append("IMAGEN: " + nombreSolo + "\n");
        info.append("tipo de almacenamiento: " + tipo.toString() + "\n");
        info.append("filas: " + numeroFilas + " columnas: " + numeroColumnas + " pixels: " + (numeroColumnas * numeroFilas) + "\n");

        // se muestra contador de maximo valor
        info.append("pixels color mas frecuente: " + entradaMayorContador.getValue() + "\n");
        info.append("ratio distancia: " + imagen.obtenerRatioDiferencia() + "\n");
        // se obtiene informacion especifica sobre el almacen
        String infoExtra = imagen.obtenerAlmacen().obtenerInfo();
        info.append(infoExtra + "\n");
        info.append("----------------------------\n");

        // se desplaza al final del texto
        info.setCaretPosition(info.getText().length());
    }

    /**
     * metodo para mostrar informacion por la ventana de texto
     * @param k
     * @param medidaConvergencia medida que provoca la parada del
     *                           algoritmo
     * @param numeroColores      numero colores de la imagen
     * @param tiempo             tiempo de ejecucion
     * @param iteraciones        iteraciones usadas
     */
    private void informarTexto(int k, double medidaConvergencia, long numeroColores,
                               double tiempo, int iteraciones) {
        // se muestra informacion de tiempo y numero de iteraciones
        info.append("**************************************\n");
        info.append("k : " + k + "\n");
        info.append(obtenerModoConvergencia().toString() + " : " +
            medidaConvergencia + "\n");
        info.append("numero de colores: " + numeroColores + "\n");
        info.append("tiempo de ejecucion: " + tiempo + "\n");
        info.append("numero de iteraciones: " + iteraciones + "\n");
    }

    /**
     * metodo para mostrar informacion sobre el inicio de ejecucion del
     * algoritmo de las k-medias
     * @param k
     * @param modoInicializacion
     * @param modoConvergencia
     */
    private void informarTexto(int k, TipoSeleccion modoInicializacion,
                               TipoConvergencia modoConvergencia) {
        info.append("INICIO AGRUPAMIENTO K-MEDIAS \n");
        info.append("k : " + k + "\n");
        info.append("Inicializacion: " + modoInicializacion.toString() + "\n");
        info.append("Convergencia: " + modoConvergencia.toString() + "\n");
        info.append("max. iteraciones: " + obtenerMaximoIteraciones() + "\n");
        switch (modoConvergencia) {
            case ESTABILIDAD:
                info.append("estabilidad: " + obtenerUmbralEstabilidad() + "\n");
                break;
        }
        info.append("----------------------------\n");
        // se analiza que ocurre al almacenar con el mapa y las listas de
        // granos
        //double ratio = Utilidades.analizarCompresion(canvas.obtenerImagen());
        //info.append("ratio compresion: " + ratio + "\n");
    }

    /**
     * muestra informacion sobre el numero de iteraciones y la distancia media
     * entre los centros
     * @param paradaIteraciones
     * @param medidaConvergencia
     */
    private void informarTexto(boolean paradaIteraciones, double medidaConvergencia) {
        if (paradaIteraciones) {
            info.append("iteraciones: " + medidaConvergencia + "\n");
        } else {
            // se muestra de todos modos el numero de iteraciones
            info.append("iteraciones: " + kmedias.obtenerIteraciones() + "\n");

            // ahora se muestra la medida de estabilidad
            DecimalFormat df = new DecimalFormat("0.000000000000");
            String cadenaPorcentaje = df.format(medidaConvergencia);
            info.append("% distancia media entre centros: " + cadenaPorcentaje + "\n");
        }
        info.append("----------------------------\n");
    }

    /**
     * muestra un mensaje por la ventana de informacion
     * @param mensaje
     */
    private void informarTexto(String mensaje) {
        info.append(mensaje);
    }

    /**
     * metodo main para lanzar la aplicacion
     * @param args argumentos del programa principal
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("error fijando estilo a interfaz");
            System.out.println(e);
        }
        AplicacionFiltrado marco = new AplicacionFiltrado();
        marco.repaint();
    }
}
