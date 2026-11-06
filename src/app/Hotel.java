package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;
import java.time.YearMonth;
import java.time.Duration;
import java.time.LocalTime;

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
                case 4 -> realizarCheckIn();
                case 5 -> realizarCheckOut();
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
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            fecha = validarFecha(texto);
            if (fecha == null){
                imprimir("El formato de fecha es invalido");
            }
        }while (fecha == null);
        return fecha;

    }

    public static void realizarCheckIn(){
        String nombre;
        int fila;
       do {
           imprimir("Ingrese el nombre del huesped a registrar:");
           nombre = sc.nextLine().trim();

           if (nombre.isEmpty()) {
               imprimir("Ingrese un nombre valido.");
           }
       }while (nombre.isEmpty());

        do {
            imprimir("Ingrese el tipo de la habitacion: ESTANDAR, PREMIER o SUITE ");
            String tipoBuscado = sc.nextLine().trim().toUpperCase();
            fila = obtenerFilaTipo(tipoBuscado);

            if (fila == -1) {
                imprimir("Tipo de habitacion no disponible");
            }continue;
        }while (fila == -1);
        int columna = -1;
        for (int j = 0; j < 5; j++) {
            if (fechasIngreso[fila][j] == null){
                columna = j;
                break;
            }
        }
        if  (columna == -1){
            imprimir("No habitaciones de este tipo disponibles.");
            return;
        }
        LocalDateTime fechaIngreso = leerFechas("Fecha de ingreso (dd/MM/yyyy HH:mm): ");

        fechasIngreso [fila][columna] = fechaIngreso;
        nombresHuespedes [fila][columna]  = nombre;

        imprimir("Check-In exitoso. Habitacion " + habitaciones[fila][columna] + " asignada a " + nombre);
    }

    public static long calcularNoches(LocalDateTime fechaIngreso, LocalDateTime fechaSalida){
        LocalDateTime inicioDiaIngreso = fechaIngreso.toLocalDate().atStartOfDay();
        LocalDateTime inicioDiaSalida = fechaSalida.toLocalDate().atStartOfDay();
        long noches = Duration.between(inicioDiaIngreso, inicioDiaSalida).toDays();

        if (fechaSalida.toLocalTime().isAfter(LocalTime.of(13, 0))){
            noches++;
        }
        if (noches<1){
            noches = 1;
        }
        return noches;
    }

    public static void realizarCheckOut (){
        int fila = -1;
        int columna = -1;
        int habitacion;
        imprimir("Ingrese la habitacion de la cual van a hacer el check-out: ");
        habitacion = Integer.parseInt(sc.nextLine().trim());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (habitacion == habitaciones[i][j]){
                fila = i;
                columna = j;
                }
            }
        }
        if (fila == -1) {
            imprimir("La habitacion " + habitacion + " no existe.");
            return;
        }

        if (fechasIngreso[fila][columna] == null){
            imprimir("La habitacion " + habitacion + " esta vacia.");
            return;
        }
        LocalDateTime fechaIngreso = fechasIngreso[fila][columna];
        LocalDateTime fechaSalida;
        do {
            fechaSalida = leerFechas("Fecha de salida (dd/MM/yyyy HH:mm): ");
            if (!fechaSalida.isAfter(fechaIngreso)) {
                imprimir("La fecha de salida debe ser posterior a la de entarda.");
            }
        } while (!fechaSalida.isAfter(fechaIngreso));

        long noches = calcularNoches(fechaIngreso, fechaSalida);
        long cobroNoches = noches * precio[fila];

        imprimir("=====Factura=====");
        imprimir("Nombre huesped: " + nombresHuespedes[fila][columna]);
        imprimir("Habitacion: " + habitacion);
        imprimir("Tipo: " + tipo[fila]);
        imprimir("Fecha de ingreso: " + FORMATO_FECHA.format(fechaIngreso));
        imprimir("Fecha de salida: " + FORMATO_FECHA.format(fechaSalida));
        imprimir("Noches: " + noches);
        imprimir("Valor por noche: " + precio[fila]);
        imprimir("Cobro noches: " + cobroNoches);

        fechasIngreso[fila][columna] = null;
        nombresHuespedes[fila][columna] = null;
    }
}



