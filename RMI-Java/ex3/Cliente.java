import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : "localhost";
        Scanner entrada = new Scanner(System.in);
        try {
            Registry registro = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
            ITelefoniaRemota servico = (ITelefoniaRemota) registro.lookup("TelefoniaRemota");
            System.out.println("Conectado ao servidor de telefonia...");

            while (true) {
                System.out.print("\nNumero do celular (ou sair): ");
                String numero = entrada.nextLine();
                if (numero.equalsIgnoreCase("sair")) {
                    break;
                }
                System.out.println("Dados da sessao... " + servico.consultarSessao(numero));
            }
        } catch (Exception erro) {
            System.out.println("Erro de conexão ou " + erro);
        }
    }
}
