package controller;

import service.ClienteService;
import service.ClienteService.Resposta;
import service.Protocolo;
import view.ClienteView;

import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/**
 * Aq é onde controla o cliente e valido oq u usuario digitou, chama o clientService 
 * (rede UDP) e atualiza a ClienteView.]
 *
 * Depois do cadastro, solicita o token automaticamente a cada Intervalo
 */
public class ClienteController {

    private static final int INTERVALO_TOKEN_MS = 15_000; // pede token a cada 15s

    private final ClienteView view;
    private final ClienteService service;

    private final Timer timerToken = new Timer(INTERVALO_TOKEN_MS, e -> solicitarToken());
    private final Timer timerContagem = new Timer(1000, e -> atualizarContagem());
    private String emailCadastrado;
    private long tokenExpiraEm;

    public ClienteController() {
        view = new ClienteView();
        service = new ClienteService();

        view.setCadastrarListener(e -> cadastrar());
        view.setTokenListener(e -> solicitarToken());

        view.setVisible(true);
    }

    private void cadastrar() {
        String nome = view.getNome();
        String email = view.getEmail();

        if (nome.isEmpty() || email.isEmpty()) {
            view.exibirMensagem("Preencha o nome e o e-mail.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nome.contains(Protocolo.SEPARADOR) || email.contains(Protocolo.SEPARADOR)) {
            view.exibirMensagem("Nome e e-mail não podem conter ';'.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            view.exibirMensagem("E-mail inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        view.setCadastroHabilitado(false);
        executarEmSegundoPlano(() -> service.cadastrar(nome, email), resposta -> {
            log((resposta.ok() ? "Cadastro: " : "Erro: ") + resposta.mensagem());
            if (resposta.ok()) {
                emailCadastrado = email;
                view.modoCadastrado();
                solicitarToken();       // primeiro token logo após o cadastro
                timerToken.start();     // e depois periodicamente
                timerContagem.start();
            } else {
                view.setCadastroHabilitado(true);
                view.exibirMensagem(resposta.mensagem(), "Cadastro", JOptionPane.WARNING_MESSAGE);
            }
        }, () -> view.setCadastroHabilitado(true));
    }

    private void solicitarToken() {
        if (emailCadastrado == null) {
            return;
        }
        executarEmSegundoPlano(() -> service.solicitarToken(emailCadastrado), resposta -> {
            if (!resposta.ok() || resposta.campos.length < 4) {
                log("Erro: " + resposta.mensagem());
                return;
            }
            String token = resposta.campos[1];
            long segundos = Long.parseLong(resposta.campos[2]);
            boolean novo = Protocolo.NOVO.equals(resposta.campos[3]);

            view.mostrarToken(token);
            tokenExpiraEm = System.currentTimeMillis() + segundos * 1000;
            atualizarContagem();
            log("Token " + token + (novo ? " (NOVO gerado)" : " (MANTIDO)") + " - expira em " + segundos + "s");
        }, null);
    }

    private void atualizarContagem() {
        long restante = Math.max(0, (tokenExpiraEm - System.currentTimeMillis() + 999) / 1000);
        view.mostrarValidade(restante > 0 ? "Válido por mais " + restante + "s" : "Expirado - será renovado no próximo pedido");
    }

    /** Roda a chamada de rede fora da thread da interface, para a janela não travar. */
    private void executarEmSegundoPlano(Chamada chamada, Consumer<Resposta> aoTerminar, Runnable aoFalhar) {
        new SwingWorker<Resposta, Void>() {
            @Override
            protected Resposta doInBackground() throws Exception {
                return chamada.executar();
            }

            @Override
            protected void done() {
                try {
                    aoTerminar.accept(get());
                } catch (Exception e) {
                    Throwable causa = e.getCause() != null ? e.getCause() : e;
                    log("Falha de comunicação: " + causa.getMessage());
                    if (aoFalhar != null) {
                        aoFalhar.run();
                    }
                }
            }
        }.execute();
    }

    private interface Chamada {
        Resposta executar() throws Exception;
    }

    private void log(String texto) {
        view.adicionarLog("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + texto);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClienteController::new);
    }
}
