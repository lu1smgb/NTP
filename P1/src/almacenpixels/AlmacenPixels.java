package almacenpixels;

import imagen.Pixel;
import imagen.SoporteDatosImagen;
import kmedias.DatosClasificacion;
import seleccion.SeleccionAleatoria;
import seleccion.SeleccionMasFrecuente;
import utilidades.Utilidades;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.DoubleStream;

/**
 * interfaz para proporcionar el comportamiento de almacenamiento
 * de pixels para las imagenes. Se utiliza una clase abstracta para
 * poder incluir los atributos de numero de filas y numero de
 * columnas
 */
public abstract class AlmacenPixels {
    /**
     * almacena el numero de filas del archivo de datos usado para
     * la generacion
     */
    protected int numeroFilas;

    /**
     * almacena el numero de columnas del archivo de datos usado para
     * la generacion
     */
    protected int numeroColumnas;

    /**
     * mapa para almacenar los contadores de ocurrencia de cada
     * pixel
     */
    protected Map<Pixel, Long> mapaPixelContador;

    /**
     * metodo de generacion a partir de un nombre de archivo
     */
    public abstract void generarPixels(SoporteDatosImagen soporte);

    /**
     * genera un buffer para la imagen con los datos almacenados
     * @return
     */
    public abstract BufferedImage generarBuffer();

    /**
     * metodo abstracto para generar un nuevo almacen a partir de los
     * datos de clasificacion
     * @param datos
     * @return
     */
    public abstract AlmacenPixels aplicarAgrupamiento(DatosClasificacion datos);

    /**
     * obtiene el tipo del almacen
     * @return
     */
    public abstract TipoAlmacen obtenerTipo();

    /**
     * permite acceder al numero de filas
     * @return
     */
    public int obtenerNumeroFilas() {
        return numeroFilas;
    }

    /**
     * permite el acceso al numero de columnas
     * @return
     */
    public int obtenerNumeroColumnas() {
        return numeroColumnas;
    }

    /**
     * AUXILIAR
     * @return tamaño el almacen de pixeles
     */
    public int obtenerTamanioAlmacen() {
        return obtenerNumeroFilas() * obtenerNumeroColumnas();
    }

    /**
     * AUXILIAR
     * @return contadores de pixeles
     */
    public Map<Pixel, Long> obtenerContadores() { return mapaPixelContador; }

    /**
     * obtiene la entrada que se corresponde con el maximo valor de
     * contador
     * @return
     * TODO comentar
     */
    public Map.Entry<Pixel, Long> obtenerDatosPixelMasFrecuente() {
        // Obtiene la entrada con el valor del contador maximo
        return this.obtenerContadores()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }

    /**
     * obtiene k pixels de forma aleatoria
     * @param k numero de pixels a obtener
     * @return
     * TODO comentar
     */
    public List<Pixel> obtenerPixelsAleatorios(int k) {
        // Generamos k indices aleatorios
        List<Integer> indices = Utilidades.generarEnterosAleatorios(k, obtenerContadores().size());
        // Obtenemos los pixeles correspondientes a los indices
        return indices.stream()
                .map(this.obtenerContadores()
                        .keySet()
                        .stream()
                        .toList()
                        ::get)
                .toList();
    }

    /**
     * selecciona los k colores mas frecuentes
     * @param k
     * @return
     * TODO: comentar
     */
    public List<Pixel> obtenerPixelsMasFrecuentes(int k) {
        return this.obtenerContadores()
                .entrySet()
                .stream()                                                     // Obtenemos las entradas de los contadores del almacen
                .sorted(Map.Entry.<Pixel, Long>comparingByValue().reversed()) // Ordenamos en orden descendente, primero los mas frencuentes
                .limit(k)                                                     // Obtenemos los k pixeles mas recientes
                .map(Map.Entry::getKey)                                       // De las entradas, solamente nos quedamos con las claves, con los pixeles
                .toList();
    }

    /**
     * calcula la media del numero de repeticiones
     * de los colores
     * TODO comentar
     */
    public Long obtenerMediaRepeticiones() {
        double media = mapaPixelContador.values()
                .stream()
                .mapToLong(x -> x)
                .average()
                .orElse(0D);
        return Double.valueOf(media).longValue();
    }

    /**
     * calcula la desviacion tipica de los contadores de repeticiones
     * @return
     * TODO comentar
     */
    public double obtenerDesviacionTipicaRepeticiones(){
        // Formula: sigma = sqrt( sum((x_i - media)**2) / (numeroFilas * numeroColumnas) )
        // Obtenemos la media
        double media = this.obtenerMediaRepeticiones();
        // Obtenemos el numerador, el cual es una sumatoria
        double resultado = mapaPixelContador.values()
                .stream()
                .mapToDouble(x -> Math.pow(x - media, 2))
                .sum();
        // Aplicamos division entre el numero de pixeles y despues la raiz cuadrada
        resultado = DoubleStream.of(resultado).reduce(resultado,
                (_, n) -> Math.sqrt(n / this.obtenerTamanioAlmacen())
        );
        return resultado;
    }

    /**
     * obtiene la diferencia entre los pixels de maximo y minimo valor de
     * color
     * @return
     */
    public double obtenerRatioDiferencia(){
        Pixel pixelMinimo = obtenerPixelMinimoColor();
        Pixel pixelMaximo = obtenerPixelMaximoColor();

        Pixel blanco = new Pixel(255, 255, 255);
        Pixel negro = new Pixel(0, 0, 0);

        // se calcula la diferencia entre blanco y negro
        double difBlancoNegro = blanco.distanciaCuadratica(negro);

        // se calcula la distancia entre ellos
        double difImagen = pixelMaximo.distanciaCuadratica(pixelMinimo);

        // se calcula el ratio en difImagen y difBlancoNegro
        return difImagen / difBlancoNegro;
    }

    /**
     * obtiene el pixel que representa el minimo color del almacen
     * @return
     * TODO comentar
     */
    private Pixel obtenerPixelMinimoColor() {
        return mapaPixelContador.keySet()
                .stream()
                .min(Pixel::compareTo)
                .orElse(new Pixel(0));
    }

    /**
     * obtiene el pixel que representa el maximo color del almacen
     * @return
     * TODO comentar
     */
    private Pixel obtenerPixelMaximoColor() {
        return mapaPixelContador.keySet()
                .stream()
                .max(Pixel::compareTo)
                .orElse(new Pixel(0));
    }

    /**
     * metodo abstracto para generar la clasificacion de pixels
     * @param centros
     * @return
     * TODO comentar y decir que se ha implementado dentro de la clase listapixels
     */
    public abstract DatosClasificacion clasificarPixels(List<Pixel> centros);

    /**
     * obtiene informacion sobre el objeto
     * @return
     */
    public abstract String obtenerInfo();
}
