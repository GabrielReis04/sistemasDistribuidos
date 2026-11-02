import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.Sessao;
import model.TabelaHash;
import utilidades.Util;

public class TelefoniaRemota extends UnicastRemoteObject implements ITelefoniaRemota {

    private TabelaHash hashSessoes;

    public TelefoniaRemota(String nomeArquivo) throws RemoteException {
        super();
        hashSessoes = new TabelaHash();

        String resposta = Util.carregarArquivoEmHashMap(nomeArquivo, hashSessoes.sessoes);
        if (!resposta.equals("")) {
            throw new RemoteException(resposta);
        }
        System.out.println("Sessoes carregadas... " + hashSessoes.sessoes.size());
    }

    @Override
    public String consultarSessao(String numeroCelular) throws RemoteException {
        System.out.println("Numero recebido.... " + numeroCelular);

        if (numeroCelular == null || numeroCelular.trim().isEmpty()) {
            return "Numero de celular nao informado";
        }

        Sessao sessao = hashSessoes.sessoes.get(numeroCelular.trim());
        if (sessao == null) {
            return "Numero de celular nao encontrado";
        }
        return sessao.getDadosSessao();
    }
}
