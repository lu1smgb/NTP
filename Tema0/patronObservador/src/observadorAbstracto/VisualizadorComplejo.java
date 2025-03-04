package observadorAbstracto;

@Deprecated
public class VisualizadorComplejo extends Observador {

    @Override
    public void actualizar() {
        System.out.println("-------------------------------");
        System.out.println("ID objeto: " + observado.getId());
        System.out.println("x: " + observado.getX());
        System.out.println("y: " + observado.getY());
        System.out.println("modulo: " + Math.sqrt(
            Math.pow(observado.getX(), 2) + Math.pow(observado.getY(), 2)
        ));
        System.out.println("-------------------------------");
    }
    
}
