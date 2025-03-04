import base.NumeroComplejo;
import observadorInterfaz.Observador;
import observadorInterfaz.VisualizadorComplejo;
import observadorInterfaz.VisualizadorSimple;

public class Main {
    /**
     * Funcion main de la clase
     * @param args
     */
    public static void main(String args[]) {
        // Inicializacion de variables
        NumeroComplejo n1 = new NumeroComplejo(1, 2);
        Observador obs1 = new VisualizadorSimple();
        Observador obs2 = new VisualizadorComplejo();
        n1.aniadirObservador(obs1);
        n1.aniadirObservador(obs2);
        n1.notificar();
        n1.duplicar();
    }
}
