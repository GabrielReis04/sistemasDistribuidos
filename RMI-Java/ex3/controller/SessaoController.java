package controller;

import model.Sessao;
import model.TabelaHash;
import utilidades.Util;

public class SessaoController {

    private final TabelaHash hashSessoes;

    public SessaoController() {
        hashSessoes = new TabelaHash();
    }

    public String carregarSessoes(String nomeArquivo) {
        String resposta = Util.carregarArquivoEmHashMap(nomeArquivo, hashSessoes.sessoes);
        if (resposta.equals("")) {
            System.out.println("Sessoes carregadas... " + hashSessoes.sessoes.size());
        }
        return resposta;
    }

    public String consultarSessao(String numeroCelular) {
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
