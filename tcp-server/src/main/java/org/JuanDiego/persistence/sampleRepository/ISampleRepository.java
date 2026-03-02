package org.JuanDiego.persistence.sampleRepository;

import org.JuanDiego.models.DNASample;

import java.io.IOException;
import java.util.List;

/**
 * Interfaz del repositorio de muestras
 * @author Juan Diego
 * @since 20260227
 * @version 1.0
 */
public interface ISampleRepository {
    void saveSample(DNASample sample) throws IOException;
    List<DNASample> obtainHistory(String id) throws IOException;
}
