package almacenpixels;

import imagen.SoporteDatosImagen;
import utilidades.Utilidades;

/**
 * interfaz para aportar la funcionalidad de creacion de almacenes
 * de pixels
 */
public interface FactoriaAlmacen {
    /**
     * metodo estatico para crear el almacen segun el tipo adecuado y tomando
     * los datos a partir del archivo correspondiente
     * @param tipo
     * @param nombreArchivo
     * @return
     */
    static AlmacenPixels crearAlmacen(TipoAlmacen tipo, String nombreArchivo) {
        AlmacenPixels almacen = null;

        // se leen los datos del archivo
        SoporteDatosImagen soporte = Utilidades.leerDatos(nombreArchivo);

        // se crea el almacen a partir de los datos del soporte
        switch (tipo) {
            case LISTAPIXELS:
                almacen = new ListaPixels();
                break;
            case MAPAPIXELLISTAGRANOS:
                break;
        }

        // se llama al metodo de generacion
        almacen.generarPixels(soporte);

        // se devuelve el almacen creado
        return almacen;
    }


}
