package org.JuanDiego.business.commands;

import org.JuanDiego.business.DNAAnalizer;
import org.JuanDiego.business.ReportGenerator;
import org.JuanDiego.models.DNASample;
import org.JuanDiego.models.Diagnostic;
import org.JuanDiego.models.Patient;
import org.JuanDiego.models.Virus;
import org.JuanDiego.persistence.IPatientRepository;
import org.JuanDiego.persistence.ISampleRepository;
import org.JuanDiego.persistence.IVirusRepository;

import java.util.List;


/**
 * Handler del comando LOADANDANALIZESAMPLE, implementa ICommandHandler
 *
 *  @author Juan Diego
 *  @since 20260301
 *  @version 1.0
 */
public class LoadAndAnalyzeSampleHandler implements ICommandHandler {
    private final ISampleRepository sampleRepository;
    private final IVirusRepository virusRepository;
    private final DNAAnalizer dnaAnalizer;
    private final ReportGenerator reportGenerator;
    private final IPatientRepository patientRepository;

    public LoadAndAnalyzeSampleHandler(ISampleRepository sampleRepository, IVirusRepository virusRepository, DNAAnalizer dnaAnalizer, ReportGenerator reportGenerator, IPatientRepository patientRepository) {
        this.sampleRepository = sampleRepository;
        this.virusRepository = virusRepository;
        this.dnaAnalizer = dnaAnalizer;
        this.reportGenerator = reportGenerator;
        this.patientRepository = patientRepository;
    }


    /**
     * Contiene la logica que se maneja con el payload del mensaje del cliente
     */
    @Override
    public String handle(String payload) {
        if (payload == null || payload.isEmpty()) {
            return "Debe enviar la ruta del archivo fasta de la muestra";
        }
        try {
            DNASample sample = sampleRepository.saveSampleFromPath(payload);
            List<Virus> virusList = virusRepository.catalog();
            if (virusList.isEmpty()) {
                return "No hay virus registrados en el catalogo";
            }

            List<Diagnostic> diagnostics = dnaAnalizer.sampleAnalisis(sample, virusList);
            dnaAnalizer.generateDiagnosticReportCSV(sample, diagnostics);

            List<DNASample> history = sampleRepository.obtainHistory(sample.getPatientId());
            List<String> mutations = dnaAnalizer.detectMutations(sample, history);

            Patient patient = patientRepository.consultPatient(sample.getPatientId());
            if (patient != null) {
                reportGenerator.evaluateNRecordHighRisk(patient, diagnostics);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Muestra registrada y analizada: ").append(sample.getPatientId()).append(" | ").append(sample.getDate());
            sb.append("\nDiagnosticos: ").append(diagnostics.size());
            if (!mutations.isEmpty()) {
                sb.append("\nMutaciones:");
                for (String m : mutations) {
                    sb.append("\n").append(m);
                }
            } else {
                sb.append("\nMutaciones: no detectadas");
            }
            if (patient == null) {
                sb.append("\nPaciente no registrado, no se evaluo alto riesgo");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Hubo un error: " + e.getMessage();
        }
    }
}
