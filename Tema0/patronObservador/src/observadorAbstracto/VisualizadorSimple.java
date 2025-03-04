package observadorAbstracto;

@Deprecated
public class VisualizadorSimple extends Observador {
    
    @Override
    public void actualizar() {
        System.out.println("-------------------------------");
        System.out.println("ID objeto: " + observado.getId());
        System.out.println("x: " + observado.getX());
        System.out.println("y: " + observado.getY());
        System.out.println("-------------------------------");
    }

}
