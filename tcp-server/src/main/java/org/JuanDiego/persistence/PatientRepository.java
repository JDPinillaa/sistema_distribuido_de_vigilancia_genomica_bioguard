package org.JuanDiego.persistence;

import org.JuanDiego.exceptions.DuplicatedPatientException;
import org.JuanDiego.models.Patient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Repositorio de persistencia para pacientes basado en archivo CSV.
 *
 * @author Juan Diego
 * @since 20260222
 * @version 1.0
 */
public class PatientRepository {
    private static final Path dataDirectory = Paths.get("data");
    private static final Path patientsFile = dataDirectory.resolve("pacientes.csv");





    /**
     * Obtiene la ruta del archivo de pacientes, asegurando la existencia del directorio base
     */
    public static Path getPacientsFile() {
        ensureDataDirectory();
        return patientsFile;
    }

    /**
     * Crea el directorio de datos si no existe.
     */
    private static void ensureDataDirectory() {
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear la carpeta de datos: " + dataDirectory, e);
        }
    }

    /**
     * Busca un paciente por su identificacion en el archivo CSV y muestra su información
     */
    public synchronized Patient consultPatient(String id) {
        File file = new File(patientsFile.toUri());
        if(!file.exists()){
            return null;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;

            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                if(data.length>0 && data[0].equals(id)){
                    return Patient.fromCSV(line);
                }

            }

        }catch (IOException e){
            System.out.println("Error al leer el archivo de pacientes: "+e.getMessage());
        }
        return null;
    }

    /**
     * Verifica si existe un paciente con el id dado.
     */
    public synchronized boolean patientExists(String id){
        return consultPatient(id) != null;
    }

    /**
     * Guarda un paciente en el archivo CSV.
     */
    public synchronized void savePatient(Patient patient) throws DuplicatedPatientException, IOException {
        if (patientExists(patient.getId())) {
            throw new DuplicatedPatientException("El paciente ya esta registrado en el sistema");
        }

        ensureDataDirectory();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientsFile.toFile(), true));
             PrintWriter out = new PrintWriter(bw)) {
            out.println(patient.toCSV());
        }
    }
}
