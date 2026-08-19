import threading
import time


class TarefaIdentificada(threading.Thread):
    def __init__(self, quantidade, nome):
        super().__init__(name=nome)
        self.quantidade = quantidade

    def run(self):
        for _ in range(self.quantidade):
            print(f"Thread {self.name} | ID interno: {threading.get_ident()}")
            time.sleep(1)


threads = [TarefaIdentificada(3, f"Tarefa-{indice}") for indice in (1, 2)]

for thread in threads:
    thread.start()
