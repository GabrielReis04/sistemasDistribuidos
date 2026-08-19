package Threads.Java;

public class PrimeiraExecucaoThread {

    static class ExecucaoRotulada extends Thread {
        private final String rotulo;

        ExecucaoRotulada(String rotulo) {
            this.rotulo = rotulo;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("Rodando a thread " + rotulo + ": " + i);
            }
        }
    }

    public static void main(String[] args) {
        String[] rotulos = {"alfa", "beta"};

        for (String rotulo : rotulos) {
            new ExecucaoRotulada(rotulo).start();
        }
    }
}
