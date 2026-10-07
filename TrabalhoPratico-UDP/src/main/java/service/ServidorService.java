package service;

import controller.ServidorController;
import model.Pessoa;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Thread do servidor: escuta a porta UDP, aplica as regras de cadastro
 * e de token e responde ao cliente.
 */
public class ServidorService extends Thread {

    private final ServidorController controller;
    private final List<Pessoa> lista = new ArrayList<>();

    public ServidorService(ServidorController controller) {
        this.controller = controller;
        setDaemon(true);
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(Comunicador.PORTA_SERVIDOR)) {
            controller.registrar("Servidor UDP ativo na porta " + Comunicador.PORTA_SERVIDOR);

            while (true) {
                DatagramPacket pacote = Comunicador.recebeMensagem(socket);
                String mensagem = Comunicador.textoDo(pacote);
                controller.registrar("Recebido de " + pacote.getAddress().getHostAddress() + ":" + pacote.getPort() + " -> " + mensagem);

                String resposta;
                try {
                    resposta = processar(mensagem);
                } catch (Exception e) {
                    // Uma mensagem com problema não pode derrubar o servidor
                    resposta = Protocolo.montar(Protocolo.ERRO, "Falha ao processar: " + e.getMessage());
                }

                Comunicador.enviaMensagem(socket, Comunicador.montaMensagem(resposta, pacote.getAddress(), pacote.getPort()));
                controller.registrar("Respondido -> " + resposta);
            }
        } catch (Exception e) {
            controller.registrar("Servidor encerrado: " + e.getMessage());
        }
    }

    private String processar(String mensagem) {
        String[] campos = Protocolo.separar(mensagem);

        switch (campos[0].toUpperCase()) {
            case Protocolo.CADASTRO:
                if (campos.length != 3) {
                    return Protocolo.montar(Protocolo.ERRO, "Formato esperado: CADASTRO;nome;email");
                }
                return cadastrar(campos[1].trim(), campos[2].trim());

            case Protocolo.TOKEN:
                if (campos.length != 2) {
                    return Protocolo.montar(Protocolo.ERRO, "Formato esperado: TOKEN;email");
                }
                return token(campos[1].trim());

            default:
                return Protocolo.montar(Protocolo.ERRO, "Ação desconhecida: " + campos[0]);
        }
    }

    private String cadastrar(String nome, String email) {
        if (nome.isEmpty() || email.isEmpty()) {
            return Protocolo.montar(Protocolo.ERRO, "Nome e e-mail são obrigatórios.");
        }
        if (buscarPorEmail(email) != null) {
            return Protocolo.montar(Protocolo.ERRO, "E-mail já cadastrado: " + email);
        }

        lista.add(new Pessoa(nome, email));
        controller.atualizarLista(lista);
        return Protocolo.montar(Protocolo.OK, "Cadastro realizado para " + nome + ".");
    }

    private String token(String email) {
        Pessoa pessoa = buscarPorEmail(email);
        if (pessoa == null) {
            return Protocolo.montar(Protocolo.ERRO, "E-mail não cadastrado: " + email);
        }

        boolean novo = pessoa.renovarTokenSeExpirado();
        controller.atualizarLista(lista);
        return Protocolo.montar(Protocolo.TOKEN,
                pessoa.getToken(),
                String.valueOf(pessoa.segundosRestantes()),
                novo ? Protocolo.NOVO : Protocolo.MANTIDO);
    }

    private Pessoa buscarPorEmail(String email) {
        for (Pessoa p : lista) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return p;
            }
        }
        return null;
    }
}
