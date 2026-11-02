import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CadastroRemoto extends UnicastRemoteObject implements ICadastroRemoto {

    private static final String DOMINIO = "@ufn.edu.br";

    public CadastroRemoto() throws RemoteException {
        super();
    }

    @Override
    public String gerarEmail(String nomeCompleto) throws RemoteException {
        System.out.println("Nome recebido.... " + nomeCompleto);
        String[] partes = nomeCompleto.toLowerCase().split(" ");

        if (partes.length <= 1) {
            return "Nao foi possivel gerar email";
        }

        String primeiroNome = partes[0];
        String ultimoNome = partes[partes.length - 1];
        return primeiroNome + "." + ultimoNome + DOMINIO;
    }

    @Override
    public String gerarHash(String texto) throws RemoteException {
        if (texto.isEmpty()) {
            return "Nao foi possivel gerar hash";
        }
        return Integer.toString(texto.hashCode());
    }
}
