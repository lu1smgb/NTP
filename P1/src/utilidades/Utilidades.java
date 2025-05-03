package utilidades;

import imagen.Imagen;
import imagen.Pixel;
import imagen.SoporteDatosImagen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * clase utilidades con metodos estaticos de conveniencia para la creacion
 * de imagenes
 */
public interface Utilidades {
    final static Random generador = new Random();

    /** Utilidades para generacion de numeros aleatorios **/
    /******************************************************/

    /**
     * genera una lista de enteros aleatorios, todos ellos
     * diferentes, entre 0 y el valor pasado como argumento
     * como limite (exclusivo)
     * @param contador
     * @param limite
     * @return
     * TODO: documentar
     */
    static List<Integer> generarEnterosAleatorios(int contador, int limite) {
        return generador.ints(0, limite).distinct().limit(contador).boxed().toList();
    }

    /** Utilidades para conversion de estructuras **/
    /******************************************************/

    /**
     * convierte un conjunto de objetos en una lista, para poder
     * seleccionar sus elementos mediante un indice
     * @param conjunto
     * @return
     */
    static List<Pixel> convertirConjuntoLista(Set<Pixel> conjunto){
        // creacion de la lista

        // se agregan todos los elementos del conjunto
        List<Pixel> lista = new ArrayList<>(conjunto);

        // se devuelve la lista
        return lista;
    }

    /** Utilidades para tratamiento de cadenas **/
    /******************************************************/

    /**
     * elimina del nombre del archivo la ruta de almacenamiento y se queda
     * unicamente con el nombre
     * @param nombreArchivo
     * @return
     */
    static String eliminarRuta(String nombreArchivo){
        // se obtiene la posicion de la ultima / y se recupera el
        // resto de cadena
        int posUltimoSeparador = nombreArchivo.lastIndexOf(File.separator);
        return nombreArchivo.substring(posUltimoSeparador + 1);
    }

    /** Utilidades para tratamiento de imagenes **/
    /******************************************************/

    /**
     * metodo para leer un fichero y devolver la lista de enteros que
     * representan los colores de cada pixel: esta es la forma en que
     * se almacenan los datos en el fichero. Posteriormente, para
     * procesarlos habra que convertir cada entero en un pixel
     *
     * @param ruta ruta desde la que hacer la carga de la imagen
     * @return lista de enteros representando los colores de los pixels,
     * por columnas
     */
    static SoporteDatosImagen leerDatos(String ruta) {
        SoporteDatosImagen soporte = null;
        try {
            FileInputStream fichero = new FileInputStream(ruta);
            soporte = cargarImagen(fichero);

            // se asigna la ruta del fichero
            fichero.close();
        } catch (Exception e) {
            System.out.println("error de carga de archivo: " + ruta);
            System.out.println(e);
        }

        // se devuelven el soporte con los datos leidos del archivo
        return soporte;
    }

    /**
     * metodo auxiliar para realizar la carga de los datos
     * de un archivo de imagen
     *
     * @param flujo flujo a usar para la carga de datos
     * @return lista de enteros representando los colores de los pixels,
     * por columnas
     * TODO: documentar
     */
    private static SoporteDatosImagen cargarImagen(InputStream flujo) {
        SoporteDatosImagen resultado = null;
        try {
            //! ARREGLAR AQUII
            BufferedImage img = ImageIO.read(flujo);
            int filas = img.getHeight();
            int columnas = img.getWidth();
            int numeroPixels = filas * columnas;
            List<Integer> datos = IntStream.range(0, numeroPixels).map(desplazamiento -> {
                List<Integer> coordenadas = convertirDesplazamientoIndices(desplazamiento, filas);
                Integer fila = coordenadas.get(0), columna = coordenadas.get(1);
                return (fila < filas && columna < columnas) ? img.getRGB(columna, fila) : 0;
            }).boxed().toList();
            resultado = new SoporteDatosImagen(columnas, filas, datos);
        } catch (IOException e) {
            System.err.println("No se ha podido cargar la imagen");
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error inesperado");
        }
        return resultado;
    }

    /**
     * metodo publico para guardar una imagen en una
     * ruta
     *
     * @param imagen imagen a salvar
     * @param ruta   ruta del archivo a generar
     * NOTA: por implementar
     */
    static void salvarImagen(Imagen imagen, String ruta) {
        try {
            FileOutputStream fichero = new FileOutputStream(ruta);
            BufferedImage buffer = imagen.generarBuffer();
            // finalmente se guarda
            ImageIO.write(buffer, "png", fichero);
            fichero.close();
        } catch (Exception e) {
            System.out.println("error en salvado de fichero");
            System.out.println(e);
        }
    }

    /**
     * recibe como argumento un desplazamiento y determina los
     * indices de fila y columna correspondientes. Los indices se
     * devuelven en una lista: el primer valor sera el numero
     * de fila y el segundo el numero de columna
     * @param desplazamiento desplazamiento del pixel objetivo
     * @param columnas numero de columnas de la imagen (ancho)
     * @return lista con la fila y columna que corresponden al
     * desplazamiento indicado
     */
    static List<Integer> convertirDesplazamientoIndices(int desplazamiento,
                                                        int columnas){
        List<Integer> indices = new ArrayList<>();

        // realiza la conversion
        int fila = desplazamiento % columnas;
        int columna = desplazamiento / columnas;

        // se agregan las coordenadas a la lista
        indices.add(fila);
        indices.add(columna);

        // se devuelve la lista
        return indices;
    }
}
