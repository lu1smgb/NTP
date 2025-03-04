import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PruebaSingleton {
    
    private final static int numeroTareas = 100;

    public static void main(String args[]) {
        ExecutorService pool = Executors.newFixedThreadPool(numeroTareas);

        for (int i = 0; i < numeroTareas; i++) {
            pool.execute(new Tarea());
        }

        pool.shutdown();

        System.out.println("Numero de instancias: " + GestorConexiones.obtenerNumeroInstancias());
    }
}
