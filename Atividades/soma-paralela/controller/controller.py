import random
from model import calcular_somas
from view import exibir_resultado


def gerar_valores_aleatorios(quantidade, limite_inferior=1, limite_superior=100):
    return [random.randint(limite_inferior, limite_superior) for _ in range(quantidade)]


def iniciar():
    valores = gerar_valores_aleatorios(10000)
    somas = calcular_somas(valores)
    exibir_resultado(somas)
