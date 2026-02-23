package org.JuanDiego.persistence;

import org.JuanDiego.models.DNASample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Repositorio de persistencia para las muestras de ADN
 */
public class SampleRepository {

    /**
     * Ruta del directorio de muestras de ADN
     */
    private static final Path sampleDirectory = Paths.get("data/samples");


    public SampleRepository() {
        ensureSampleDirectory();
    }


    /**
     * Crea el directorio de las muestras, si no existe
     */
    private static void ensureSampleDirectory(){
        try{
            Files.createDirectories(sampleDirectory);
        }catch(IOException e){
            throw new IllegalStateException("No se pudo crear el directorio: "+e.getMessage());
        }
    }

    /**
     * Metodo que guarda la muestra de un paciente en su carpeta
     */
    public synchronized void saveSample(DNASample sample) throws IOException{
        String patientId = sample.getPatientId();
        File patientFolder = new File(sampleDirectory+"/"+patientId);

        if(!patientFolder.exists()){
            Files.createDirectories(patientFolder.toPath());
        }

        String fileName = "sample_"+ sample.getDate().replace("/", "-")+".fasta";
        File toPatientFolder = new File(patientFolder, fileName);

        try(FileWriter fw = new FileWriter(toPatientFolder)){
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println(">"+patientId+"|"+sample.getDate());
            out.println(sample.getSequence());
        }




    }


    /**
     * Obtiene el historial de muestras de un paciente
     */
    public synchronized List<DNASample> obtainHistory(String id) throws IOException {
        List<DNASample> history = new ArrayList<>();
        File patientFolder = new File(sampleDirectory+"/"+id);

        if(!patientFolder.exists() || !patientFolder.isDirectory()){
            return history;
        }

        File[] files = patientFolder.listFiles((dir, name) -> name.endsWith(".fasta"));

        if(files != null){
            for(File file : files){
                try(BufferedReader br = new BufferedReader(new FileReader(file))){
                    String firstLine = br.readLine();
                    String scndLine = br.readLine();

                    if(firstLine != null && scndLine != null){
                        history.add(DNASample.fromFasta(firstLine, scndLine));
                    }
                    }
                }
            }
        return history;
        }

    }
}
