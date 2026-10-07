package service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Classe Comunicador (vista em aula): concentra o envio e o recebimento
 * de pacotes UDP, usada tanto pelo cliente quanto pelo servidor.
 */
public class Comunicador {

    public static final int PORTA_SERVIDOR = 1234;
    public static final String HOST_SERVIDOR = "localhost";
    private static final int TAMANHO_BUFFER = 1024;

    public static DatagramPacket montaMensagem(String mensagem, InetAddress endereco, int porta) {
        // UTF-8 para nomes com acento chegarem corretos do outro lado
        byte[] buffer = mensagem.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(buffer, buffer.length, endereco, porta);
    }

    public static DatagramPacket montaMensagem(String mensagem, String host, int porta) throws UnknownHostException {
        return montaMensagem(mensagem, InetAddress.getByName(host), porta);
    }

    public static void enviaMensagem(DatagramSocket socket, DatagramPacket pacote) throws java.io.IOException {
        socket.send(pacote);
    }

    /**
     * Bloqueia até chegar um pacote. Se o socket tiver timeout configurado
     * e nada chegar a tempo, lança SocketTimeoutException.
     */
    public static DatagramPacket recebeMensagem(DatagramSocket socket) throws java.io.IOException {
        DatagramPacket pacote = new DatagramPacket(new byte[TAMANHO_BUFFER], TAMANHO_BUFFER);
        socket.receive(pacote);
        return pacote;
    }

    public static String textoDo(DatagramPacket pacote) {
        return new String(pacote.getData(), 0, pacote.getLength(), StandardCharsets.UTF_8).trim();
    }

    /** Envia uma mensagem ao servidor e espera a resposta (com timeout). */
    public static String requisicao(String mensagem, int timeoutMs) throws java.io.IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(timeoutMs);
            enviaMensagem(socket, montaMensagem(mensagem, HOST_SERVIDOR, PORTA_SERVIDOR));
            return textoDo(recebeMensagem(socket));
        } catch (SocketTimeoutException e) {
            throw new java.io.IOException("Servidor não respondeu em " + timeoutMs / 1000 + "s. Ele está rodando?");
        }
    }
}
