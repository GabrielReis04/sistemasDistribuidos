import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRelogioRemoto extends Remote {
    String dataHoraAtual() throws RemoteException;
}
