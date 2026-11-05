package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Hotel {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    static Scanner sc = new Scanner(System.in);

    static int[][] habitaciones = {
            {101, 102, 103, 104, 105},
            {201, 202, 203, 204, 205},
            {301, 302, 303, 304, 305}
    };

    static LocalDateTime[][] fechasIngreso = new LocalDateTime[3][5];
    static String[][] nombresHuespedes = new String[3][5];

    static String[] tipo = {"ESTANDAR", "PREMIER", "SUITE"};
    static int[] precio = {300000, 500000, 1000000};


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
                case 1 -> consultarTodasLasHabitaciones();
                case 2 -> consultarHabitacionPorNumero();
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

    public static void consultarTodasLasHabitaciones(){
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                int fila = i;
                int columna = j;
                mostrarHabitacion(fila, columna);
            }
        }

    }

    public static void consultarHabitacionPorNumero(){
        System.out.print("Ingrese el numero de la habitacion: ");
        int habitacion = Integer.parseInt(sc.nextLine().trim());

        boolean habitacionExiste = false;

        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                if (habitacion == habitaciones[i][j]){
                    habitacionExiste = true;
                    int fila = i;
                    int columna = j;
                    mostrarHabitacion(fila, columna);
                }
            }
        }
        if (!habitacionExiste){
            imprimir("Habitacion " + habitacion + " no existe.");
        }
    }

    public static void mostrarHabitacion (int fila, int columna){


                String estado;
                if (fechasIngreso[fila][columna] == null) {
                    estado = "Libre";
                } else {
                    estado = "Ocupada";
                }
                imprimir("Habitacion # " + habitaciones[fila][columna] + " ,piso " + (fila+1));
                imprimir("Tipo: " + tipo[fila]);
                imprimir("Precio: " + precio[fila]);
                imprimir("Estado de la habitacion: " + estado);
                imprimir("_______________________________");

    }
}



