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
        rojo = 0L;
        verde = 0L;
        azul = 0L;
        contador = 0L;
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
        int mediaRojo = Math.round(rojo / contador);
        int mediaVerde = Math.round(verde / contador);
        int mediaAzul = Math.round(azul / contador);
        return new Pixel(mediaRojo, mediaVerde, mediaAzul);
    }
}
