package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Tela do cliente. Só cuida da interface: quem decide o que fazer é o ClienteController.
 */
public class ClienteView extends JFrame {

    private final JTextField txtNome = new JTextField(25);
    private final JTextField txtEmail = new JTextField(25);
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JButton btnToken = new JButton("Solicitar token agora");
    private final JLabel lblToken = new JLabel("------");
    private final JLabel lblValidade = new JLabel(" ");
    private final JTextArea areaLog = new JTextArea(10, 40);

    public ClienteView() {
        setTitle("Cliente UDP - Cadastro e Token");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Cadastro"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; formulario.add(new JLabel("Nome completo:"), c);
        c.gridx = 1; formulario.add(txtNome, c);
        c.gridx = 0; c.gridy = 1; formulario.add(new JLabel("E-mail:"), c);
        c.gridx = 1; formulario.add(txtEmail, c);
        c.gridx = 1; c.gridy = 2; c.anchor = GridBagConstraints.EAST; formulario.add(btnCadastrar, c);

        JPanel painelToken = new JPanel(new GridLayout(3, 1, 4, 4));
        painelToken.setBorder(BorderFactory.createTitledBorder("Token (validade de 60s)"));
        lblToken.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        lblToken.setHorizontalAlignment(SwingConstants.CENTER);
        lblValidade.setHorizontalAlignment(SwingConstants.CENTER);
        painelToken.add(lblToken);
        painelToken.add(lblValidade);
        painelToken.add(btnToken);
        btnToken.setEnabled(false);

        areaLog.setEditable(false);
        areaLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Histórico"));

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(formulario, BorderLayout.NORTH);
        topo.add(painelToken, BorderLayout.CENTER);

        JPanel raiz = new JPanel(new BorderLayout(8, 8));
        raiz.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        raiz.add(topo, BorderLayout.NORTH);
        raiz.add(scrollLog, BorderLayout.CENTER);
        setContentPane(raiz);

        pack();
        setLocationRelativeTo(null);
    }

    public String getNome() {
        return txtNome.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public void setCadastrarListener(ActionListener listener) {
        btnCadastrar.addActionListener(listener);
    }

    public void setTokenListener(ActionListener listener) {
        btnToken.addActionListener(listener);
    }

    public void setCadastroHabilitado(boolean habilitado) {
        btnCadastrar.setEnabled(habilitado);
    }

    /** Após o cadastro: trava os campos e libera o botão de token. */
    public void modoCadastrado() {
        txtNome.setEditable(false);
        txtEmail.setEditable(false);
        btnCadastrar.setEnabled(false);
        btnToken.setEnabled(true);
    }

    public void mostrarToken(String token) {
        lblToken.setText(token);
    }

    public void mostrarValidade(String texto) {
        lblValidade.setText(texto);
    }

    public void adicionarLog(String texto) {
        areaLog.append(texto + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    public void exibirMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
}
