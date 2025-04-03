package seleccion;

import almacenpixels.AlmacenPixels;
import imagen.Pixel;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * clase para realizar la seleccion de pixels de forma aleatoria
 */
public class SeleccionAleatoria implements EstrategiaSeleccion {

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
        // No puede ser almacen.obtenerPixelsAleatorios(k); -> Bucle infinito
        return new Random()
                .ints(k, almacen.obtenerTamanioAlmacen()) // Generamos k indices aleatorios
                .mapToObj(index ->                        // Mapeamos un indice a un pixel
                    almacen.obtenerContadores()
                            .keySet()
                            .stream()
                            .toList()
                            .get(index)
                ).toList();                               // Finalmente convertimos a lista
    }
}
