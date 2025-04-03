package kmedias;

import almacenpixels.AlmacenPixels;
import imagen.Pixel;

import java.util.List;
import java.util.Map;


/**
 * clase para almacenar los datos generados durante el proceso de
 * clasificacion
 */
public class DatosClasificacion {
    /**
     * dato que representa el mapeo de pixels: la clave sera el pixel antiguo
     * y el valor el pixel nuevo
     */
    private Map<Pixel, Pixel> transformacion;

    /**
     * lista de nuevos centros generados por la clasificacion
     */
    private List<Pixel> centros;

    /**
     * constructor de la clase
     * @param transformacion
     * @param centros
     */
    public DatosClasificacion(Map<Pixel,Pixel> transformacion, List<Pixel> centros) {
        this.transformacion = transformacion;
        this.centros = centros;
    }

    /**
     * accede al almacen generado
     * @return
     */
    public Map<Pixel,Pixel> obtenerTransformacion() {
        return transformacion;
    }

    /**
     * accede a los centros generados
     * @return
     */
    public List<Pixel> obtenerCentros() {
        return centros;
    }

}
