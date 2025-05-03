package convergencia;

import imagen.Pixel;
import kmedias.KMedias;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.lang.Math.min;

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
        double medidaConvergencia = kmedias.obtenerMedidaConvergencia(); // Medida de convergencia: distancia media entre los centroides
        double umbral = kmedias.obtenerUmbral();                         // Umbral de parada
        return medidaConvergencia <= umbral;                             // Parada: la distancia es igual o inferior al umbral
    }

    /**
     * se calcula la medida que puede determinar la necesidad de parar
     * @param kmedias
     * @return
     * TODO: documentar y arreglar
     * TODO: debe decidirse si el método es público o privado
     * EL METODO DEBE SER PUBLICO PORQUE ES LA IMPLEMENTACION DE UN METODO ABSTRACTO PUBLICO Y ADEMAS ES
     * USADO POR KMEDIAS
     */
    @Override
    public double calcularMedida(KMedias kmedias) {
        // Aquí se calcula la distancia media entre centroides
        List<Pixel> centrosT1 = kmedias.obtenerCentrosT1();
        List<Pixel> centrosT2 = kmedias.obtenerCentrosT2();
        double distancia = distanciaEntreCentros(centrosT1, centrosT2);
        double ratio = kmedias.obtenerRatioDiferencia();
        return distancia / (distanciaMaxima * ratio);
    }

    private double distanciaEntreCentros(List<Pixel> ct1, List<Pixel> ct2) {
        return IntStream.range(0, ct1.size()).mapToDouble(i -> ct1.get(i).distanciaCuadratica(ct2.get(i))).sum();
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
