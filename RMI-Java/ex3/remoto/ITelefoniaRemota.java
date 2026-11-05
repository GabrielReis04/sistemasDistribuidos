package remoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITelefoniaRemota extends Remote {
    String consultarSessao(String numeroCelular) throws RemoteException;
}
