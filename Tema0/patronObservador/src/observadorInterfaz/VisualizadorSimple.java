package observadorInterfaz;

import base.NumeroComplejo;

public class VisualizadorSimple implements Observador {
    @Override
    public void actualizar(NumeroComplejo observado) {
        System.out.println("-------------------------------");
        System.out.println("ID:\t" + observado.getId());
        System.out.println("x:\t" + observado.getX());
        System.out.println("y:\t" + observado.getY());
        System.out.println("-------------------------------");
    }
}
