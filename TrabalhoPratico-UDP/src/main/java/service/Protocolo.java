package service;

/**
 * Formato das mensagens trocadas via UDP (campos separados por ';').
 *
 * Cliente -> Servidor:
 *   CADASTRO;nome;email
 *   TOKEN;email
 *
 * Servidor -> Cliente:
 *   OK;mensagem
 *   ERRO;mensagem
 *   TOKEN;valor;segundosRestantes;NOVO|MANTIDO
 */
public class Protocolo {
    public static final String SEPARADOR = ";";

    public static final String CADASTRO = "CADASTRO";
    public static final String TOKEN = "TOKEN";
    public static final String OK = "OK";
    public static final String ERRO = "ERRO";

    public static final String NOVO = "NOVO";
    public static final String MANTIDO = "MANTIDO";

    public static String montar(String... campos) {
        return String.join(SEPARADOR, campos);
    }

    public static String[] separar(String mensagem) {
        return mensagem.split(SEPARADOR, -1);
    }
}
