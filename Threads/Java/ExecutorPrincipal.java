package Threads.Java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExecutorPrincipal {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> valoresColetados = Collections.synchronizedList(new ArrayList<>());

        Thread trabalhador1 = criarTrabalhador(valoresColetados, 5);
        Thread trabalhador2 = criarTrabalhador(valoresColetados, 5);

        trabalhador1.start();
        trabalhador2.start();

        trabalhador1.join();
        trabalhador2.join();

        System.out.println("Resultado final: " + valoresColetados);
    }

    private static Thread criarTrabalhador(List<Integer> destino, int quantidadeItens) {
        return new Thread(() -> {
            for (int i = 1; i <= quantidadeItens; i++) {
                synchronized (destino) {
                    destino.add(i);
                    System.out.println(Thread.currentThread().getName() + " registrou: " + i);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }
}
