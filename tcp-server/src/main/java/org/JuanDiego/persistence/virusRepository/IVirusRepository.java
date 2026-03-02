package org.JuanDiego.persistence.virusRepository;

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
    List<Virus> catalog();
}
