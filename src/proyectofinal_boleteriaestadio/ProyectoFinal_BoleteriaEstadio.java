package proyectofinal_boleteriaestadio;

import java.util.Scanner;
import java.time.LocalDate;

public class ProyectoFinal_BoleteriaEstadio {

    private static Scanner entrada = new Scanner(System.in);

    // ----------------------
    // ARREGLOS PARA PARTIDOS
    // ----------------------
    private static int[] codigosPartidos = new int[10];
    private static String[] descripciones = new String[10];
    private static int[] fechas = new int[10];  // ddmmaaaa

    private static int[] capacidadesSombra = new int[10];
    private static int[] capacidadesSol = new int[10];
    private static int[] capacidadesPalco = new int[10];

    private static double[] preciosSombra = new double[10];
    private static double[] preciosSol = new double[10];
    private static double[] preciosPalco = new double[10];

    private static int contadorPartidos = 0;

    // ----------------------
    // ARREGLOS PARA BOLETOS POR PARTIDO
    // ----------------------
    private static String[][] codigosBoletosVendidos = new String[10][];
    private static int[][] edadesBoletos = new int[10][];
    private static double[][] preciosBoletos = new double[10][];

    private static int[] contadorBoletosSombra = new int[10];
    private static int[] contadorBoletosSol = new int[10];
    private static int[] contadorBoletosPalco = new int[10];

    // ----------------------
    // METODO PRINCIPAL
    // ----------------------
    public static void main(String[] args) {
        int opcionPrincipal;

        do {
            System.out.println("\n--- MENU PRINCIPAL DE BOLETERIA ---");
            System.out.println("1. Administrador");
            System.out.println("2. Vender boletos");
            System.out.println("3. Estadisticas en tiempo real");
            System.out.println("4. Salir del sistema");
            System.out.print("Ingrese el numero de su opcion: ");

            opcionPrincipal = entrada.nextInt();
            entrada.nextLine();

            switch (opcionPrincipal) {
                case 1:
                    menuAdministrador();
                    break;
                case 2:
                    venderBoletos();
                    break;
                case 3:
                    reporteAsistenciaPorSectorYTipo();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
                    break;
            }
        } while (opcionPrincipal != 4);
    }

    // ----------------------
    // MENU ADMINISTRADOR
    // ----------------------
    public static void menuAdministrador() {
        int opcionAdmin;

        do {
            System.out.println("\n--- MENU DE ADMINISTRADOR ---");
            System.out.println("1. Ingresar nuevo partido");
            System.out.println("2. Ver partidos existentes");
            System.out.println("3. Volver al menu principal");
            System.out.print("Ingrese el numero de su opcion: ");

            opcionAdmin = entrada.nextInt();
            entrada.nextLine();

            switch (opcionAdmin) {
                case 1:
                    ingresarPartido();
                    break;
                case 2:
                    verPartidos();
                    break;
                case 3:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
                    break;
            }
        } while (opcionAdmin != 3);
    }

    // ----------------------
    // FUNCION: INGRESAR NUEVO PARTIDO
    // ----------------------
    public static void ingresarPartido() {
        int codigo, dia, mes, anio;
        boolean repetido;

        if (contadorPartidos >= codigosPartidos.length) {
            System.out.println("No se pueden agregar mas partidos. Arreglo lleno.");
            return;
        }

        System.out.println("\n** Nuevo Partido **");

        // Validar codigo unico
        do {
            System.out.print("Ingrese el codigo numerico del partido: ");
            while (!entrada.hasNextInt()) {
                System.out.println("Solo se permiten numeros.");
                entrada.next();
            }
            codigo = entrada.nextInt();
            entrada.nextLine();

            repetido = false;
            for (int i = 0; i < contadorPartidos; i++) {
                if (codigosPartidos[i] == codigo) {
                    System.out.println("Ese codigo ya existe. Intente con otro.");
                    repetido = true;
                    break;
                }
            }
        } while (repetido);
        codigosPartidos[contadorPartidos] = codigo;

        // Descripcion
        System.out.print("Ingrese la descripcion del partido: ");
        descripciones[contadorPartidos] = entrada.nextLine();

        // Fecha
        System.out.print("Ingrese el dia del partido (1-31): ");
        dia = entrada.nextInt();
        System.out.print("Ingrese el mes del partido (1-12): ");
        mes = entrada.nextInt();
        System.out.print("Ingrese el anio del partido (AAAA): ");
        anio = entrada.nextInt();
        entrada.nextLine();
        fechas[contadorPartidos] = dia * 1000000 + mes * 10000 + anio;

        // Capacidades y precios
        System.out.print("Ingrese la capacidad de sombra: ");
        capacidadesSombra[contadorPartidos] = entrada.nextInt();
        System.out.print("Ingrese el precio de sombra: ");
        preciosSombra[contadorPartidos] = entrada.nextDouble();

        System.out.print("Ingrese la capacidad de sol: ");
        capacidadesSol[contadorPartidos] = entrada.nextInt();
        System.out.print("Ingrese el precio de sol: ");
        preciosSol[contadorPartidos] = entrada.nextDouble();

        System.out.print("Ingrese la capacidad de palco: ");
        capacidadesPalco[contadorPartidos] = entrada.nextInt();
        System.out.print("Ingrese el precio de palco: ");
        preciosPalco[contadorPartidos] = entrada.nextDouble();
        entrada.nextLine();

        // Inicializar contadores de boletos por sector
        contadorBoletosSombra[contadorPartidos] = 0;
        contadorBoletosSol[contadorPartidos] = 0;
        contadorBoletosPalco[contadorPartidos] = 0;

        // Dimensionar arreglo de boletos para este partido
        int capacidadTotal = capacidadesSombra[contadorPartidos] +
                             capacidadesSol[contadorPartidos] +
                             capacidadesPalco[contadorPartidos];
        codigosBoletosVendidos[contadorPartidos] = new String[capacidadTotal];
        edadesBoletos[contadorPartidos] = new int[capacidadTotal];
        preciosBoletos[contadorPartidos] = new double[capacidadTotal];

        contadorPartidos++;
        System.out.println("Partido agregado correctamente.");
    }

    // ----------------------
    // FUNCION: VER PARTIDOS
    // ----------------------
    public static void verPartidos() {
        int dia, mes, anio;

        if (contadorPartidos == 0) {
            System.out.println("No hay partidos registrados.");
            return;
        }

        System.out.println("\n--- LISTA DE PARTIDOS ---");
        for (int i = 0; i < contadorPartidos; i++) {
            dia = fechas[i] / 1000000;
            mes = (fechas[i] / 10000) % 100;
            anio = fechas[i] % 10000;

            System.out.println("Codigo: " + codigosPartidos[i] +
                               " | Descripcion: " + descripciones[i] +
                               " | Fecha: " + dia + "/" + mes + "/" + anio +
                               " | Sombra: " + capacidadesSombra[i] + " disponibles - Precio: " + preciosSombra[i] +
                               " | Sol: " + capacidadesSol[i] + " disponibles - Precio: " + preciosSol[i] +
                               " | Palco: " + capacidadesPalco[i] + " disponibles - Precio: " + preciosPalco[i]);
        }
    }

    // ----------------------
    // FUNCION: VENDER BOLETOS (CORREGIDA)
    // ----------------------
    public static void venderBoletos() {
        int codigoPartido, i, opcionSector, cantidad, edad, dia, mes, anio;
        String sector = "";
        double precioBase = 0, precioFinal;
        LocalDate hoy;
        LocalDate fechaPartido;

        if (contadorPartidos == 0) {
            System.out.println("No hay partidos disponibles.");
            return;
        }

        verPartidos();

        System.out.print("Ingrese codigo del partido que desea comprar: ");
        codigoPartido = entrada.nextInt();
        entrada.nextLine();

        // Buscar indice del partido
        for (i = 0; i < contadorPartidos; i++) {
            if (codigosPartidos[i] == codigoPartido) {
                break;
            }
        }

        if (i == contadorPartidos) {
            System.out.println("Codigo no valido.");
            return;
        }

        // Seleccion de sector
        System.out.println("Sectores disponibles:");
        System.out.println("1. Sombra (" + capacidadesSombra[i] + " disponibles) - Precio: " + preciosSombra[i]);
        System.out.println("2. Sol (" + capacidadesSol[i] + " disponibles) - Precio: " + preciosSol[i]);
        System.out.println("3. Palco (" + capacidadesPalco[i] + " disponibles) - Precio: " + preciosPalco[i]);
        System.out.print("Seleccione sector (1-3): ");
        opcionSector = entrada.nextInt();
        entrada.nextLine();

        System.out.print("Ingrese cantidad de boletos (maximo 5): ");
        cantidad = entrada.nextInt();
        entrada.nextLine();

        if (cantidad > 5) {
            System.out.println("No se pueden vender mas de 5 boletos por transaccion.");
            return;
        }

        int baseIndex = 0;
        int currentCounter;

        switch (opcionSector) {
            case 1:
                if (cantidad > capacidadesSombra[i]) {
                    System.out.println("No hay suficientes boletos disponibles en este sector.");
                    return;
                }
                sector = "Sombra";
                precioBase = preciosSombra[i];
                currentCounter = contadorBoletosSombra[i];
                break;
            case 2:
                if (cantidad > capacidadesSol[i]) {
                    System.out.println("No hay suficientes boletos disponibles en este sector.");
                    return;
                }
                sector = "Sol";
                precioBase = preciosSol[i];
                baseIndex = contadorBoletosSombra[i];
                currentCounter = contadorBoletosSol[i];
                break;
            case 3:
                if (cantidad > capacidadesPalco[i]) {
                    System.out.println("No hay suficientes boletos disponibles en este sector.");
                    return;
                }
                sector = "Palco";
                precioBase = preciosPalco[i];
                baseIndex = contadorBoletosSombra[i] + contadorBoletosSol[i];
                currentCounter = contadorBoletosPalco[i];
                break;
            default:
                System.out.println("Sector no valido.");
                return;
        }

        hoy = LocalDate.now();
        int fechaInt = fechas[i];
        dia = fechaInt / 1000000;
        mes = (fechaInt / 10000) % 100;
        anio = fechaInt % 10000;
        fechaPartido = LocalDate.of(anio, mes, dia);

        boolean preventa = hoy.isEqual(fechaPartido.minusDays(1));

        System.out.println("\n--- Boletos Comprados ---");
        for (int j = 0; j < cantidad; j++) {
            int numeroBoleto = currentCounter + 1;
            String codigoBoleto = codigoPartido + "-" + sector.charAt(0) + "-" + numeroBoleto;

            System.out.print("Ingrese edad del comprador para el boleto " + (j + 1) + ": ");
            edad = entrada.nextInt();
            entrada.nextLine();

            if (edad >= 65) {
                precioFinal = precioBase * 0.65;
            } else if (preventa) {
                precioFinal = precioBase * 0.75;
            } else {
                precioFinal = precioBase;
            }

            codigosBoletosVendidos[i][baseIndex + currentCounter] = codigoBoleto;
            edadesBoletos[i][baseIndex + currentCounter] = edad;
            preciosBoletos[i][baseIndex + currentCounter] = precioFinal;

            currentCounter++;
            System.out.printf("Boleto %d: %s | Precio: %.2f\n", j + 1, codigoBoleto, precioFinal);
        }

        switch (opcionSector) {
            case 1:
                capacidadesSombra[i] -= cantidad;
                contadorBoletosSombra[i] = currentCounter;
                break;
            case 2:
                capacidadesSol[i] -= cantidad;
                contadorBoletosSol[i] = currentCounter;
                break;
            case 3:
                capacidadesPalco[i] -= cantidad;
                contadorBoletosPalco[i] = currentCounter;
                break;
        }

        System.out.println("Venta completada.\n");
    }

    // ----------------------
    // REPORTE ESTADISTICAS EN TIEMPO REAL (CORREGIDO)
    // ----------------------
    public static void reporteAsistenciaPorSectorYTipo() {
        if (contadorPartidos == 0) {
            System.out.println("No hay partidos registrados.");
            return;
        }

        System.out.println("\n--- ESTADISTICAS EN TIEMPO REAL ---");
        for (int i = 0; i < contadorPartidos; i++) {
            System.out.println("\nPartido: " + descripciones[i]);

            int sombraNinos = 0, sombraAdultos = 0, sombraMayores = 0;
            int solNinos = 0, solAdultos = 0, solMayores = 0;
            int palcoNinos = 0, palcoAdultos = 0, palcoMayores = 0;

            double ingresoTotal = 0;

            // Procesar boletos de Sombra
            for (int j = 0; j < contadorBoletosSombra[i]; j++) {
                int edad = edadesBoletos[i][j];
                ingresoTotal += preciosBoletos[i][j];
                if (edad <= 12) sombraNinos++;
                else if (edad < 65) sombraAdultos++;
                else sombraMayores++;
            }
            
            // Procesar boletos de Sol
            int solStartIndex = contadorBoletosSombra[i];
            for (int j = 0; j < contadorBoletosSol[i]; j++) {
                int edad = edadesBoletos[i][solStartIndex + j];
                ingresoTotal += preciosBoletos[i][solStartIndex + j];
                if (edad <= 12) solNinos++;
                else if (edad < 65) solAdultos++;
                else solMayores++;
            }
            
            // Procesar boletos de Palco
            int palcoStartIndex = contadorBoletosSombra[i] + contadorBoletosSol[i];
            for (int j = 0; j < contadorBoletosPalco[i]; j++) {
                int edad = edadesBoletos[i][palcoStartIndex + j];
                ingresoTotal += preciosBoletos[i][palcoStartIndex + j];
                if (edad <= 12) palcoNinos++;
                else if (edad < 65) palcoAdultos++;
                else palcoMayores++;
            }

            System.out.println("Sector Sombra -> Ninios: " + sombraNinos + ", Adultos: " + sombraAdultos + ", 3ra Edad: " + sombraMayores);
            System.out.println("Sector Sol -> Ninios: " + solNinos + ", Adultos: " + solAdultos + ", 3ra Edad: " + solMayores);
            System.out.println("Sector Palco -> Ninios: " + palcoNinos + ", Adultos: " + palcoAdultos + ", 3ra Edad: " + palcoMayores);

            int totalNinos = sombraNinos + solNinos + palcoNinos;
            int totalAdultos = sombraAdultos + solAdultos + palcoAdultos;
            int totalMayores = sombraMayores + solMayores + palcoMayores;
            int totalGeneral = totalNinos + totalAdultos + totalMayores;

            System.out.println("\n>>> Totales del partido:");
            System.out.println("Ninios: " + totalNinos + " | Adultos: " + totalAdultos + " | 3ra Edad: " + totalMayores);
            System.out.println("TOTAL GENERAL: " + totalGeneral);
            System.out.printf("INGRESO TOTAL RECAUDADO: L. %.2f\n", ingresoTotal);

            if (totalGeneral > 0) {
                double pctNinos = (totalNinos * 100.0) / totalGeneral;
                double pctAdultos = (totalAdultos * 100.0) / totalGeneral;
                double pctMayores = (totalMayores * 100.0) / totalGeneral;

                System.out.println("\n>>> Porcentajes de asistencia:");
                System.out.printf("Ninios: %.2f%%\n", pctNinos);
                System.out.printf("Adultos: %.2f%%\n", pctAdultos);
                System.out.printf("3ra Edad: %.2f%%\n", pctMayores);
            } else {
                System.out.println("\nNo hay asistencia registrada para este partido.");
            }
        }
    }
}//fin de class