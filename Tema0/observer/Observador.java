package Tema0.observer;

import Tema0.base.NumeroComplejo;

/**
 * Clase base para el comportamiento de visualizacion
 * de objetos de la clase numero complejo
 */
public abstract class Observador {

    protected NumeroComplejo observado;

    /**
     * Asignar el numero complejo observado
     * @param observado Numero complejo a observar
     */
    public void asignarObservado(NumeroComplejo observado) {
        this.observado = observado;
    }

    /**
     * Metodo abstracto que se llama cuando se produzcan
     * cambios en el numero observado
     */
    public abstract void actualizar();

}
