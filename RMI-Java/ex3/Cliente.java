import controller.ClienteController;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import remoto.ITelefoniaRemota;
import view.TelefoniaView;

public class Cliente {

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : "localhost";
        TelefoniaView view = new TelefoniaView();
        try {
            Registry registro = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
            ITelefoniaRemota servico = (ITelefoniaRemota) registro.lookup("TelefoniaRemota");
            new ClienteController(servico, view).iniciar();
        } catch (Exception erro) {
            view.mostrarErro("Erro de conexão ou " + erro);
        }
    }
}
