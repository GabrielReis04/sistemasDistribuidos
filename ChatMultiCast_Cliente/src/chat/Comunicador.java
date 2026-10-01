package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Comunicador {

    // Recebe uma mensagem enviada pelo socket
    public static String recebeMensagem(Socket socket) {
        try {
            DataInputStream leitor =
                    new DataInputStream(socket.getInputStream());

            return leitor.readUTF();

        } catch (IOException e) {
            return null;
        }
    }

    // Envia uma mensagem pelo socket
    public static void enviaMensagem(Socket socket, String mensagem) {
        try {
            DataOutputStream escritor =
                    new DataOutputStream(socket.getOutputStream());

            escritor.writeUTF(mensagem);
            escritor.flush();

        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem.");
        }
    }
}
