package org.JuanDiego.persistence;

import org.JuanDiego.exceptions.DuplicatedPatientException;
import org.JuanDiego.models.Patient;
import org.JuanDiego.parsers.PatientCsvParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Repositorio de persistencia para pacientes basado en archivo CSV.
 *
 * @author Juan Diego
 * @since 20260222
 * @version 1.1
 */
public class PatientRepository implements IPatientRepository {

    /**
     * Ruta del directorio de los datos guardados en el servidor
     */
    private static final Path dataDirectory = Paths.get("data");

    /**
     * Nombre del archivo .csv donde se van a guardar los pacientes
     */
    private static final Path patientsFile = dataDirectory.resolve("patients.csv");

    /**
     * Metodo constructor
     *
     * poniendo @ensureDataDirectory se asegura que cuando se instancie este repositorio el directorio de data si exista
     */
    public PatientRepository() {
        ensureDataDirectory();
    }

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
    @Override
    public synchronized Patient consultPatient(String id) {
        File file = new File(patientsFile.toUri());
        if(!file.exists()){
            return null;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;

            while((line = br.readLine()) != null){
                Patient patient = PatientCsvParser.parseLine(line);
                if(patient.getId().equals(id)){
                    return patient;
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
    @Override
    public synchronized boolean patientExists(String id){
        return consultPatient(id) != null;
    }

    /**
     * Guarda un paciente en el archivo CSV.
     */
    @Override
    public synchronized void savePatient(Patient patient) throws DuplicatedPatientException, IOException {
        if (patientExists(patient.getId())) {
            throw new DuplicatedPatientException("El paciente ya esta registrado en el sistema");
        }

        ensureDataDirectory();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientsFile.toFile(), true));
             PrintWriter out = new PrintWriter(bw)) {
            out.println(PatientCsvParser.toCsv(patient));
        }
    }
}
