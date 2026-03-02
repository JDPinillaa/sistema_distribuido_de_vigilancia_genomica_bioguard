package org.JuanDiego.business.commands.consultPatient;

import org.JuanDiego.business.commands.ICommandHandler;
import org.JuanDiego.models.Patient;
import org.JuanDiego.persistence.patientRepository.IPatientRepository;


/**
 * Handler del comando CONSULTPATIENT, implementa ICommandHandler
 *
 * @author Juan Diego
 * @since 20260301
 * @version 1.0
 */
public class ConsultPatientHandler implements ICommandHandler {
    private final IPatientRepository patientRepository;

    public ConsultPatientHandler(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Contiene la logica que se maneja con el payload del mensaje del cliente
     */
    @Override
    public String handle(String payload) {
        if (payload == null || payload.isEmpty()) {
            return "Debe ingresar el documento del paciente";
        }
        try {
            Patient patient = patientRepository.consultPatient(payload);
            if (patient == null) {
                return "El paciente con el numero de identificacion ingresado no existe en el sistema";
            }
            return "Paciente encontrado: " + patient.toCSV();
        } catch (Exception e) {
            return "Hubo un error: " + e.getMessage();
        }
    }
}
