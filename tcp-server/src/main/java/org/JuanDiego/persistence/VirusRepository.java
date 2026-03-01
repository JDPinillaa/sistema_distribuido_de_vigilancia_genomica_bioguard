package org.JuanDiego.persistence;

import org.JuanDiego.exceptions.InvalidFastaFormatException;
import org.JuanDiego.models.Virus;
import org.JuanDiego.parsers.VirusFastaParser;

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
public class VirusRepository implements IVirusRepository {

    /**
     * Ruta del directorio de virus
     */
    private static final Path virusDirectory = Paths.get("data/viruses");


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
    public synchronized void saveVirus(String filepath) throws IOException, InvalidFastaFormatException{
        File file = new File(filepath);
        if(!file.exists()){
            throw new FileNotFoundException("No se encontro el archivo en la ruta indicada");
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String firstLine = br.readLine();
            String scndLine = br.readLine();

            Virus v = VirusFastaParser.parse(firstLine, scndLine);
            File toDirectory = new File(virusDirectory + "/" + v.getName() + ".fasta");
            Files.copy(file.toPath(), toDirectory.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Metodo que lee un archivo fasta desde una ruta local, lo copia al repositorio y retorna el virus parseado
     */
    @Override
    public synchronized Virus saveVirusFromPath(String filepath) throws IOException, InvalidFastaFormatException{
        File file = new File(filepath);
        if(!file.exists()){
            throw new FileNotFoundException("No se encontro el archivo en la ruta indicada");
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String firstLine = br.readLine();
            String scndLine = br.readLine();

            Virus v = VirusFastaParser.parse(firstLine, scndLine);
            File toDirectory = new File(virusDirectory + "/" + v.getName() + ".fasta");
            Files.copy(file.toPath(), toDirectory.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return v;
        }
    }

    /**
     * Metodo que guarda un Virus en el directorio de virus como archivo fasta
     */
    @Override
    public synchronized void saveVirus(Virus virus) throws IOException {
        File toDirectory = new File(virusDirectory + "/" + virus.getName() + ".fasta");
        String[] fastaLines = VirusFastaParser.toFastaLines(virus);
        try (FileWriter fw = new FileWriter(toDirectory)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println(fastaLines[0]);
            out.println(fastaLines[1]);
        }
    }


    /**
     * Carga el catalogo de virus persistidos en el servidor.
     * Lee todos los archivos .fasta del directorio de virus y los parsea a instancias de Virus.
     * Si un archivo no es valido, se omite y se continua con los demas.
     */
    @Override
    public synchronized List<Virus> catalog(){
        List<Virus> catalog = new ArrayList<>();
        File directory = new File(virusDirectory.toUri());

        File[] files = directory.listFiles(((dir, name) -> name.endsWith(".fasta")));

        if(files !=null){
            for(File file : files){
                try(BufferedReader br = new BufferedReader(new FileReader(file))){
                    String firstLine = br.readLine();
                    String scndLine = br.readLine();
                    if(firstLine != null && scndLine != null){
                        catalog.add(VirusFastaParser.parse(firstLine, scndLine));
                    }
                } catch (IOException | InvalidFastaFormatException e) {
                    System.out.println("No se pudo cargar el virus");;
                }
            }
        }
        return catalog;
    }











}
