import threading


def carregar_numeros(caminho, destino):
    with open(caminho, "r", encoding="utf-8") as arquivo:
        for linha in arquivo:
            linha = linha.strip()
            if linha:
                destino.append(int(linha))


def carregar_nomes(caminho, destino):
    with open(caminho, "r", encoding="utf-8") as arquivo:
        for linha in arquivo:
            linha = linha.strip()
            if linha:
                destino.append(linha)


def exibir_lista(titulo, lista):
    print(titulo)
    for item in lista:
        print(f"  {item}")


def main():
    numeros = []
    nomes = []

    leitor_numeros = threading.Thread(target=carregar_numeros, args=("numeros.txt", numeros))
    leitor_nomes = threading.Thread(target=carregar_nomes, args=("nomes.txt", nomes))

    leitor_numeros.start()
    leitor_nomes.start()
    leitor_numeros.join()
    leitor_nomes.join()

    exibidor_numeros = threading.Thread(target=exibir_lista, args=("Números lidos:", numeros))
    exibidor_nomes = threading.Thread(target=exibir_lista, args=("Nomes lidos:", nomes))

    exibidor_numeros.start()
    exibidor_nomes.start()
    exibidor_numeros.join()
    exibidor_nomes.join()


if __name__ == "__main__":
    main()
