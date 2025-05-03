package almacenpixels;

import convergencia.EstrategiaConvergencia;
import imagen.ComponentesRGBA;
import imagen.Pixel;
import imagen.SoporteDatosImagen;
import kmedias.AcumuladorDatosGrupos;
import kmedias.DatosClasificacion;
import utilidades.Utilidades;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * almacen de pixels que definen el contenido de la imagen
 */
public class ListaPixels extends AlmacenPixels {
    /**
     * almacen de datos
     */
    private List<Pixel> pixels;

    /**
     * constructor de la clase
     */
    public ListaPixels() {
        // genera el almacen
        this.pixels = new ArrayList<Pixel>();
    }

    /**
     * constructor a partir de lista de pixels
     * @param numeroFilas
     * @param numeroColumnas
     * @param pixels
     */
    public ListaPixels(int numeroFilas, int numeroColumnas, List<Pixel> pixels) {
        this.numeroFilas = numeroFilas;
        this.numeroColumnas = numeroColumnas;
        this.pixels = pixels;
    }

    /**
     * METODO AUXILIAR
     * Genera y/o actualiza los contadores de pixeles
     */
    public void actualizarContadores() {
        this.mapaPixelContador = this.pixels.stream()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
    }

    /**
     * metodo de que debe dar valor a los atributos numeroFilas y numeroColumnas.
     * Tambien debe generar la lista de pixels (atributo pixels) y el mapa
     * de pixels y contadores (atributo mapaPixelContador). Para conseguirlo
     * se usaran los datos almacenados en SoporteDatosImagen
     * @param soporte
     * TODO comentar
     */
    @Override
    public void generarPixels(SoporteDatosImagen soporte) {
        this.numeroFilas = soporte.obtenerNumeroFilas();
        this.numeroColumnas = soporte.obtenerNumeroColumnas();
        this.pixels = soporte.obtenerDatos().stream().map(Pixel::new).toList();
        this.actualizarContadores();
    }

    /**
     * genera un buffer para la imagen con los datos almacenados
     * @return
     * TODO comentar
     */
    @Override
    public BufferedImage generarBuffer() {
        BufferedImage img = new BufferedImage(this.numeroFilas, this.numeroColumnas, BufferedImage.TYPE_INT_RGB);
        IntStream.range(0, obtenerTamanioAlmacen()).forEach(i -> {
            List<Integer> coordenadas = Utilidades.convertirDesplazamientoIndices(i, this.numeroColumnas);
            Integer fila = coordenadas.get(0), columna = coordenadas.get(1);
            Pixel pixel = this.pixels.get(i);
            int indiceColor = pixel.obtenerIndiceColor();
            img.setRGB(columna, fila, indiceColor);
        });
        return img;
    }

    /**
     * genera un nuevo almacen con los datos transformados
     * @param datos
     * @return
     * TODO arreglar y comentar
     */
    @Override
    public AlmacenPixels aplicarAgrupamiento(DatosClasificacion datos) {

        // Mapa pixel a centroide
        Map<Pixel, Pixel> transformacion = datos.obtenerTransformacion();

        // Guardamos la transformacion en los pixels del nuevo almacen
        ListaPixels nuevoAlmacen = new ListaPixels();
        nuevoAlmacen.numeroFilas = this.numeroFilas;
        nuevoAlmacen.numeroColumnas = this.numeroColumnas;
        // Cambiar el pixel igual a clave -> pixel igual a valor
        nuevoAlmacen.pixels = this.pixels.stream()
                .map(transformacion::get)
                .toList();

        // Volvemos a generar los contadores a partir de la transformada
        nuevoAlmacen.actualizarContadores();

        return nuevoAlmacen;

    }

    /**
     * obtiene el tipo de almacen
     * @return
     */
    @Override
    public TipoAlmacen obtenerTipo() {
        return TipoAlmacen.LISTAPIXELS;
    }

    /**
     * metodo para generar la clasificacion de pixels
     * @param centros
     * @return
     * TODO arreglar y comentar
     */
    public DatosClasificacion clasificarPixels(List<Pixel> centros) {
        // Aqui se hace la iteracion de clasificacion de pixeles
        // en funcion de la distancia de los centroides para KMedias.ejecutarEtapa
        // Se mapea cada uno de los pixeles unicos al centroide mas cercano
        // y el resultado se guarda en el map de transformacion
        Map<Pixel, Pixel> transformacion = mapaPixelContador.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        pixel -> centros.stream()
                                .min(Comparator.comparing(pixel::distanciaCuadratica))
                                .orElse(centros.getFirst())
                ));
        return new DatosClasificacion(transformacion, centros);
    }

    /**
     * obtiene informacion sobre el objeto
     * @return
     */
    public String obtenerInfo(){
        DecimalFormat df = new DecimalFormat("0.0000");
        String cadenaMedia = df.format(obtenerMediaRepeticiones());
        String cadenaDesviacion = df.format(obtenerDesviacionTipicaRepeticiones());
        // se obtiene la estimacion del numero de bytes requeridos
        String info = "Numero de pixels: " + pixels.size() + "\n";
        info += "Media de contadores: " + cadenaMedia + "\n";
        info += "Desviación tipica: " + cadenaDesviacion + "\n";
        return info;
    }
}
