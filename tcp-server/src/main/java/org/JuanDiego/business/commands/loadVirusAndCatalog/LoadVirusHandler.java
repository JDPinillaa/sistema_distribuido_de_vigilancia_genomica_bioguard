package org.JuanDiego.business.commands.loadVirusAndCatalog;

import org.JuanDiego.business.commands.ICommandHandler;
import org.JuanDiego.models.Virus;
import org.JuanDiego.parsers.VirusFastaParser;
import org.JuanDiego.persistence.virusRepository.IVirusRepository;

/**
 * Handler del comando LOADVIRUS, implementa ICommandHandler
 *
 *  @author Juan Diego
 *  @since 20260301
 *  @version 1.0
 */
public class LoadVirusHandler implements ICommandHandler {
    private final IVirusRepository virusRepository;

    public LoadVirusHandler(IVirusRepository virusRepository) {
        this.virusRepository = virusRepository;
    }


    /**
     * Contiene la logica que se maneja con el payload del mensaje del cliente
     */
    @Override
    public String handle(String payload) {
        if (payload == null || payload.isEmpty()) {
            return "Debe enviar el contenido fasta del virus";
        }
        try {
            Virus virus = VirusFastaParser.parse(payload);
            virusRepository.saveVirus(virus);
            return "Virus registrado con exito";
        } catch (Exception e) {
            return "Hubo un error: " + e.getMessage();
        }
    }
}
