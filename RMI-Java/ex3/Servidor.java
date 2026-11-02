import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

    private static final String NOME_SERVICO = "TelefoniaRemota";

    public static void main(String[] args) {
        String arquivo = (args.length > 0) ? args[0] : "telephony_sessions.txt";
        try {
            Registry registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            System.out.println("Porta padrao do RMI Java..." + Registry.REGISTRY_PORT);
            TelefoniaRemota servico = new TelefoniaRemota(arquivo);
            registro.rebind(NOME_SERVICO, servico);
            System.out.println("Servidor pronto para atender clientes....");
        } catch (Exception erro) {
            System.out.println("Erro de conexão ou " + erro);
        }
    }
}
