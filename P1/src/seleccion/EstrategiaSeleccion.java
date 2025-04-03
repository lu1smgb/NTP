package seleccion;

import almacenpixels.AlmacenPixels;
import imagen.Pixel;

import java.util.List;

/**
 * clase base para el comportamiento de inicializacion seleccionando
 * k pixels del almacen pasado como argumento
 */
public interface EstrategiaSeleccion {
    /**
     * metodo abstracto para realizar la inicializacion
     * @param almacen de pixels de los que seleccionar k
     * @param k
     */
    List<Pixel> seleccionar(AlmacenPixels almacen, int k);

    /**
     * factoria de creacion de objetos
     * @param tipo
     * @return
     */
    static EstrategiaSeleccion factoriaSeleccion(TipoSeleccion tipo){
        EstrategiaSeleccion selector = null;
        switch (tipo){
            case SELECCION_ALEATORIA:
                selector = new SeleccionAleatoria();
                break;
            case SELECCION_MAS_FRECUENTE:
                selector = new SeleccionMasFrecuente();
                break;
        }

        // se devuelve el objeto creado
        return selector;
    }
}
