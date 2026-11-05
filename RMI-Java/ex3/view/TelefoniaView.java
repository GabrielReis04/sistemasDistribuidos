package view;

import java.util.Scanner;

public class TelefoniaView {

    private final Scanner entrada;

    public TelefoniaView() {
        entrada = new Scanner(System.in);
    }

    public String lerNumeroCelular() {
        System.out.print("\nNumero do celular (ou sair): ");
        return entrada.nextLine();
    }

    public void mostrarSessao(String dadosSessao) {
        System.out.println("Dados da sessao... " + dadosSessao);
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void mostrarErro(String erro) {
        System.out.println(erro);
    }

    public void fechar() {
        entrada.close();
    }
}
