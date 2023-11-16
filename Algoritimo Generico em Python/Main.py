from AlgoritimoGenerico import AlgoritmoGenetico

def main():
    # Solicita informações do usuário para configurar o algoritmo genético
    tamanho_populacao = int(input("Tamanho da população: "))
    estado_final = input("Palavra desejada: ")
    taxa_selecao = int(input("Taxa de seleção (entre 20 a 40%): "))
    taxa_mutacao = int(input("Taxa de mutação (entre 5 a 10%): "))
    qtd_geracoes = int(input("Quantidade de gerações: "))

    # Cria uma instância do AlgoritmoGenetico com os parâmetros fornecidos
    ag = AlgoritmoGenetico(tamanho_populacao, estado_final, taxa_selecao, taxa_mutacao, qtd_geracoes)
    
    # Executa o algoritmo genético
    ag.executar()

if __name__ == "__main__":
    # Chama a função principal quando o script é executado diretamente
    main()
