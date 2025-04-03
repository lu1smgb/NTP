package convergencia;

/**
 * enumerado para los tipos de convergencia usados
 */
public enum TipoConvergencia {
    /**
     * parada por numero de iteraciones
     */
    ITERACIONES,

    /**
     * parada por poca variacion en los centroides
     * entre iteraciones
     */
    ESTABILIDAD
}
