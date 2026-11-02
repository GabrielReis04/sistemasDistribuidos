package utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import model.Sessao;

public class Util {

    public static String carregarArquivoEmHashMap(String nomeArquivo, HashMap<String, Sessao> sessoes) {
        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] dadosLinha = linha.split(";", 2);
                if (dadosLinha.length == 2) {
                    Sessao sessao = new Sessao(dadosLinha[0].trim(), dadosLinha[1]);
                    sessoes.put(sessao.getNumeroCelular(), sessao);
                }
            }
            return "";
        } catch (IOException e) {
            return "Erro ao processar o arquivo: " + e.getMessage();
        }
    }
}
