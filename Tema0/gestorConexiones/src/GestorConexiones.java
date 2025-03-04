/**
 * Gestor de conexiones SINGLETON
 */
public class GestorConexiones {

    private static int contadorInstancias = 0;
    
    /**
     * Instancia
     */
    private static GestorConexiones instance = null;

    /**
     * Constructor privado
     */
    private GestorConexiones() {
        System.out.println("Gestor creado correctamente");
        contadorInstancias++;
    };

    /**
     * Metodo para obtener el gestor de conexiones
     * Se inicializa si se llama a este objeto por primera vez
     * V1:
     * - Llama a la instancia declarada en la definicion de la clase
     * - Si no se ha declarado, no funcionara
     * @return La instancia de gestor de conexiones
     */
    public static GestorConexiones obtenerGestor1() {
        System.out.println("Gestor obtenido");
        return instance;
    }

    /**
     * Metodo para obtener el gestor de conexiones
     * Se inicializa si se llama a este objeto por primera vez
     * V1:
     * - Funcion SINCRONA
     * - Inicializa la instancia
     * @return La instancia de gestor de conexiones
     */
    public static synchronized GestorConexiones obtenerGestor2() {
        if (instance == null) {
            instance = new GestorConexiones();
        }
        System.out.println("Gestor obtenido");
        return instance;
    }

    /**
     * Metodo para obtener el gestor de conexiones
     * Se inicializa si se llama a este objeto por primera vez
     * V1:
     * - Funcion con bloque SINCRONO
     * - Inicializa la instancia
     * @return La instancia de gestor de conexiones
     */
    public static GestorConexiones obtenerGestor3() {
        if (instance == null) {
            synchronized (GestorConexiones.class) {
                instance = new GestorConexiones();
            }
        }
        System.out.println("Gestor obtenido");
        return instance;
    }

    public static int obtenerNumeroInstancias() {
        return contadorInstancias;
    }

}
