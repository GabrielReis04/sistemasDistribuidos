package controller;

import model.Pessoa;
import service.ServidorService;
import view.ServidorView;

import javax.swing.SwingUtilities;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Liga a tela do servidor (ServidorView) à thread que atende os clientes (ServidorService).
 */
public class ServidorController {

    private final ServidorView view;
    private final ServidorService service;

    public ServidorController() {
        view = new ServidorView();
        view.setVisible(true);

        service = new ServidorService(this);
        service.start();
    }

    // Chamado pelo Service quando a lista muda (cadastro ou token novo)
    public void atualizarLista(List<Pessoa> lista) {
        StringBuilder sb = new StringBuilder();
        for (Pessoa p : lista) {
            sb.append(String.format("%-25s %-28s token: %s%n",
                    p.getNome(), p.getEmail(), p.getToken() == null ? "-" : p.getToken()));
        }
        SwingUtilities.invokeLater(() -> view.atualizarPessoas(sb.toString()));
    }

    // Chamado pelo Service a cada mensagem recebida/enviada
    public void registrar(String texto) {
        String linha = "[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + texto;
        System.out.println(linha);
        SwingUtilities.invokeLater(() -> view.adicionarLog(linha));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServidorController::new);
    }
}
