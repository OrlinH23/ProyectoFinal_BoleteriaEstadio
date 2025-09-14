package proyectofinal_boleteriaestadio;

import java.util.Scanner;
import java.time.LocalDate;

public class ProyectoFinal_BoleteriaEstadio {

    
    
    //HOLA ESTA ES MI PRIMERA PRUEBA
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
            System.out.println("3. Salir del sistema");
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
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
                    break;
            }
        } while (opcionPrincipal != 3);
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

        System.out.println("\n**** Nuevo Partido ****");

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
        System.out.print("Ingrese el año del partido (AAAA): ");
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
    // FUNCION: VENDER BOLETOS
    // ----------------------
    public static void venderBoletos() {
        int codigoPartido, i, opcionSector, cantidad, edad, dia, mes, anio, contadorSector = 0;
        String sector = "";
        int capacidadDisponible = 0;
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

        switch (opcionSector) {
            case 1:
                sector = "Sombra";
                capacidadDisponible = capacidadesSombra[i];
                precioBase = preciosSombra[i];
                contadorSector = contadorBoletosSombra[i];
                break;
            case 2:
                sector = "Sol";
                capacidadDisponible = capacidadesSol[i];
                precioBase = preciosSol[i];
                contadorSector = contadorBoletosSol[i];
                break;
            case 3:
                sector = "Palco";
                capacidadDisponible = capacidadesPalco[i];
                precioBase = preciosPalco[i];
                contadorSector = contadorBoletosPalco[i];
                break;
            default:
                System.out.println("Sector no valido.");
                return;
        }

        // Cantidad de boletos
        System.out.print("Ingrese cantidad de boletos (maximo 5): ");
        cantidad = entrada.nextInt();
        entrada.nextLine();

        if (cantidad > 5) {
            System.out.println("No se pueden vender mas de 5 boletos por transaccion.");
            return;
        }

        if (cantidad > capacidadDisponible) {
            System.out.println("No hay suficientes boletos disponibles en este sector.");
            return;
        }

        // Edad del comprador principal
        System.out.print("Ingrese edad del comprador principal: ");
        edad = entrada.nextInt();
        entrada.nextLine();

        // Fecha actual y preventa
        hoy = LocalDate.now();
        int fechaInt = fechas[i];
        dia = fechaInt / 1000000;
        mes = (fechaInt / 10000) % 100;
        anio = fechaInt % 10000;
        fechaPartido = LocalDate.of(anio, mes, dia);

        boolean preventa = hoy.isEqual(fechaPartido.minusDays(1));

        // Generar boletos secuenciales y mostrar
        System.out.println("\n--- Boletos Comprados ---");
        for (int j = 0; j < cantidad; j++) {
            int numeroBoleto = contadorSector + 1;
            String codigoBoleto = codigoPartido + "-" + sector.charAt(0) + "-" + numeroBoleto;

            // Aplicar descuento solo al comprador principal (primer boleto)
            if (j == 0 && edad >= 65) {
                precioFinal = precioBase * 0.65;
            } else if (j == 0 && preventa) {
                precioFinal = precioBase * 0.75;
            } else {
                precioFinal = precioBase;
            }

            codigosBoletosVendidos[i][contadorSector] = codigoBoleto;
            contadorSector++;

            System.out.printf("Boleto %d: %s | Precio: %.2f\n", j + 1, codigoBoleto, precioFinal);
        }

        // Actualizar capacidad y contadores
        switch (opcionSector) {
            case 1:
                capacidadesSombra[i] -= cantidad;
                contadorBoletosSombra[i] = contadorSector;
                break;
            case 2:
                capacidadesSol[i] -= cantidad;
                contadorBoletosSol[i] = contadorSector;
                break;
            case 3:
                capacidadesPalco[i] -= cantidad;
                contadorBoletosPalco[i] = contadorSector;
                break;
        }

        System.out.println("Venta completada.\n");
    }

}//fin de class principal