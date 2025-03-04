package observadorInterfaz;

import base.NumeroComplejo;

public interface Observador {
    /**
     * Metodo por implementar en las clases que usen esta interfaz
     * @param observado
     */
    public void actualizar(NumeroComplejo observado);
}
