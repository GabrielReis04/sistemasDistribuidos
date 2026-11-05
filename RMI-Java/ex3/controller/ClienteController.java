package controller;

import java.rmi.RemoteException;
import remoto.ITelefoniaRemota;
import view.TelefoniaView;

public class ClienteController {

    private final ITelefoniaRemota servico;
    private final TelefoniaView view;

    public ClienteController(ITelefoniaRemota servico, TelefoniaView view) {
        this.servico = servico;
        this.view = view;
    }

    public void iniciar() {
        view.mostrarMensagem("Conectado ao servidor de telefonia...");

        while (true) {
            String numero = view.lerNumeroCelular();
            if (numero.trim().equalsIgnoreCase("sair")) {
                break;
            }
            try {
                view.mostrarSessao(servico.consultarSessao(numero));
            } catch (RemoteException erro) {
                view.mostrarErro("Erro na consulta remota: " + erro.getMessage());
            }
        }
        view.fechar();
    }
}
