from model import normalizar_nomes
from view import exibir_resultado


def carregar_nomes(caminho_arquivo):
    with open(caminho_arquivo, "r", encoding="utf-8") as arquivo:
        return arquivo.readlines()


def iniciar():
    nomes = carregar_nomes("usuarios.txt")
    resultado = normalizar_nomes(nomes)
    exibir_resultado(resultado)
