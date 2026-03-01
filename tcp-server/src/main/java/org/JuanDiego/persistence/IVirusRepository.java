package org.JuanDiego.persistence;

import org.JuanDiego.exceptions.InvalidFastaFormatException;
import org.JuanDiego.models.Virus;

import java.io.IOException;
import java.util.List;


/**
 * Interfaz del repositorio de virus
 *  @author Juan Diego
 *  @since 20260227
 *  @version 1.0
 */
public interface IVirusRepository {
    void saveVirus(Virus virus) throws IOException;
    Virus saveVirusFromPath(String filepath) throws IOException, InvalidFastaFormatException;
    List<Virus> catalog();
}
