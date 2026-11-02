import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

    private static final String NOME_SERVICO = "RelogioRemoto";

    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            RelogioRemoto relogio = new RelogioRemoto();
            System.out.println("Servidor pronto para atender clientes....");
            registro.bind(NOME_SERVICO, relogio);
        } catch (Exception erro) {
            System.out.println("Error: " + erro);
        }
    }
}
