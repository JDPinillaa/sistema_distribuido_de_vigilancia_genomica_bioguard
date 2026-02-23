package org.JuanDiego.parsers;

import org.JuanDiego.enums.Gender;
import org.JuanDiego.models.Patient;

/**
 * Parser que parsea la informacion de los pacientes, ya sea para moldear un paciente desde la info de un csv, o para a partir de un paciente, crear su linea en el formato del csv
 * @author Juan Diego
 * @since 20260222
 * @version 1.0
 */
public class PatientCsvParser {

    /**
     * Parsea una linea CSV y construye un Paciente
     */
    public static Patient parseLine(String line) {
        String[] data = line.split(",");
        if (data.length != 8) {
            throw new IllegalArgumentException("La linea leida del CSV no esta en el formato correcto.");
        }
        return new Patient(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], Gender.fromAbbreviation(data[5]), data[6], data[7]);
    }

    /**
     * Convierte un Patient a una linea CSV
     */
    public static String toCsv(Patient patient) {
        return String.join(",", patient.getId(), patient.getName(), patient.getSurname(), String.valueOf(patient.getAge()), patient.getEmail(), String.valueOf(patient.getGender().getAbbreviation()), patient.getCity(), patient.getCountry());
    }
}
