package Threads.Java;

import java.io.*;
import java.net.*;

public class SocketCliente {
    private static final String ENDERECO_SERVIDOR = "10.104.12.13";
    private static final int PORTA_SERVIDOR = 12345;
    private static final String COMANDO_SAIDA = "encerrar";

    public static void main(String[] args) {
        new SocketCliente().executar();
    }

    private void executar() {
        System.out.println("Iniciando conexão com o servidor...");

        try (Socket conexao = abrirConexao()) {
            PrintWriter saida = new PrintWriter(conexao.getOutputStream(), true);
            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            BufferedReader entradaConsole = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Conexão estabelecida! Digite suas mensagens (ou '" + COMANDO_SAIDA + "' para sair):");
            trocarMensagens(entradaConsole, saida, entradaServidor);

        } catch (UnknownHostException e) {
            System.err.println("Não foi possível localizar o host: " + ENDERECO_SERVIDOR);
        } catch (IOException e) {
            System.err.println("Falha de I/O durante a comunicação com o servidor: " + e.getMessage());
        }

        System.out.println("Conexão finalizada.");
    }

    private Socket abrirConexao() throws IOException {
        return new Socket(ENDERECO_SERVIDOR, PORTA_SERVIDOR);
    }

    private void trocarMensagens(BufferedReader console, PrintWriter saida, BufferedReader servidor) throws IOException {
        String mensagem = console.readLine();

        while (mensagem != null && !mensagem.trim().equalsIgnoreCase(COMANDO_SAIDA)) {
            saida.println(mensagem);
            System.out.println("Resposta do servidor: " + servidor.readLine());
            mensagem = console.readLine();
        }
    }
}
