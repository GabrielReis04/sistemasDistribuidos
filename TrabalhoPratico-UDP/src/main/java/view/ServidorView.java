package view;

import javax.swing.*;
import java.awt.*;

/**
 * Tela do servidor: mostra as pessoas cadastradas e o histórico de mensagens.
 */
public class ServidorView extends JFrame {

    private final JTextArea areaPessoas = new JTextArea(8, 50);
    private final JTextArea areaLog = new JTextArea(12, 50);

    public ServidorView() {
        setTitle("Servidor UDP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        areaPessoas.setEditable(false);
        areaPessoas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPessoas = new JScrollPane(areaPessoas);
        scrollPessoas.setBorder(BorderFactory.createTitledBorder("Pessoas cadastradas"));

        areaLog.setEditable(false);
        areaLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Mensagens UDP"));

        JPanel raiz = new JPanel(new GridLayout(2, 1, 8, 8));
        raiz.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        raiz.add(scrollPessoas);
        raiz.add(scrollLog);
        setContentPane(raiz);

        pack();
        setLocationRelativeTo(null);
    }

    public void atualizarPessoas(String texto) {
        areaPessoas.setText(texto);
    }

    public void adicionarLog(String texto) {
        areaLog.append(texto + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }
}
