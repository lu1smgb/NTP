package kmedias;

import imagen.ComponentesRGBA;
import imagen.Pixel;
import imagen.RGBA;

/**
 * clase auxiliar para acumular los datos de los pixels que se
 * van asignando a los centros
 */
public class AcumuladorDatosGrupos {
    private Long rojo;
    private Long verde;
    private Long azul;
    private Long contador;

    /**
     * constructor de la clase
     */
    public AcumuladorDatosGrupos() {
        rojo = (Long) 0L;
        verde = (Long) 0L;
        azul = (Long) 0L;
        contador = (Long) 0L;
    }

    /**
     * metodo para acumular sobre el dato de interes
     * @param pixel
     */
    public void acumular(Pixel pixel){
        rojo += pixel.obtenerComponente(ComponentesRGBA.ROJO);
        verde += pixel.obtenerComponente(ComponentesRGBA.VERDE);
        azul += pixel.obtenerComponente(ComponentesRGBA.AZUL);

        // cada vez que se acumula, se incrementa el contador
        contador++;
    }

    /**
     * calcula la media de los valores R, G y B
     * @return
     */
    public Pixel calcularMedia(){
        int mediaRojo = Math.round((float) rojo / contador);
        int mediaVerde = Math.round((float) verde / contador);
        int mediaAzul = Math.round((float) azul / contador);
        return new Pixel(mediaRojo, mediaVerde, mediaAzul);
    }
}
