import random
import threading


def preencher_valores(valores, quantidade):
    for _ in range(quantidade):
        valores.append(random.randint(1, 1000))


def ordenar_insercao(valores):
    for i in range(1, len(valores)):
        atual = valores[i]
        j = i - 1
        while j >= 0 and valores[j] > atual:
            valores[j + 1] = valores[j]
            j -= 1
        valores[j + 1] = atual

    print("Lista ordenada pelo método de inserção")


def _mesclar(valores, inicio, meio, fim, area_temporaria):
    area_temporaria[inicio:fim] = valores[inicio:fim]
    i, j, k = inicio, meio, inicio

    while i < meio and j < fim:
        if area_temporaria[i] <= area_temporaria[j]:
            valores[k] = area_temporaria[i]
            i += 1
        else:
            valores[k] = area_temporaria[j]
            j += 1
        k += 1

    while i < meio:
        valores[k] = area_temporaria[i]
        i += 1
        k += 1

    while j < fim:
        valores[k] = area_temporaria[j]
        j += 1
        k += 1


def _dividir(valores, inicio, fim, area_temporaria):
    if fim - inicio <= 1:
        return

    meio = (inicio + fim) // 2
    _dividir(valores, inicio, meio, area_temporaria)
    _dividir(valores, meio, fim, area_temporaria)
    _mesclar(valores, inicio, meio, fim, area_temporaria)


def ordenar_mesclagem(valores):
    _dividir(valores, 0, len(valores), [0] * len(valores))
    print("Lista ordenada pelo método de mesclagem")


def executar_em_paralelo(tarefas):
    threads = [threading.Thread(target=funcao, args=argumentos) for funcao, argumentos in tarefas]

    for thread in threads:
        thread.start()
    for thread in threads:
        thread.join()


lista_a = []
lista_b = []

executar_em_paralelo([
    (preencher_valores, (lista_a, 1000)),
    (preencher_valores, (lista_b, 500)),
])

executar_em_paralelo([
    (ordenar_insercao, (lista_a,)),
    (ordenar_mesclagem, (lista_b,)),
])
