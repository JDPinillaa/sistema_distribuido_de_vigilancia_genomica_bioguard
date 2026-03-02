package org.JuanDiego.business;

import org.JuanDiego.models.DNASample;
import org.JuanDiego.models.Diagnostic;
import org.JuanDiego.models.Virus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase es la encaragada de la logica de negocio principal de las operaciones con cruces de ADN
 */
public class DNAAnalizer {


    /**
     * Metodo que compara la muestra de ADN del paciente con la lista de virus para encontrar coincidencias en la secuencia genetica, genera un diagnostico por cada virus encontrado y retorna una lista que contiene cada diagnostico
     */
    public List<Diagnostic> sampleAnalisis(DNASample sample, List<Virus> virusList){
        List<Diagnostic> results = new ArrayList<>();
        String pacientSequence = sample.getSequence();

        for(Virus virus : virusList){
            String virusSequence = virus.getGeneticSequence();
            int i = pacientSequence.indexOf(virusSequence);

            while(i >= 0){
                int lastPosition = i + virusSequence.length() - 1;
                int startPosition = i + 1;
                int endPosition = lastPosition + 1;
                results.add(new Diagnostic(virus, startPosition, endPosition));
                i = pacientSequence.indexOf(virusSequence, i+1);
            }
        }
        return results;
    }

    /**
     * Metodo que toma los resultados del analisis y los pasa a un csv en la carpeta del paciente
     */
    public void generateDiagnosticReportCSV(DNASample sample, List<Diagnostic>results) throws IOException{

        String folderPath = "data/samples/" + sample.getPatientId();
        File csvFile = new File(folderPath, "diagnostic_"+ sample.getDate().replace("/", "-")+".csv");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))){
            for(Diagnostic diagnostic : results){
                bw.write(diagnostic.toCSV());
                bw.newLine();
            }
        }
    }

    /**
     * Metodo que busca posibles mutaciones analizando la muestra actual con el historial de muestras del paciente, retorna una lista de mensajes en Strings indicando donde hubieron cambios
     */
    public List<String> detectMutations(DNASample currentSample, List<DNASample> history){
        List<String> mutationsReport = new ArrayList<>();
        String currentDna = currentSample.getSequence();

        for (DNASample previousSample : history){
            if(previousSample.getDate().equals(currentSample.getDate())){
                continue;
            }
            String previousDna = previousSample.getSequence();
            mutationsReport.addAll(compareStrings(currentDna, previousDna, previousSample.getDate()));


        }
        return mutationsReport;
    }

    /**
     * Metodo auxiliar que compara dos cadenas caracter por caracter para encontrar bloques diferentes y marca si esta dentro de posibles mutaciones con una bandera
     */
    private List<String> compareStrings(String current, String previous, String previousDate){
        List<String> diferences = new ArrayList<>();
        int minimumLength = Math.min(current.length(), previous.length());
        boolean inMutation = false;
        int startOfMutation = -1;

        for(int i = 0; i<minimumLength; i++){
            if(current.charAt(i) != previous.charAt(i)){
                if(!inMutation){
                    inMutation = true;
                    startOfMutation = i;
                }
            }else {
                if(inMutation){
                    inMutation = false;
                    diferences.add("Mutación detectada respecto a "+ previousDate + " inicio en "+startOfMutation+", Fin en "+ (i-1));

                }
            }
        }

        if(inMutation){
            diferences.add("Mutación detectada respecto a "+ previousDate+ ": inicio en "+startOfMutation+ ", fin en "+ (minimumLength-1));

        }

        return diferences;
    }
}
