package kmedias;

import almacenpixels.AlmacenPixels;
import almacenpixels.ListaPixels;
import almacenpixels.TipoAlmacen;
import convergencia.*;
import imagen.Imagen;
import imagen.Pixel;
import seleccion.*;

import java.nio.channels.Selector;
import java.util.List;

import static convergencia.TipoConvergencia.*;

/**
 * clase principal para gestionar la aplicacion del algoritmo de las
 * k-medias
 */
public class KMedias {
    /**
     * imagen a procesar: los datos de entrada para el
     * algoritmo de las k-medias son los pixels de la
     * imagen
     */
    private final Imagen imagen;

    /**
     * numero de grupos a formar
     */
    private final int k;

    /**
     * atributo para delegar el comportamiento de seleccion de
     * pixels
     */
    private EstrategiaSeleccion selector;

    /**
     * atributo para delegar el comportamiento de determinacion
     * de convergencia
     */
    private EstrategiaConvergencia detectorParada;

    /**
     * umbral para deteccion de parada
     */
    private double umbral;

    /**
     * contador de iteraciones ejecutadas por el algoritmo
     */
    private int iteraciones;

    /**
     * almacena la distancia media entre los centros obtenidos en
     * una iteración
     */
    private double medidaConvergencia;

    /**
     * clasificacion de pixels en los grupos. Las claves
     * son los centroides y los valores las listas de
     * pixels propias de cada grupo
     */
    private DatosClasificacion datosClasificacion;

    /**
     * lista de centroides de la iteracion al inicio
     */
    private List<Pixel> centrosT1;

    /**
     * lista de centroides generados al final de la
     * iteracion
     */
    private List<Pixel> centrosT2;

    /**
     * constructor privado para evitar creacion de
     * objetos que no pase por el metodo factoria
     * @param imagen imagen a filtrar
     * @param k numero de colores a considerar
     * @param selector estrategia de inicializacion
     * @param detectorParada estretagia de convergencia
     * @param umbral valor umbral para deteccion de parada
     */
    private KMedias(Imagen imagen, int k, EstrategiaSeleccion selector,
                    EstrategiaConvergencia detectorParada, double umbral) {
        // se asigna valor a los datos miembro
        this.imagen = imagen;
        this.k = k;
        this.selector = selector;
        this.detectorParada = detectorParada;
        this.umbral = umbral;

        // se inicializa el contador de iteraciones
        iteraciones = 0;

        // el primer paso consiste en obtener la lista de centros iniciales.
        // La tarea se realiza delegando en el inicializador
        centrosT1 = selector.seleccionar(imagen.obtenerAlmacen(), k);
    }

    /**
     * metodo factoria: es la unica forma de crear
     * objetos de la clase
     * @param k numero de colores a considerar
     * @param modoInicializacion estretegia de inicializacion
     * @param modoConvergencia estretegia de convergencia
     * @param umbral umbral a considerar para la convergencia
     * @param imagen imagen a analizar
     * @return objeto de la clase KMedias construido de acuerdo
     * a la parametrizacion pasada como argumento
     */
    public static KMedias factoria(int k, TipoSeleccion modoInicializacion,
                                   TipoConvergencia modoConvergencia,
                                   double umbral, Imagen imagen){
        // se crea el selector de centroides
        EstrategiaSeleccion selector = EstrategiaSeleccion.factoriaSeleccion(modoInicializacion);

        // se crea el objeto para la deteccion de la condicion de parada
        EstrategiaConvergencia detectorParada = EstrategiaConvergencia.factoriaConvergencia(modoConvergencia);

        // se crea el objeto y se devuelve
        return new KMedias(imagen, k, selector, detectorParada, umbral);
    }

    /**
     * permite cambiar el detector de parada en tiempo de ejecucion
     * @param tipoDetector
     * @param umbral usado para el tipo de convergencia
     */
    public void asignarDetectorParada(TipoConvergencia tipoDetector, double umbral){
        switch (tipoDetector){
            case ITERACIONES:
                this.detectorParada = new ConvergenciaIteraciones();
                break;
            case ESTABILIDAD:
                this.detectorParada = new ConvergenciaEstabilidad();
                break;
        }

        // se cambia el valor de umbral
        this.umbral = umbral;
    }

    /**
     * se recupera el tipo de detector de parada usado
     * @return
     */
    public TipoConvergencia obtenerTipoConvergencia() {
        return detectorParada.obtenerTipo();
    }

    /**
     * obtiene el numero de iteraciones
     * @return
     */
    public Integer obtenerIteraciones(){
        return iteraciones;
    }

    /**
     * obtiene la distancia media entre los centros de una iteración
     * y de la siguiente
     * @return
     */
    public double obtenerMedidaConvergencia(){
        medidaConvergencia = detectorParada.calcularMedida(this);
        return medidaConvergencia;
    }

    /**
     * se obtiene la primera lista de centros
     * @return
     */
    public List<Pixel> obtenerCentrosT1(){
        return centrosT1;
    }

    /**
     * se obtiene la segunda lista de centros
     * @return
     */
    public List<Pixel> obtenerCentrosT2(){
        return centrosT2;
    }

    /**
     * obtiene el valor de umbral
     * @return
     */
    public double obtenerUmbral(){
        return umbral;
    }

    /**
     * determina si hay que parar o no
     * @return
     * TODO: por implementar
     */
    public boolean detener() {
        return detectorParada.detener(this);
    }

    /**
     * TODO: metodo por implementar
     */
    public Imagen ejecutarEtapa() {
        DatosClasificacion clasificacion = imagen.obtenerAlmacen().clasificarPixels(centrosT2 == null ? centrosT1 : centrosT2);
    }
}
