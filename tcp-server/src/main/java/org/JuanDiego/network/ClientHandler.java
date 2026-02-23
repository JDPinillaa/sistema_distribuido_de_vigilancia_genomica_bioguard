package org.JuanDiego.network;

import org.JuanDiego.business.IMessageProcessor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Atiende una conexion TCP de un cliente y procesa un unico mensaje.
 * @author Juan Diego
 * @since 20260223
 * @version 1.0
 *
 * Lee un String con {@link DataInputStream#readUTF()}, lo entrega al
 * {@link IMessageProcessor}, y devuelve la respuesta con
 * {@link DataOutputStream#writeUTF(String)}.
 *
 * El procesamiento se sincroniza con un lock estatico para evitar concurrencia
 * simultanea sobre el procesador compartido.
 */
public class ClientHandler implements Runnable {
    private static final Object PROCESSOR_LOCK = new Object();
    private final Socket clientSocket;
    private final IMessageProcessor processor;

    /**
     * Crea un handler para un cliente.
     */
    public ClientHandler(Socket clientSocket, IMessageProcessor processor) {
        this.clientSocket = clientSocket;
        this.processor = processor;
    }

    @Override
    /**
     * Ejecuta la atencion del cliente.
     *
     * Lee un mensaje, lo procesa y responde. Cierra el socket y streams
     * automaticamente al finalizar.
     */
    public void run() {
        try (Socket socket = clientSocket;
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            String clientMessage = in.readUTF();
            String response;
            synchronized (PROCESSOR_LOCK) {
                response = processor.process(clientMessage);
            }
            out.writeUTF(response);
            out.flush();
        } catch (IOException e) {
            System.err.println("[ClientHandler] Error con cliente: " + e.getMessage());
        }
    }
}
