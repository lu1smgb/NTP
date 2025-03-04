package base;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import observadorInterfaz.Observador;

// java NumeroComplejo.java [args]
public class NumeroComplejo {

    /**
     * Variables estaticas
     * - Contador de objetos
     * - Identificador de clase
     */
    private static long contadorObjetos = 0;
    public final static String IDCLASE = "NumeroComplejo";

    /**
     * Parte real
     */
    protected double x;

    /** 
     * Parte imaginaria
    */
    protected double y;

    /**
     * Identificador
     */
    private long id;

    /**
     * Observadores
     */
    private List<Observador> observadores;

    /**
     * Constructor por defecto
     */
    public NumeroComplejo() {
        id = NumeroComplejo.contadorObjetos++;
        this.observadores = new ArrayList<Observador>();
    };

    /**
     * Constructor por parametros
     * @param x Parte real
     * @param y Parte imaginaria
     */
    public NumeroComplejo(double x, double y) {
        this();
        this.x = x;
        this.y = y;
    }

    /**
     * Getter del contador de objetos
     * @return Numero de objetos creados
     */
    public static long getNumObjetos() {
        return NumeroComplejo.contadorObjetos;
    }

    /**
     * Getter de la parte real
     * @return Parte real del numero
     */
    public double getX() {
        return this.x;
    }

    /**
     * Getter de la parte imaginaria
     * @return Parte imaginaria del numero
     */
    public double getY() {
        return this.y;
    }

    /**
     * Getter del identificador del numero
     * @return Identificador del numero
     */
    public long getId() {
        return this.id;
    }

    /**
     * Setter del contador de objetos
     * @param numObjetos Nuevo numero de objetos
     */
    public static void setNumObjetos(long numObjetos) {
        contadorObjetos = numObjetos;
    }

    /**
     * Setter de la parte real
     * @param x Nueva parte real
     */
    public void setX(double x) {
        this.x = x;
        notificar();
    } 

    /**
     * Setter de la parte imaginaria
     * @param y Nueva parte imaginaria
     */
    public void setY(double y) {
        this.y = y;
        notificar();
    }

    /**
     * Setter del identificador del numero
     * @param id Nuevo identificador del numero
     */
    public void setId(long id) {
        this.id = id;
        notificar();
    }

    /**
     * Agrega un observador a la lista de observadores del numero
     * @param obs Observador a agregar
     */
    public void aniadirObservador(Observador obs) {
        if (!this.observadores.contains(obs)) {
            this.observadores.add(obs);
            //// obs.asignarObservado(this);
        }
    }

    /**
     * Metodo para notificar a todos los observadores que actualizen su vista
     */
    public void notificar() {
        for (Observador observador : this.observadores) {
            observador.actualizar(this);
        }
    }

    /**
     * Duplica los valores del numero
     * Version estatica / de clase
     * @param num Numero complejo sobre el que realizar la operacion
     */
    public static void duplicar(NumeroComplejo num) {
        num.setX(num.getX()*2);
        num.setY(num.getY()*2);
        num.notificar();
    }

    /**
     * Duplica los valores del numero
     * Version no estatica / de instancia
     */
    public void duplicar() {
        this.x *= 2;
        this.y *= 2;
        notificar();
    }

    /**
     * Asigna valores al azar
     * Version estatica / de clase
     * @param num Numero complejo sobre el que realizar la operacion
     */
    public static void cambiarAzar(NumeroComplejo num) {
        Random rng = new Random();
        num = new NumeroComplejo(rng.nextDouble(), rng.nextDouble());
        num.notificar();
    }

    /**
     * Asigna valores al azar
     * Version no estatica / de instancia
     */
    public void cambiarAzar() {
        Random rng = new Random();
        this.setX(rng.nextDouble());
        this.setY(rng.nextDouble());
        notificar();
    }

    @Override
    public String toString() {
        return "[" + this.id + "] " + this.x + " + i" + this.y;
    }

}
