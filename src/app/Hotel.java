package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;
import java.time.YearMonth;

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
                case 3 -> consultarDisponibilidadPorTipo();
                case 4 -> imprimir("Fecha leida: " + leerFechas("Fecha (dd/MM/yyyy HH:mm): "));
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

    public static void consultarDisponibilidadPorTipo(){
        imprimir("Ingrese el tipo de la habitacion: ESTANDAR, PREMIER o SUITE ");
        String tipoBuscado = sc.nextLine().trim().toUpperCase();

        int fila = obtenerFilaTipo(tipoBuscado);

        if (fila == -1){
            imprimir("Tipo de la habitacion " + tipoBuscado + " no existe.");
            return;
        }

        boolean habitacionExiste = false;

        for (int j = 0; j < 5; j++){
            if (fechasIngreso[fila][j] == null) {
                mostrarHabitacion(fila, j);
                habitacionExiste = true;
            }

        }
        if (!habitacionExiste){
            imprimir("No hay habitaciones disponibles del tipo: " +  tipoBuscado);
        }
    }

    public static int obtenerFilaTipo(String tipoBuscado){
            for (int i = 0; i < 3; i++){
                if (tipo[i].equals(tipoBuscado)){
                    return i;
                }
            }
            return -1;
    }

    private static LocalDateTime validarFecha(String texto) {
        // Exige dos dígitos para día, mes, hora y minuto; y cuatro para el año.
        if (!texto.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
            return null;
        }

        // Separa los números usando /, espacio y : como separadores.
        Scanner partesFecha = new Scanner(texto);
        partesFecha.useDelimiter("[/: ]");
        int dia = partesFecha.nextInt();
        int mes = partesFecha.nextInt();
        int anio = partesFecha.nextInt();
        int hora = partesFecha.nextInt();
        int minuto = partesFecha.nextInt();
        partesFecha.close();

        // Revisa los rangos básicos de año, mes, día y hora.
        if (anio < 1 || mes < 1 || mes > 12 || dia < 1 || hora > 23 || minuto > 59) {
            return null;
        }

        // Obtiene cuántos días tiene ese mes; febrero puede tener 28 o 29.
        if (dia > YearMonth.of(anio, mes).lengthOfMonth()) {
            return null;
        }

        // Convierte el texto validado en una fecha y hora.
        return LocalDateTime.parse(texto, FORMATO_FECHA);
    }

    public static LocalDateTime leerFechas(String mensaje){
        LocalDateTime fecha;
        do{
            imprimir("Ingrese la fecha en formato dd/MM/yyyy HH:mm");
            String texto = sc.nextLine().trim();
            fecha = validarFecha(texto);
            if (fecha == null){
                imprimir("El formato de fecha es invalido");
            }
        }while (fecha == null);
        return fecha;

    }
}



