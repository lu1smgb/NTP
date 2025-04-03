package paso3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * clase para mostrar ejemplos relacionados con el procesamiento
 * de los datos almacenados en un archivo
 */
public class ProcesamientoArchivo {
    /**
     * permite almacenar el contenido de datos de un archivo
     */
    private List<String> contenido;
    private Map<String, Long> contadores;

    /**
     * constructor de la clase
     * 
     * @param nombre
     */
    public ProcesamientoArchivo(String nombre) {
        // se almacena en contenido todo lo leido del archivo
        contenido = leerArchivo(nombre);
        contadores = generarContadoresFuncional();
    }

    /**
     * por implementar
     * 
     * @param nombre
     * @return
     */
    private List<String> leerArchivo(String nombre) {
        List<String> lineas = new ArrayList<String>();

        try {
            // Leemos las lineas, pasamos a minuscula y eliminamos caracteres especiales
            lineas = Files.lines(Paths.get(nombre), StandardCharsets.UTF_8)
                    .map(linea -> linea.toLowerCase().replaceAll("[^a-záéíóú\\s]", ""))
                    .toList();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + nombre);
            System.err.println(e);
            System.exit(-1);
        }

        return lineas;
    }

    public Map<String, Long> generarContadoresImperativo() {
        Map<String, Long> contadores = new TreeMap<String, Long>();

        Pattern patron = Pattern.compile("\\s+");

        for (String linea : contenido) {
            String palabras[] = patron.split(linea);

            for (int i=0; i < palabras.length; i++) {
                String palabra = palabras[i];
                if (!palabra.isEmpty()) {
                    long contador = 1L;
                    if (contadores.containsKey(palabra)) {
                        contador = contadores.get(palabra) + 1;
                    }
                    contadores.put(palabra, contador);
                }
            }
        }

        return contadores;
    }

    public Map<String, Long> generarContadoresFuncional() {

        Pattern patron = Pattern.compile("\\s+");

        // Separamos las palabras
        // Quitamos las que estan vacias
        /**
         * ARGUMENTOS DE groupingBy
         *  1. Criterio de agrupacion
         *  2. Tipo de la coleccion resultante
         *  3. Como se procesa la coleccion
         */
        return contenido.stream().flatMap(linea -> patron.splitAsStream(linea))
                        .filter(palabra -> !palabra.isEmpty())
                        .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

    }

    public void mostrarContadores() {

        System.out.println("--------------- MAPA DE CONTADORES ---------------");
        System.out.println(generarCadenaContadores());
        System.out.println("--------------------------------------------------");

    }

    public String generarCadenaContadores() {
        return contadores.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue())
                                             .collect(Collectors.joining("\n"));
    }

    public Map<Character, List<String>> generarDiccionario() {

        Pattern patron = Pattern.compile("\\s+");

        Map<String, Long> contadores = generarContadoresFuncional();

        return contadores.keySet().stream()
                         .collect(Collectors.groupingBy(palabra -> palabra.charAt(0), TreeMap::new, Collectors.toList()));
    }

    /**
     * metodo main para pruebas
     * 
     * @param args
     */
    public static void main(String args[]) {
        // se crea objeto de la clase
        ProcesamientoArchivo objeto = new ProcesamientoArchivo("./data/fuenteOvejuna.txt");
        objeto.mostrarContadores();
    }
}

// Instalar jmh
// 1. Settings
// 2. Files/project structure/libraries