package org.JuanDiego;

import org.JuanDiego.common.PropertiesManager;
import org.JuanDiego.network.SSLTCPClient;
import org.JuanDiego.network.TCPConfig;

import java.util.Scanner;


/**
 * Clase Main que va a ser tratada como el menú de opciones del cliente
 * @author Juan Diego
 * @since 20260223
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando cliente...");

        TCPConfig config = new TCPConfig(new PropertiesManager("aplication.properties"));
        SSLTCPClient client = new SSLTCPClient(config);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                String option = scanner.nextLine().trim();
                switch (option) {
                    case "1" -> handleRegisterPatient(scanner, client);
                    case "2" -> handleConsultPatient(scanner, client);
                    case "3" -> handleLoadVirus(scanner, client);
                    case "4" -> handleLoadVirusFromPath(scanner, client);
                    case "5" -> send(client, "SHOWVIRUSCATALOG", "");
                    case "6" -> handleAnalyzeSample(scanner, client);
                    case "0" -> {
                        System.out.println("Saliendo del cliente.");
                        return;
                    }
                    default -> System.out.println("Opcion no valida.");
                }
            }
        }
    }


    /**
     * Pinta el menu del sistema del cliente en consola
     */
    private static void printMenu() {
        System.out.println();

        System.out.println("""
                       Sistema Distribuido de Vigilancia Genomica Bioguard
                
                Bienvenido a la implementacion de este sistema de Juan Diego Pinilla
                
                Que operacion desea hacer en este momento?, esta implementacion tiene
                las siguientes opciones:
                
                1. Registrar paciente
                2. Consultar paciente
                3. Cargar virus manualmente, poniendo los virus en el formato:
                (>nombreVirus | nivelInfeccciosidad)
                4. Cargar virus desde un archivo fasta
                5. Mostrar el catalogo de los virus que usa este sistema
                6. Cargar y analizar una muestra (.fasta)
                0. Salir
                
                
                """);
        System.out.print("Seleccione una opcion: ");
    }


    /**
     * Maneja el caso de que el cliente quiera registrar un paciente
     */
    private static void handleRegisterPatient(Scanner scanner, SSLTCPClient client) {
        System.out.println("Ingrese la linea CSV del paciente:");
        System.out.println("Formato: id,nombre,apellido,edad,email,sexo(M/F),ciudad,pais");
        String csvLine = scanner.nextLine().trim();
        send(client, "REGISTERPATIENT", csvLine);
    }

    /**
     * Maneja el caso de que el cliente desee consultar un paciente
     */
    private static void handleConsultPatient(Scanner scanner, SSLTCPClient client) {
        System.out.print("Ingrese el documento del paciente: ");
        String id = scanner.nextLine().trim();
        send(client, "CONSULTPATIENT", id);
    }

    /**
     * Maneja el caso de que el cliente quiera ingresar un virus manualmente
     */
    private static void handleLoadVirus(Scanner scanner, SSLTCPClient client) {
        String fasta = readMultiline(scanner, "Pegue el contenido FASTA y termine con una linea END:");
        send(client, "LOADVIRUS", fasta);
    }

    /**
     *Maneja el caso de que el cliente quiera ingresar un virus adjuntando un fasta
     */
    private static void handleLoadVirusFromPath(Scanner scanner, SSLTCPClient client) {
        System.out.print("Ingrese la ruta del archivo FASTA: ");
        String path = scanner.nextLine().trim();
        send(client, "LOADVIRUSFROMFASTA", path);
    }

    /**
     * aneja el caso de que el cliente quiera cargar una muestra
     */
    private static void handleAnalyzeSample(Scanner scanner, SSLTCPClient client) {
        System.out.print("Ingrese la ruta del archivo FASTA de la muestra: ");
        String path = scanner.nextLine().trim();
        send(client, "LOADANDANALIZESAMPLE", path);
    }

    /**
     * Envia el mensaje al servidor y muestra su respuesta
     */
    private static void send(SSLTCPClient client, String command, String payload) {
        String message = (payload == null || payload.isBlank()) ? command : command + "::" + payload;
        String response = client.sendMessage(message);
        System.out.println("Respuesta del servidor:");
        System.out.println(response);
    }

    /**
     *Lee multiples lineas desde consola hasta que se ponga END
     */
    private static String readMultiline(Scanner scanner, String prompt) {
        System.out.println(prompt);
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if ("END".equals(line)) {
                break;
            }
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(line);
        }
        return sb.toString();
    }
}
