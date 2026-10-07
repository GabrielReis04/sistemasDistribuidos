package service;

import java.io.IOException;

/**
 * Lado de rede do cliente: monta as mensagens do protocolo e envia via UDP.
 */
public class ClienteService {

    private static final int TIMEOUT_MS = 3000;

    /** Resposta do servidor já separada em campos. */
    public static class Resposta {
        public final String tipo;
        public final String[] campos;

        Resposta(String texto) {
            this.campos = Protocolo.separar(texto);
            this.tipo = campos[0];
        }

        public boolean ok() {
            return !Protocolo.ERRO.equals(tipo);
        }

        public String mensagem() {
            return campos.length > 1 ? campos[1] : "";
        }
    }

    public Resposta cadastrar(String nome, String email) throws IOException {
        return new Resposta(Comunicador.requisicao(Protocolo.montar(Protocolo.CADASTRO, nome, email), TIMEOUT_MS));
    }

    public Resposta solicitarToken(String email) throws IOException {
        return new Resposta(Comunicador.requisicao(Protocolo.montar(Protocolo.TOKEN, email), TIMEOUT_MS));
    }
}
