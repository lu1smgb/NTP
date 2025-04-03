package seleccion;

/**
 * enumerado con los diferentes modos de inicializacion para el
 * algoritmo de las k-medias
 */
public enum TipoSeleccion {
    /**
     * seleccion aleatoria de centroides
     */
    SELECCION_ALEATORIA,

    /**
     * seleccion de los centroides a partir de los
     * colores mas frecuentes
     */
    SELECCION_MAS_FRECUENTE
}
