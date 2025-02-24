package Tema0;

import Tema0.base.NumeroComplejo;

public class Main {
    /**
     * Funcion main de la clase
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hola");
        System.out.println("Argumentos: " + args.length);

        // Constructor por defecto
        System.out.println("--- Constructor por defecto ---");
        NumeroComplejo n1 = null;
        n1 = new NumeroComplejo();
        System.out.println(n1);
        System.out.println("Objetos: " + NumeroComplejo.getNumObjetos());

        // Constructor por parametros
        System.out.println("--- Constructor por parametros ---");
        n1 = new NumeroComplejo(1, 2);
        System.out.println(n1);
        System.out.println("Objetos: " + NumeroComplejo.getNumObjetos());

        // Funcion duplicar estatica
        System.out.println("--- Funcion duplicar estatica ---");
        NumeroComplejo.duplicar(n1);
        System.out.println(n1);

        // Funcion duplicar no estatica
        System.out.println("--- Funcion duplicar no estatica ---");
        n1.duplicar();
        System.out.println(n1);

        // Funcion cambiarAzar estatica
        System.out.println("--- Funcion cambiarAzar estatica ---");
        NumeroComplejo.cambiarAzar(n1);
        System.out.println(n1);

        // Funcion duplicar no estatica
        System.out.println("--- Funcion cambiarAzar no estatica ---");
        n1.cambiarAzar();
        System.out.println(n1);
    }
}
