package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    static Scanner sc = new Scanner(System.in);

    int[][] habitaciones = {
            {101, 102, 103, 104, 105},
            {201, 202, 203, 204, 205},
            {301, 302, 303, 304, 305}
    };

    LocalDateTime[][] fechasIngreso = new LocalDateTime[3][5];
    String[][] nombresHuespedes = new String[3][5];

    static String[] tipo = {"ESTANDAR", "PREMIER", "SUITE"};
    static int[] precio = {300000, 500000, 1000000}


    public static void main(String[] args) {
        int opcion;

        do{
            imprimir("=====HOTEL=====");
            imprimir("1. Consultar todas las habitaciones.");
            imprimir("2. Consultar habitacion por numero.");
            imprimir("3. Consultar disponibilidad por tipo.");
            imprimir("4. Realizar check-in");
            imprimir("5. Realizar check-out y generar factura");
            imprimir("0. salir");
            System.out.print("Opcion:");
            opcion = Integer.parseInt(sc.nextLine().trim());

            switch(opcion){
                case 1 -> imprimir("pendinete");
                case 2 -> imprimir("pendiente");
                case 3 -> imprimir("pendiente");
                case 4 -> imprimir("pendiente");
                case 5 -> imprimir("pendiente");
                case 0 -> imprimir("Hasta pronto.");
                default -> imprimir("Opcion no valida.");
            }
        }while (opcion != 0);


    }

    public static void imprimir(String mensaje) {
        System.out.println(mensaje);
    }
}
