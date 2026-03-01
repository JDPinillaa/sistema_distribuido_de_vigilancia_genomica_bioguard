package org.JuanDiego.business.commands;

import org.JuanDiego.models.Virus;
import org.JuanDiego.persistence.IVirusRepository;

/**
 * Handler del comando SHOWVIRUSCATALOG, implementa ICommandHandler
 *
 *  @author Juan Diego
 *  @since 20260301
 *  @version 1.0
 */
public class ShowVirusCatalogHandler implements ICommandHandler {
    private final IVirusRepository virusRepository;

    public ShowVirusCatalogHandler(IVirusRepository virusRepository) {
        this.virusRepository = virusRepository;
    }


    /**
     * Contiene la logica que se maneja con el payload del mensaje del cliente
     */
    @Override
    public String handle(String payload) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Virus v : virusRepository.catalog()) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(v.getName()).append(" | ").append(v.getInfectionLevel().getDescription()).append(" | ").append(v.getGeneticSequence().length());
            }
            if (sb.length() == 0) {
                return "Catalogo de virus vacio";
            }
            return sb.toString();
        } catch (Exception e) {
            return "Hubo un error: " + e.getMessage();
        }
    }
}
