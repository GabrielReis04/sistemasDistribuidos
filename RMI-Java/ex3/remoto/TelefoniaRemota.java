package remoto;

import controller.SessaoController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TelefoniaRemota extends UnicastRemoteObject implements ITelefoniaRemota {

    private final SessaoController controller;

    public TelefoniaRemota(SessaoController controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    @Override
    public String consultarSessao(String numeroCelular) throws RemoteException {
        System.out.println("Numero recebido.... " + numeroCelular);
        return controller.consultarSessao(numeroCelular);
    }
}
