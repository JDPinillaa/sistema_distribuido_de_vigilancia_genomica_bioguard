package org.JuanDiego.business.commands;

import org.JuanDiego.models.Virus;
import org.JuanDiego.persistence.IVirusRepository;

/**
 * Handler del comando LOADVIRUSFROMFASTA, implementa ICommandHandler
 *
 *  @author Juan Diego
 *  @since 20260301
 *  @version 1.0
 */
public class LoadVirusFromFastaHandler implements ICommandHandler {
    private final IVirusRepository virusRepository;

    public LoadVirusFromFastaHandler(IVirusRepository virusRepository) {
        this.virusRepository = virusRepository;
    }

    /**
     * Contiene la logica que se maneja con el payload del mensaje del cliente
     */
    @Override
    public String handle(String payload) {
        if (payload == null || payload.isEmpty()) {
            return "Debe enviar la ruta del archivo fasta";
        }
        try {
            Virus virus = virusRepository.saveVirusFromPath(payload);
            return "Virus registrado con exito: " + virus.getName();
        } catch (Exception e) {
            return "Hubo un error: " + e.getMessage();
        }
    }
}
