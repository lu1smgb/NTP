package visualizador;

import almacenpixels.TipoAlmacen;
import imagen.Imagen;
import imagen.Pixel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

/**
 * clase para visualizar las imagenes
 */
public class CanvasImagen extends JComponent {
    /**
     * nombre de la imagen por defecto
     */
    private final static String defecto = "barco.png";

    /**
     * imagen mostrada
     */
    private Imagen imagen;

    /**
     * buffer para visualizacion de la imagen
     */
    BufferedImage buffer;

    /**
     * constructor de la clase
     * @param tipo tipo de almacen para las imagenes
     */
    public CanvasImagen(TipoAlmacen tipo){
        String ruta = "./data/";

        // se carga la imagen por defecto
        ruta = ruta + defecto;
        imagen = new Imagen(tipo, ruta);

        // se genera el contenido del buffer
        buffer = imagen.generarBuffer();
    }

    /**
     * devuelve la imagen
     * @return imagen representada en el canvas
     */
    public Imagen obtenerImagen(){
        return imagen;
    }

    /**
     * permite asignar la imagen a visualizar
     * @param imagen imagen a visualizar
     */
    public void asignarImagen(Imagen imagen){
        this.imagen = imagen;

        // se genera el contenido del buffer
        buffer = imagen.generarBuffer();

        // se produce el repintado
        repaint();
    }

    /**
     * metodo de utilidad para cargar la imagen de un fichero
     * @param ruta ruta de ubicacion del fichero
     * @param tipo tipo de almacen para los datos de la imagen
     */
    public void cargarFichero(String ruta, TipoAlmacen tipo){
        // se produce la llamada a recargar, pasando como argumento
        // la ruta
        recargar(tipo, ruta);
    }

    /**
     * metodo para producir la recarga de los datos de la imagen
     * en el componente grafico
     */
    public void recargar(TipoAlmacen tipo, String ruta){
        // si se ha dado valor concreto a ruta, se toma el
        // archivo indicado
        if(!Objects.equals(ruta, "./data/")){
            imagen = new Imagen(tipo, ruta);
        }
        else{
            String nuevaRuta = ruta + defecto;
            imagen = new Imagen(tipo, nuevaRuta);
        }

        // se genera el contenido del buffer
        buffer = imagen.generarBuffer();

        // se produce el repintado
        repaint();
    }

    /**
     * devuelve la dimension del componente (debe llamarse
     * de esta forma al estar sobrescrito)
     * @return dimension de la imagen del canvas
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imagen.obtenerNumeroColumnas(),
                imagen.obtenerNumeroFilas());
    }

    /**
     * metodo de pintado
     * @param graphics objeto Graphics asociado
     */
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        // se ordena el pintado
        graphics.drawImage(buffer, 0, 0, null);
    }
}
