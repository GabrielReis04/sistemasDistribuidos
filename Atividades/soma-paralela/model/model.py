import threading


def dividir_em_blocos(valores, quantidade_blocos):
    tamanho_bloco = len(valores) // quantidade_blocos
    blocos = []
    inicio = 0

    for indice in range(quantidade_blocos):
        fim = inicio + tamanho_bloco if indice < quantidade_blocos - 1 else len(valores)
        blocos.append(valores[inicio:fim])
        inicio = fim

    return blocos


def somar_bloco(bloco, somas, posicao):
    somas[posicao] = sum(bloco)


def calcular_somas(valores, quantidade_blocos=4):
    blocos = dividir_em_blocos(valores, quantidade_blocos)
    somas = [0] * quantidade_blocos

    threads = [
        threading.Thread(target=somar_bloco, args=(bloco, somas, indice))
        for indice, bloco in enumerate(blocos)
    ]

    for thread in threads:
        thread.start()
    for thread in threads:
        thread.join()

    return somas
