package org.JuanDiego.business.commands.registerPatient;

import org.JuanDiego.business.commands.ICommandHandler;
import org.JuanDiego.models.Patient;
import org.JuanDiego.parsers.PatientCsvParser;
import org.JuanDiego.persistence.patientRepository.IPatientRepository;

/**
 * Handler del comando LOADANDANALIZESAMPLE, implementa ICommandHandler
 *
 *  @author Juan Diego
 *  @since 20260301
 *  @version 1.0
 */
public class RegisterPatientHandler implements ICommandHandler {
    private final IPatientRepository patientRepository;

    public RegisterPatientHandler(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    /**
     * Contiene la logica que se maneja con el payload del mensaje del cliente
     */
    @Override
    public String handle(String payload) {
        if (payload == null || payload.isEmpty()) {
            return "Debe ingresar la informacion del paciente";
        }
        try {
            Patient patient = PatientCsvParser.parseLine(payload);
            patientRepository.savePatient(patient);
            return "Paciente registrado con exito";
        } catch (Exception e) {
            return "Hubo un error: " + e.getMessage();
        }
    }
}
