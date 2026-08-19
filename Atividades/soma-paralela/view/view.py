def exibir_resultado(somas):
    for indice, soma in enumerate(somas, start=1):
        print(f"Thread {indice}: {soma}")

    print(f"Soma total: {sum(somas)}")
