package paso4;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * representa puntos en un espacio tridimensional
 */
public class Pixel {
    /**
     * componente roja del color
     */
    private double rojo;

    /**
     * componente verde del color
     */
    private double verde;

    /**
     * componente azul del color
     */
    private double azul;

    /**
     * indice completo del color, componiendo las componentes,
     * incluido el componente alpha no almacenado
     */
    private int indice;

    /**
     * Constructor de objeto
     * @param rojo
     * @param verde
     * @param azul
     * @param indice
     */
    public Pixel(double rojo, double verde, double azul, int indice){
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
        this.indice = indice;
    }

    /**
     * devuelve el valor de la componente roja
     * @return
     */
    public double obtenerRojo(){
        return rojo;
    }

    /**
     * devuelva la componente verde
     * @return
     */
    public double obtenerVerde(){
        return verde;
    }

    /**
     * devuelve la componente azul
     * @return
     */
    public double obtenerAzul(){
        return azul;
    }

    /**
     * devuelve el valor de indice
     * @return
     */
    public int obtenerIndice(){
        return indice;
    }

    public static Pixel crearPixel(String linea){
        // se obtiene una lista con los componentes de la linea
        List<String> componentes =
                Arrays.stream(linea.split(",")).
                        map(palabra -> palabra.stripLeading()).
                        collect(Collectors.toList());

        // los componentes siempre estan en orden: rojo, verde,
        // azul, indice
        return new Pixel(Double.parseDouble(componentes.get(0)),
             Double.parseDouble(componentes.get(1)),
             Double.parseDouble(componentes.get(2)),
             Integer.parseInt(componentes.get(3)));
    }

    /**
     * calcula la distancia cuadratica de este punto y
     * el punto pasado como argumento
     * @param otro
     * @return
     */
    public double distanciaCuadratica(Pixel otro){
        return Math.pow(rojo - otro.rojo, 2) +
                Math.pow(verde - otro.verde, 2) +
                Math.pow(azul - otro.azul, 2);
    }


    public boolean enIntervalo(double minimo, double maximo){
        // se calcula si el indice esta en los rangos indicados
        return (indice >= minimo && indice <= maximo);
    }

    /**
     * metodo para mostrar el contenido del objeto por pantalla
     * @return
     */
    public String toString(){
        return " r: " + rojo + " g: " + verde + " b: " + azul;
    }
}

