package Threads.Java;

public class TarefaMemoriaCompartilhada {

    public static void main(String[] args) {
        String[] identificadores = {"Rotina-1", "Rotina-2"};

        for (String id : identificadores) {
            new Thread(TarefaMemoriaCompartilhada::executarRotina, id).start();
        }
    }

    private static void executarRotina() {
        Thread atual = Thread.currentThread();
        System.out.println("Executando na thread: " + atual.getName() + " | ID: " + atual.getId());
    }
}
