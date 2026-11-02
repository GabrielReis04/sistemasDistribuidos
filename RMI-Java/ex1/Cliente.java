import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : "localhost";
        try {
            Registry registro = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
            IRelogioRemoto relogio = (IRelogioRemoto) registro.lookup("RelogioRemoto");
            System.out.println("Data e hora vinda do servidor!!");
            System.out.println("Data: " + relogio.dataHoraAtual());
        } catch (Exception erro) {
            System.out.println("Error: " + erro);
        }
    }
}
