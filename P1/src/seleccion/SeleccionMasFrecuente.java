package seleccion;

import almacenpixels.AlmacenPixels;
import imagen.Pixel;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * clase para realizar la seleccion de pixels de forma aleatoria
 */
public class SeleccionMasFrecuente implements EstrategiaSeleccion {
    /**
     * seleccion aleatoria de centroides utilizando
     * distribucion uniforme sobre el numero de pixels
     *
     * @param almacen lista de la que seleccionar k pixels
     * @return lista de objetos de la clase Pixel
     * TODO: por implementar
     */
    @Override
    public List<Pixel> seleccionar(AlmacenPixels almacen, int k) {
        // No puede ser almacen.obtenerPixelsMasFrecuentes(k); -> Bucle infinito
        return almacen.obtenerContadores().entrySet().stream()                // Obtenemos las entradas de los contadores del almacen
                .sorted(Map.Entry.<Pixel, Long>comparingByValue().reversed()) // Ordenamos en orden descendente, primero los mas frencuentes
                .limit(k)                                                     // Obtenemos los k pixeles mas recientes
                .map(Map.Entry::getKey)                                       // De las entradas, solamente nos quedamos con las claves, con los pixeles
                .toList();                                                    // Convertimos a lista
    }
}
