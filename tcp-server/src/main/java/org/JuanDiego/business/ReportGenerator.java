package org.JuanDiego.business;

import org.JuanDiego.enums.InfectionLevel;
import org.JuanDiego.models.Diagnostic;
import org.JuanDiego.models.Patient;
import org.JuanDiego.models.Virus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Esta clase es la encargada de generar los reportes en el sistema
 */
public class ReportGenerator {
    private static final Path reportsDirectory = Paths.get("data/reports");
    private static final Path riskReportFile = reportsDirectory.resolve("high_risk_patients.csv");

    /**
     * Metodo constructor, usa @ensureReportsDirectory(), para que apenas se instancie la clase, se genere el directorio
     */
    public ReportGenerator() {
        ensureReportsDirectory();
    }


    /**
     * Metodo auxiliar que crea el directorio de reportes si no existe
     */
    private static void ensureReportsDirectory() {
        try {
            Files.createDirectories(reportsDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear la carpeta de datos: " + reportsDirectory, e);
        }
    }


    /**
     *Metodo que evalua a un paciente y sus diagnosticos haber si se debe agregar en el csv de alto riesgo
     */
    public synchronized void evaluateNRecordHighRisk(Patient patient, List<Diagnostic> diagnostics) throws IOException {
        List<Virus> uniqueViruses = extractUniqueViruses(diagnostics);

        int highLvlCount = 0;
        int midLowLvlCount = 0;

        List<String> highLvlNames = new ArrayList<>();
        List<String> midLowLvlNames = new ArrayList<>();

        for (Virus virus : uniqueViruses) {
            if (virus.getInfectionLevel() == InfectionLevel.Nivel_Alto) {
                highLvlCount++;
                highLvlNames.add(virus.getName());
            } else if (virus.getInfectionLevel() == InfectionLevel.Nivel_medio || virus.getInfectionLevel() == InfectionLevel.Nivel_bajo) {
                midLowLvlCount++;
                midLowLvlNames.add(virus.getName());
            }
        }

        if (highLvlCount > 3) {
            registerOnCSV(patient, uniqueViruses.size(), highLvlCount, midLowLvlNames, highLvlNames);
        }
    }


    /**
     * Metodo que escribe la linea que describe al paciente en el formato csv, en el archivo csv de alto riesgo
     */
    private void registerOnCSV(Patient patient, int totalViruses, int highQty, List<String> midLowNames, List<String> highNames) throws IOException {
        File file = new File(riskReportFile.toUri());
        boolean isNew = !file.exists();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
            if(isNew){
                bw.write("Documento,Cantidad_virus_detectados,Cantidad_altamente_infecciosos,Lista_virus_bajo_medio,Lista_virus_altos");
                bw.newLine();
            }

            String strMidLow = toBracketList(midLowNames);
            String strHigh = toBracketList(highNames);

            String csvLine = String.format("%s,%d,%d,%s,%s",
                    patient.getId(),
                    totalViruses,
                    highQty,
                    strMidLow,
                    strHigh);
            bw.write(csvLine);
            bw.newLine();
        }
    }

    /**
     * Metodo auxiliar que ayuda a extraer los virus unicos y que no hayan duplicados
     */
    private List<Virus> extractUniqueViruses(List<Diagnostic> diagnostics){
        List<Virus> uniques = new ArrayList<>();
        for(Diagnostic diagnostic : diagnostics){
            boolean exists = false;
            for(Virus v : uniques){
                if(v.getName().equals(diagnostic.getDetectedVirus().getName())){
                    exists = true;
                    break;
                }
            }
            if (!exists){
                uniques.add(diagnostic.getDetectedVirus());
            }
        }
        return uniques;
    }

    private String toBracketList(List<String> names) {
        return "[" + String.join(", ", names) + "]";
    }


}
