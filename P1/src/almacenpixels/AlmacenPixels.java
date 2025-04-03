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
        return mapaPixelContador.size();
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
     */
    public Map.Entry<Pixel, Long> obtenerDatosPixelMasFrecuente() {
        // Obtiene la entrada con el valor del contador maximo
        return mapaPixelContador.entrySet().stream().max(Map.Entry.comparingByValue()).get();
    }

    /**
     * obtiene k pixels de forma aleatoria
     * @param k numero de pixels a obtener
     * @return
     */
    public List<Pixel> obtenerPixelsAleatorios(int k) {
        return new SeleccionAleatoria().seleccionar(this, k);
    }

    /**
     * selecciona los k colores mas frecuentes
     * @param k
     * @return
     */
    public List<Pixel> obtenerPixelsMasFrecuentes(int k) {
        return new SeleccionMasFrecuente().seleccionar(this, k);
    }

    /**
     * calcula la media del numero de repeticiones
     * de los colores
     */
    public Long obtenerMediaRepeticiones() {
        OptionalDouble media = mapaPixelContador.values().stream().mapToLong(x -> x).average();
        return media.isPresent() ? Double.valueOf(media.getAsDouble()).longValue() : 0L;
    }

    /**
     * calcula la desviacion tipica de los contadores de repeticiones
     * @return
     */
    public double obtenerDesviacionTipicaRepeticiones(){
        // Formula: sigma = sqrt( sum((x_i - media)**2) / (numeroFilas * numeroColumnas) )
        // Obtenemos la media
        double media = this.obtenerMediaRepeticiones();
        // Obtenemos el numerador, el cual es una sumatoria
        double resultado = mapaPixelContador.values().stream().mapToDouble(x -> Math.pow(x - media, 2)).sum();
        // Aplicamos division entre el numero de pixeles y despues la raiz cuadrada
        resultado = DoubleStream.of(resultado).reduce(resultado, (_rold, n) -> Math.sqrt(n / this.obtenerTamanioAlmacen()));
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
     */
    private Pixel obtenerPixelMinimoColor() {
        return mapaPixelContador.keySet().stream().min(Pixel::compareTo).orElse(new Pixel(0));
    }

    /**
     * obtiene el pixel que representa el maximo color del almacen
     * @return
     */
    private Pixel obtenerPixelMaximoColor() {
        return mapaPixelContador.keySet().stream().max(Pixel::compareTo).orElse(new Pixel(0));
    }

    /**
     * metodo abstracto para generar la clasificacion de pixels
     * @param centros
     * @return
     */
    public abstract DatosClasificacion clasificarPixels(List<Pixel> centros);

    /**
     * obtiene informacion sobre el objeto
     * @return
     */
    public abstract String obtenerInfo();
}
