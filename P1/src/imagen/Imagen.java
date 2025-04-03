package imagen;

import almacenpixels.AlmacenPixels;
import almacenpixels.FactoriaAlmacen;
import almacenpixels.TipoAlmacen;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * clase para almacenar y gestionar imagenes png
 */
public class Imagen {
    /**
     * path al archivo del que se leyeron los datos
     */
    private String ruta;

    /**
     * dato miembro para almacenar los pixels
     */
    private AlmacenPixels pixels;

    /**
     * para indicar el tipo de almacen de que se trata
     */
    private TipoAlmacen tipo;

    /**
     * constructor de la clase
     * @param tipo
     * @param nombreArchivo
     */
    public Imagen(TipoAlmacen tipo, String nombreArchivo) {
        // se crea el almacen usando la factoria de almacenes
        pixels = FactoriaAlmacen.crearAlmacen(tipo, nombreArchivo);

        // se almacena el nombre del archivo
        ruta = nombreArchivo;

        // se almacena el tipo
        this.tipo = tipo;
    }

    /**
     * constructor de la clase a partir de almacen de pixels
     * @param pixels
     * @param ruta
     */
    public Imagen(AlmacenPixels pixels, String ruta){
        this.ruta = ruta;
        this.pixels = pixels;
        this.tipo = pixels.obtenerTipo();
    }

    /**
     * generacion del buffer: se delega en el almacen de pixels
     * @return
     */
    public BufferedImage generarBuffer() {
        return pixels.generarBuffer();
    }

    /**
     * accede al contador de filas
     * @return Numero de filas de la imagen
     */
    public int obtenerNumeroFilas() {
        return pixels.obtenerNumeroFilas();
    }

    /**
     * accede al contador de columnas
     * @return Numero de columnas de la imagen
     */
    public int obtenerNumeroColumnas(){
        return pixels.obtenerNumeroColumnas();
    }

    /**
     * devuelve la ruta
     * @return devuelve la ruta del fichero desde el que
     * se cargo la imagen del canvas
     */
    public String obtenerRuta(){
        return ruta;
    }

    /**
     * permite acceder al tipo de almacen
     * @return
     */
    public TipoAlmacen obtenerTipo(){
        return tipo;
    }

    /**
     * obtiene la entrada Pixel - contador del pixel mas repetido
     * @return
     */
    public Map.Entry<Pixel, Long> obtenerDatosPixelMasFrecuente() {
        return pixels.obtenerDatosPixelMasFrecuente();
    }

    /**
     * permite el acceso al almacen de pixels
     * @return
     */
    public AlmacenPixels obtenerAlmacen(){
        return pixels;
    }

    /**
     * obtiene la diferencia entre el pixel de minimo y maximo color
     * @return
     */
    public double obtenerRatioDiferencia(){
        return pixels.obtenerRatioDiferencia();
    }
}
