package org.JuanDiego.persistence;

import org.JuanDiego.exceptions.DuplicatedPatientException;
import org.JuanDiego.models.Patient;

import java.io.IOException;

/**
 * Interfaz del repositorio de pacientes
 *
 * @author Juan Diego
 * @since 20260227
 * @version 1.0
 */
public interface IPatientRepository {
    Patient consultPatient(String id);
    boolean patientExists(String id);
    void savePatient(Patient patient) throws DuplicatedPatientException, IOException;
}
