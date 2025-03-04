package observadorInterfaz;

import base.NumeroComplejo;

public class VisualizadorComplejo implements Observador {
    @Override
    public void actualizar(NumeroComplejo observado) {
        System.out.println("-------------------------------");
        System.out.println("ID:\t" + observado.getId());
        System.out.println("x:\t" + observado.getX());
        System.out.println("y:\t" + observado.getY());
        System.out.println("modulo:\t" + Math.sqrt(
            Math.pow(observado.getX(), 2) + Math.pow(observado.getY(), 2)
        ));
        System.out.println("-------------------------------");
    }
}
