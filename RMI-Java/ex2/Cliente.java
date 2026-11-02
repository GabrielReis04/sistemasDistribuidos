import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    private static final String HOST_PADRAO = "10.104.12.12";

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : HOST_PADRAO;
        Scanner entrada = new Scanner(System.in);
        try {
            Registry registro = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
            ICadastroRemoto servico = (ICadastroRemoto) registro.lookup("CadastroRemoto");
            System.out.println("Servicos vindos do servidor ....");

            System.out.print("Nome completo: ");
            String nome = entrada.nextLine();

            System.out.println("Email gerado... " + servico.gerarEmail(nome));
            System.out.println("Has gerado... " + servico.gerarHash(nome));
        } catch (Exception erro) {
            System.out.println("Erro de conexão ou " + erro);
        }
    }
}
