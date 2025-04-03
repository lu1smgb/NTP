package convergencia;

import kmedias.KMedias;

/**
 * interfaz para definir la funcionalidad necesaria para el
 * criterio de parada del algoritmo
 */
public interface EstrategiaConvergencia {
    /**
     * metodo para considerar la necesidad de parar
     * @param kmedias
     * @return
     */
    boolean detener(KMedias kmedias);

    /**
     * calcula la medida que se considera para la parada
     * @param kmedias
     * @return
     */
    double calcularMedida(KMedias kmedias);

    /**
     * obtiene el tipo de objeto
     * @return
     */
    TipoConvergencia obtenerTipo();

    /**
     * metodo factoria para la creacion de la estrategia de convergencia
     * @param tipo
     * @return
     */
    static EstrategiaConvergencia factoriaConvergencia(TipoConvergencia tipo){
        EstrategiaConvergencia detectorParada = null;
        switch (tipo){
            case ITERACIONES:
                detectorParada = new ConvergenciaIteraciones();
                break;
            case ESTABILIDAD:
                detectorParada = new ConvergenciaEstabilidad();
                break;
        }

        // se devuelve el detector
        return detectorParada;
    }
}
