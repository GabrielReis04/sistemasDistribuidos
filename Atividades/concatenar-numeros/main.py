import threading


class ColecaoDeNumeros:
    def __init__(self):
        self._valores = []
        self._trava = threading.Lock()

    def carregar_de(self, caminho_arquivo):
        with open(caminho_arquivo, "r", encoding="utf-8") as arquivo:
            numeros_lidos = [int(linha.strip()) for linha in arquivo if linha.strip()]

        with self._trava:
            self._valores.extend(numeros_lidos)

    def valores(self):
        with self._trava:
            return list(self._valores)


def main():
    colecao = ColecaoDeNumeros()

    thread_1 = threading.Thread(target=colecao.carregar_de, args=("numeros1.txt",))
    thread_2 = threading.Thread(target=colecao.carregar_de, args=("numeros2.txt",))

    thread_1.start()
    thread_2.start()
    thread_1.join()
    thread_2.join()

    print("Lista final:", colecao.valores())


if __name__ == "__main__":
    main()
