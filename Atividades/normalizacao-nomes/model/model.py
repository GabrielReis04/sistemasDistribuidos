import threading


def normalizar(nomes):
    return [nome.strip().upper() for nome in nomes]


def dividir_em_partes(nomes, quantidade_partes):
    tamanho_parte = len(nomes) // quantidade_partes
    partes = []
    inicio = 0

    for indice in range(quantidade_partes):
        fim = inicio + tamanho_parte if indice < quantidade_partes - 1 else len(nomes)
        partes.append(nomes[inicio:fim])
        inicio = fim

    return partes


def processar_parte(parte, resultados, posicao):
    resultados[posicao] = normalizar(parte)


def normalizar_nomes(nomes, quantidade_partes=2):
    partes = dividir_em_partes(nomes, quantidade_partes)
    resultados = [None] * quantidade_partes

    threads = [
        threading.Thread(target=processar_parte, args=(parte, resultados, indice))
        for indice, parte in enumerate(partes)
    ]

    for thread in threads:
        thread.start()
    for thread in threads:
        thread.join()

    saida = []
    for parte_normalizada in resultados:
        saida.extend(parte_normalizada)

    return saida
