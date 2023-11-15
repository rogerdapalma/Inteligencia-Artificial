from AlgoritimoGenerico import AlgoritmoGenetico

def main():
    tamanho_populacao = int(input("Tamanho da população: "))
    estado_final = input("Palavra desejada: ")
    taxa_selecao = int(input("Taxa de seleção (entre 20 a 40%): "))
    taxa_mutacao = int(input("Taxa de mutação (entre 5 a 10%): "))
    qtd_geracoes = int(input("Quantidade de gerações: "))

    ag = AlgoritmoGenetico(tamanho_populacao, estado_final, taxa_selecao, taxa_mutacao, qtd_geracoes)
    ag.executar()

if __name__ == "__main__":
    main()
