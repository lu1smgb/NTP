package imagen;

import java.util.ArrayList;
import java.util.List;

/**
 * clase para almacenar la informacion leida de los archivos de
 * imagenes
 */
public class SoporteDatosImagen {
    /**
     * numero de filas de la imagen
     */
    private int numeroFilas;

    /**
     * numero de columnas de la imagen
     */
    private int numeroColumnas;

    /**
     * lista de enteros codificando los colores de los pixels
     */
    private List<Integer> datos;

    /**
     * constructor asignando valores a los atributos
     * @param numeroFilas
     * @param numeroColumnas
     * @param datos
     */
    public SoporteDatosImagen(int numeroFilas, int numeroColumnas, List<Integer> datos) {
        this.numeroFilas = numeroFilas;
        this.numeroColumnas = numeroColumnas;
        this.datos = datos;
    }

    /**
     * metodo de acceso al numero de filas
     * @return
     */
    public int obtenerNumeroFilas(){
        return numeroFilas;
    }

    /**
     * metodo de acceso al numero de columnas
     * @return
     */
    public int obtenerNumeroColumnas(){
        return numeroColumnas;
    }

    /**
     * metodo de acceso a los datos
     * @return
     */
    public List<Integer> obtenerDatos(){
        return datos;
    }
}
