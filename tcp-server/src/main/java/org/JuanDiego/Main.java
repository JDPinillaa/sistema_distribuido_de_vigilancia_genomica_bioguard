package org.JuanDiego;

import org.JuanDiego.business.DNAAnalizer;
import org.JuanDiego.business.ReportGenerator;
import org.JuanDiego.business.SystemProcessor;
import org.JuanDiego.common.PropertiesManager;
import org.JuanDiego.network.SSLTCPServer;
import org.JuanDiego.network.TCPConfig;
import org.JuanDiego.persistence.patientRepository.PatientRepository;
import org.JuanDiego.persistence.sampleRepository.SampleRepository;
import org.JuanDiego.persistence.virusRepository.VirusRepository;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");

        try{
            PatientRepository patientRepository = new PatientRepository();
            SampleRepository sampleRepository = new SampleRepository();
            VirusRepository virusRepository = new VirusRepository();

            DNAAnalizer dnaAnalizer = new DNAAnalizer();
            ReportGenerator reportGenerator = new ReportGenerator();

            SystemProcessor systemProcessor = new SystemProcessor(patientRepository, sampleRepository, virusRepository, dnaAnalizer, reportGenerator);

            System.out.println("Cargando configuracion del servidor");

            TCPConfig config = new TCPConfig(new PropertiesManager("aplication.properties"));
            SSLTCPServer server = new SSLTCPServer(config, systemProcessor);
            server.start();
        } catch (Exception e) {
            System.err.println("Error critico: " + e.getMessage());
        }
    }
}
