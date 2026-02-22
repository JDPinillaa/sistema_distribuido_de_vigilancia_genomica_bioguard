package org.JuanDiego.persistence;

import org.JuanDiego.exceptions.InvalidFastaFormatException;
import org.JuanDiego.models.Virus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


/**
 * Repositorio de persistencia para los virus
 * @author Juan Diego
 * @since 20260222
 * @version 1.0
 */
public class VirusRepository {
    private static final Path virusDirectory = Paths.get("data/virus");


    /**
     * Metodo constructor
     *
     * poniendo @ensureVirusDirectory aqui se asegura que cuando se arranque el servidor ya exista el directorio de los virus
     */
    public VirusRepository() {
        ensureVirusDirectory();
    }

    /**
     * Crea el directorio de los virus si no existe
     */
    private static void ensureVirusDirectory(){
        try{
            Files.createDirectories(virusDirectory);
        }catch(IOException e){
            throw new IllegalStateException("No se pudo crear el directorio de alamcenamiento de virus" + e.getMessage());
        }
    }


    /**
     * Metodo que lee un archivo fasta desde una ruta local
     */
    public synchronized void virusRegister(String filepath) throws IOException, InvalidFastaFormatException{
        File file = new File(filepath);
        if(!file.exists()){
            throw new FileNotFoundException("No se encontro el archivo en la ruta indicada");
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String firstLine = br.readLine();
            String scndLine = br.readLine();

            if(firstLine == null || scndLine == null){
                throw new InvalidFastaFormatException("El archivo fasta esta incompleto");
            }

            try{
                Virus v = Virus.fromFasta(firstLine, scndLine);
                File toDirectory = new File(virusDirectory + "/" + v.getName() + ".fasta");
                Files.copy(file.toPath(), toDirectory.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }catch(IllegalArgumentException e){
                throw new InvalidFastaFormatException("Error: "+e.getMessage());
            }
        }
    }


    /**
     * Metodo que lee todos los archivos .fasta que hay en el servidor y los convierte en instancias de virus
     */
    public synchronized List<Virus> loadCatalog(){
        List<Virus> catalog = new ArrayList<>();
        File directory = new File(virusDirectory.toUri());

        File[] files = directory.listFiles(((dir, name) -> name.endsWith(".fasta")));

        if(files !=null){
            for(File file : files){
                try(BufferedReader br = new BufferedReader(new FileReader(file))){
                    String firstLine = br.readLine();
                    String scndLine = br.readLine();
                    if(firstLine != null && scndLine != null){
                        catalog.add(Virus.fromFasta(firstLine, scndLine));
                    }
                } catch (IOException e) {
                    System.out.println("No se pudo cargar el virus");;
                }
            }
        }
        return catalog;
    }











}
