import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICadastroRemoto extends Remote {
    String gerarEmail(String nomeCompleto) throws RemoteException;
    String gerarHash(String texto) throws RemoteException;
}
