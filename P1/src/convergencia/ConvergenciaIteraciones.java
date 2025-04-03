package convergencia;

import kmedias.KMedias;

/**
 * clase para aportar la funcionalidad de parada considerando
 * unicamente el numero de iteraciones
 */
public class ConvergenciaIteraciones implements EstrategiaConvergencia {
    /**
     * metodo para considerar la necesidad de parar
     * @param kmedias
     * @return
     */
    public boolean detener(KMedias kmedias){
        // se obtiene el numero de iteraciones
        int iteraciones = kmedias.obtenerIteraciones();

        // se obtiene el umbral
        double umbral = kmedias.obtenerUmbral();

        // devuelve verdadero si el numero de iteraciones supera el
        // umbral pasado como argumento
        return iteraciones >= umbral;
    }

    /**
     * se calcula la medida que puede determinar la necesidad de parar
     * @param kmedias
     * @return
     */
    @Override
    public double calcularMedida(KMedias kmedias) {
        return kmedias.obtenerIteraciones();
    }

    /**
     * devuelve el tipo de detector de parada
     * @return
     */
    @Override
    public TipoConvergencia obtenerTipo() {
        return TipoConvergencia.ITERACIONES;
    }
}
