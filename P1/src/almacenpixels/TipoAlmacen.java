package almacenpixels;

/**
 * enumerado para indicar los diferentes tipos de almacenamiento
 * que puede haber: lista de pixels y mapa de pixels, donde asociado
 * a cada pixel se almacena una lista de granos. Un grano permite
 * almacenar dos indices: uno de inicio y otro de fin. El indice
 * indica la posicion del pixel en el orden por columnas usado para
 * cargar los datos de la imagen
 */
public enum TipoAlmacen {
    LISTAPIXELS,
    MAPAPIXELLISTAGRANOS
}
