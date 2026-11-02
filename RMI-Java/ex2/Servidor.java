import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

    private static final String NOME_SERVICO = "CadastroRemoto";

    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            System.out.println("Porta padrao do RMI Java..." + Registry.REGISTRY_PORT);
            CadastroRemoto servico = new CadastroRemoto();
            System.out.println("Servidor retornando....");
            registro.rebind(NOME_SERVICO, servico);
        } catch (Exception erro) {
            System.out.println("Erro de conexão ou " + erro);
        }
    }
}
