package seleccion;

import almacenpixels.AlmacenPixels;
import imagen.Pixel;

import java.util.List;

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
        return almacen.obtenerPixelsMasFrecuentes(k);
    }
}
