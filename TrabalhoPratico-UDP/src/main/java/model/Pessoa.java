package model;

import java.security.SecureRandom;

/**
 * Pessoa cadastrada no servidor, com seu token temporário.
 */
public class Pessoa {

    public static final long VALIDADE_TOKEN_MS = 60_000; // 60 segundos
    private static final SecureRandom RANDOM = new SecureRandom();

    private final String nome;
    private final String email;
    private String token;
    private long tokenGeradoEm; // instante (ms) em que o token atual foi gerado

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public boolean tokenValido() {
        return token != null && System.currentTimeMillis() - tokenGeradoEm < VALIDADE_TOKEN_MS;
    }

    /**
     * Se o token atual ainda está dentro dos 60s, mantém o mesmo.
     * Caso contrário (ou se ainda não existe), gera um novo token aleatório.
     *
     * @return true se um novo token foi gerado, false se foi mantido
     */
    public boolean renovarTokenSeExpirado() {
        if (tokenValido()) {
            return false;
        }
        token = String.format("%06d", RANDOM.nextInt(1_000_000));
        tokenGeradoEm = System.currentTimeMillis();
        return true;
    }

    public long segundosRestantes() {
        long restante = VALIDADE_TOKEN_MS - (System.currentTimeMillis() - tokenGeradoEm);
        return Math.max(0, (restante + 999) / 1000);
    }

    @Override
    public String toString() {
        return nome + " <" + email + ">";
    }
}
