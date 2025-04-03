package convergencia;

import imagen.Pixel;
import kmedias.KMedias;

import java.util.List;

/**
 * clase para aportar la funcionalidad de parada considerando
 * la estabilidad de los centros de los grupos
 */
public class ConvergenciaEstabilidad implements EstrategiaConvergencia {
    /**
     * se determina la distancia maxima posible entre dos pixels
     */
    final static double distanciaMaxima = 255*255*3;

    /**
     * metodo para considerar la necesidad de parar
     * @param kmedias
     * @return
     * TODO: por implemetar
     */
    public boolean detener(KMedias kmedias){
        double medidaConvergencia = calcularMedida(kmedias); // Medida de convergencia: distancia media entre los centroides
        double umbral = kmedias.obtenerUmbral();             // Umbral de parada
        return medidaConvergencia <= umbral;                 // Parada: la distancia es inferior al umbral
    }

    /**
     * se calcula la medida que puede determinar la necesidad de parar
     * @param kmedias
     * @return
     * TODO: por implementar
     * TODO: debe decidirse si el método es público o privado
     */
    @Override
    public double calcularMedida(KMedias kmedias) {
        // Aqui se calcula la distancia media entre centroides

        return kmedias.obtenerCentrosT2()
    }

    /**
     * devuelve el tipo de objeto usado para la deteccion de la convergencia
     * @return
     */
    @Override
    public TipoConvergencia obtenerTipo() {
        return TipoConvergencia.ESTABILIDAD;
    }
}
