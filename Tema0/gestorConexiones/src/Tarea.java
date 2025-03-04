public class Tarea implements Runnable {

    private static int contadorTareas = 0;

    private int id;

    public Tarea() {
        this.id = contadorTareas++;
    }

    @Override
    public void run() {
        GestorConexiones gestorConexiones = GestorConexiones.obtenerGestor3();
        for (int i = 0; i < 10000; i++) {}
    }
}
