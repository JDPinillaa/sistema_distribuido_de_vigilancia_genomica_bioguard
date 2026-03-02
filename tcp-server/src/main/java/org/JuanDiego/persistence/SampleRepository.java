package org.JuanDiego.persistence;

import org.JuanDiego.exceptions.InvalidFastaFormatException;
import org.JuanDiego.models.DNASample;
import org.JuanDiego.parsers.SampleFastaParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Repositorio de persistencia para las muestras de ADN
 *  @author Juan Diego
 *  @since 20260222
 *  @version 1.1
 */
public class SampleRepository implements ISampleRepository {

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
    @Override
    public synchronized void saveSample(DNASample sample) throws IOException{
        String patientId = sample.getPatientId();
        File patientFolder = new File(sampleDirectory+"/"+patientId);

        if(!patientFolder.exists()){
            Files.createDirectories(patientFolder.toPath());
        }

        String fileName = "sample_"+ sample.getDate().replace("/", "-") +".fasta";
        File toPatientFolder = new File(patientFolder, fileName);

        String[] fastaLines = SampleFastaParser.toFastaLines(sample);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(toPatientFolder));
             PrintWriter out = new PrintWriter(bw)) {
            out.println(fastaLines[0]);
            out.println(fastaLines[1]);
        }
    }

    /**
     * Lee un archivo fasta desde una ruta local, lo parsea y lo guarda en el repositorio
     */
    @Override
    public synchronized DNASample saveSampleFromPath(String filepath) throws IOException, InvalidFastaFormatException{
        File file = new File(filepath);
        if(!file.exists()){
            throw new FileNotFoundException("No se encontro el archivo en la ruta indicada");
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String firstLine = br.readLine();
            String scndLine = br.readLine();

            DNASample sample = SampleFastaParser.parse(firstLine, scndLine);
            saveSample(sample);
            return sample;
        }
    }


    /**
     * Obtiene el historial de muestras de un paciente
     */
    @Override
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
                        history.add(SampleFastaParser.parse(firstLine, scndLine));
                    }
                } catch (InvalidFastaFormatException e) {
                    System.out.println("Formato fasta invalido");
                }
            }
        }
        return history;
    }

}
