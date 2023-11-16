# Importa o módulo random para geração de números aleatórios
import random

# Importa as classes Cromossomo e Util de módulos externos
from Cromossomo import Cromossomo
from Util import Util

# Classe principal que implementa o Algoritmo Genético
class AlgoritmoGenetico:
    def __init__(self, tamanho_populacao, estado_final, taxa_selecao, taxa_mutacao, qtd_geracoes):
        # Inicializa as variáveis da instância
        self.populacao = []  # Lista para armazenar a população atual de cromossomos
        self.nova_populacao = []  # Lista para armazenar a nova população gerada
        self.tamanho_populacao = tamanho_populacao  # Tamanho da população
        self.estado_final = estado_final  # Estado final desejado
        self.taxa_selecao = taxa_selecao  # Taxa de seleção
        self.taxa_reproducao = 100 - taxa_selecao  # Taxa de reprodução (complementar da taxa de seleção)
        self.taxa_mutacao = taxa_mutacao  # Taxa de mutação
        self.qtd_geracoes = qtd_geracoes  # Quantidade total de gerações

    def gerar_populacao_inicial(self):
        # Gera a população inicial de cromossomos com base no estado final desejado
        for _ in range(self.tamanho_populacao):
            self.populacao.append(Cromossomo(Util.gerar_palavra(len(self.estado_final)), self.estado_final))
        self.ordenar_populacao()

    def ordenar_populacao(self):
        # Ordena a população com base na aptidão dos cromossomos
        self.populacao.sort()

    def exibir_populacao(self):
        # Exibe os cromossomos e suas aptidões na população
        for cromossomo in self.populacao:
            print(f"Cromossomo: {cromossomo.valor} - {cromossomo.aptidao}")

    def selecionar_por_torneio(self):
        # Adiciona o melhor cromossomo da população atual à nova população (elitismo)
        self.nova_populacao.append(self.populacao[0])

        # Determina a quantidade de cromossomos a serem selecionados por torneio
        qtd_selecionados = int(self.taxa_selecao * len(self.populacao) / 100)
        i = 1

        # Realiza o processo de seleção por torneio
        while i <= qtd_selecionados:
            c1 = random.choice(self.populacao)
            c2 = random.choice(self.populacao)
            c3 = random.choice(self.populacao)

            # Realiza o torneio e seleciona o melhor cromossomo
            torneio = sorted([c1, c2, c3], key=lambda x: x.aptidao, reverse=True)
            selecionado = torneio[0]

            # Adiciona o cromossomo selecionado à nova população, se ainda não estiver presente
            if selecionado not in self.nova_populacao:
                self.nova_populacao.append(selecionado)
                i += 1

    def selecionar_por_roleta(self):
        # Calcula a aptidão total da população
        aptidao_total = sum(c.aptidao for c in self.populacao)

        # Calcula a porcentagem de aptidão de cada cromossomo
        for cromossomo in self.populacao:
            cromossomo.porcentagem_aptidao = cromossomo.aptidao * 100 / aptidao_total
            # Evita porcentagens nulas
            if cromossomo.porcentagem_aptidao == 0:
                cromossomo.porcentagem_aptidao = 1

        # Cria uma lista ponderada para o sorteio com base na aptidão
        sorteio = [c for c in self.populacao for _ in range(int(c.porcentagem_aptidao))]

        # Adiciona o melhor cromossomo da população atual à nova população (elitismo)
        self.nova_populacao.append(self.populacao[0])

        # Realiza o processo de seleção por roleta
        qtd_selecionados = int(self.taxa_selecao * len(self.populacao) / 100)
        for _ in range(qtd_selecionados):
            # Realiza o sorteio e seleciona um cromossomo
            selecionado = random.choice(sorteio)
            # Adiciona o cromossomo selecionado à nova população
            self.nova_populacao.append(selecionado)
            # Remove o cromossomo selecionado da lista ponderada para evitar seleção duplicada
            sorteio = [c for c in sorteio if c != selecionado]

    def reproduzir(self):
        # Determina a quantidade de cromossomos a serem reproduzidos
        qtd_reproduzidos = int(self.taxa_reproducao * len(self.populacao) / 100)

        # Inicializa o contador
        i = 0

        # Realiza o processo de reprodução
        while i < qtd_reproduzidos:
            # Seleciona aleatoriamente dois pais da população atual
            pai = random.choice(self.populacao)
            mae = random.choice(self.populacao)

            # Realiza o cruzamento dos pais para gerar dois filhos
            s_pai = pai.valor
            s_mae = mae.valor
            s_filho1 = s_pai[:len(s_pai)//2] + s_mae[len(s_mae)//2:]
            s_filho2 = s_mae[:len(s_mae)//2] + s_pai[len(s_pai)//2:]

            # Adiciona os filhos à nova população
            self.nova_populacao.append(Cromossomo(s_filho1, self.estado_final))
            self.nova_populacao.append(Cromossomo(s_filho2, self.estado_final))

            # Atualiza o contador
            i += 2

        # Ajusta o tamanho da nova população
        while len(self.nova_populacao) > len(self.populacao):
            self.nova_populacao.pop()

    def mutar(self):
        # Determina a quantidade de cromossomos a serem mutados
        qtd_mutantes = random.randint(0, len(self.populacao) // 5)

        # Realiza o processo de mutação
        for _ in range(qtd_mutantes):
            # Seleciona aleatoriamente um cromossomo para mutação
            posicao_mutante = random.randint(0, len(self.populacao) - 1)
            mutante = self.populacao[posicao_mutante]

            # Realiza a mutação no valor do cromossomo
            valor_mutado = list(mutante.valor)
            caracter_mutante = random.choice(mutante.valor)
            caracter_sorteado = random.choice(Util.letras)

            # Substitui um caractere pelo caracter_sorteado
            valor_mutado[mutante.valor.index(caracter_mutante)] = caracter_sorteado
            mutante.valor = ''.join(valor_mutado)

            # Recalcula a aptidão do cromossomo após a mutação
            mutante.aptidao = mutante.calcular_aptidao(self.estado_final)

    def executar(self):
        # Gera a população inicial e a ordena
        self.gerar_populacao_inicial()
        self.ordenar_populacao()

        # Exibe a população inicial
        print("Geração 1")
        self.exibir_populacao()

        # Executa o algoritmo genético por um número especificado de gerações
        for i in range(1, self.qtd_geracoes):
            # Realiza a seleção por torneio
            self.selecionar_por_torneio()
            
            # Realiza a reprodução
            self.reproduzir()

            # Realiza a mutação a cada X gerações
            if i % (len(self.populacao) // self.taxa_mutacao) == 0:
                self.mutar()

            # Ordena a nova população e a substitui pela população atual
            self.populacao = sorted(self.nova_populacao, reverse=True)
            self.nova_populacao.clear()

            # Exibe a população da geração atual
            print(f"\n\nGeração {i + 1}")
            self.exibir_populacao()
