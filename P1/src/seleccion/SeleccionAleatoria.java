package seleccion;

import almacenpixels.AlmacenPixels;
import imagen.Pixel;
import utilidades.Utilidades;

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
     * TODO: documentar
     */
    @Override
    public List<Pixel> seleccionar(AlmacenPixels almacen, int k) {
        return almacen.obtenerPixelsAleatorios(k);
    }
}
