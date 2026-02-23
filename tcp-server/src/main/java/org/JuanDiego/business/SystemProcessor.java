package org.JuanDiego.business;

import org.JuanDiego.models.DNASample;
import org.JuanDiego.models.Diagnostic;
import org.JuanDiego.models.Patient;
import org.JuanDiego.models.Virus;
import org.JuanDiego.parsers.PatientCsvParser;
import org.JuanDiego.parsers.SampleFastaParser;
import org.JuanDiego.parsers.VirusFastaParser;
import org.JuanDiego.persistence.PatientRepository;
import org.JuanDiego.persistence.SampleRepository;
import org.JuanDiego.persistence.VirusRepository;

import java.util.List;

/**
 * Esta clase procesa los mensajes entrantes del cliente, y maneja la logica de la aplicacíon con los processors
 * @author Juan Diego
 * @since 20260223
 * @version 1.0
 */
public class SystemProcessor implements IMessageProcessor {



    ////// Inyección de dependencias
    private final PatientRepository patientRepository;
    private final SampleRepository sampleRepository;
    private final VirusRepository virusRepository;
    private final DNAAnalizer dnaAnalizer;
    private final ReportGenerator reportGenerator;
    private final PatientCsvParser patientCsvParser;
    private final SampleFastaParser sampleFastaParser;
    private final VirusFastaParser virusFastaParser;

    public SystemProcessor(PatientRepository patientRepository, SampleRepository sampleRepository, VirusRepository virusRepository, DNAAnalizer dnaAnalizer, ReportGenerator reportGenerator, PatientCsvParser patientCsvParser, SampleFastaParser sampleFastaParser, VirusFastaParser virusFastaParser) {
        this.patientRepository = patientRepository;
        this.sampleRepository = sampleRepository;
        this.virusRepository = virusRepository;
        this.dnaAnalizer = dnaAnalizer;
        this.reportGenerator = reportGenerator;
        this.patientCsvParser = patientCsvParser;
        this.sampleFastaParser = sampleFastaParser;
        this.virusFastaParser = virusFastaParser;
    }


    /**
     * Maneja la logica de los comandos del sistema
     */
    @Override
    public String process(String message) {
        try{
            String[] parts = message.split("::", 2);
            String command = parts[0].trim().toUpperCase();
            String payload = parts.length > 1 ? parts[1].trim() : "";

            switch (command){
                case "REGISTERPATIENT":
                    if(payload.isEmpty()){
                        return "Debe ingresar la informacion del paciente";
                    }
                    return processPatientRegistration(payload);
                case "CONSULTPATIENT":
                    if(payload.isEmpty()){
                        return "Debe ingresar el documento del paciente";
                    }
                    return processPatientConsult(payload);

                case "LOADVIRUS":
                    if (payload.isEmpty()) {
                        return "Debe enviar el contenido fasta del virus";
                    }
                    return processVirusLoad(payload);

                case "LOADVIRUSFROMFASTA":
                    if (payload.isEmpty()) {
                        return "Debe enviar la ruta del archivo fasta";
                    }
                    return processVirusLoadFromPath(payload);

                case "SHOWVIRUSCATALOG":
                    return processVirusCatalog();

                case "LOADANDANALIZESAMPLE":
                    if (payload.isEmpty()) {
                        return "Debe enviar la ruta del archivo fasta de la muestra";
                    }
                    return processSampleAnalisis(payload);

                default:
                    return "El comando no fue reconocido";
            }
        }catch (Exception e){
            return "Hubo un error: " + e.getMessage();
        }
    }



    //////processors



    private String processPatientRegistration(String csvLine) throws Exception{
        Patient patient = patientCsvParser.parseLine(csvLine);
        patientRepository.savePatient(patient);
        return "Paciente registrado con exito";
    }

    private String processPatientConsult(String id){
        Patient patient = patientRepository.consultPatient(id);
        if(patient == null){
            return "El paciente con el numero de identificacion ingresado no existe en el sistema";

        }
        return "Paciente encontrado: " + patient.toCSV();
    }

    private String processVirusLoad(String fastaContent) throws Exception{
        Virus virus = VirusFastaParser.parse(fastaContent);
        virusRepository.saveVirus(virus);
        return "Virus registrado con exito";
    }

    private String processVirusLoadFromPath(String filepath) throws Exception{
        Virus virus = virusRepository.saveVirusFromPath(filepath);
        return "Virus registrado con exito: " + virus.getName();
    }

    private String processSampleAnalisis(String filepath) throws Exception{
        DNASample sample = sampleRepository.saveSampleFromPath(filepath);
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
    }

    private String processVirusCatalog(){
        StringBuilder sb = new StringBuilder();
        for (Virus v : virusRepository.catalog()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(v.getName()).append(" | ").append(v.getInfectionLevel().getDescription()).append(" | ").append(v.getGeneticSequence().length());
        }
        if (sb.length() == 0) {
            return "Catalogo de virus vacio";
        }
        return sb.toString();
    }


}
