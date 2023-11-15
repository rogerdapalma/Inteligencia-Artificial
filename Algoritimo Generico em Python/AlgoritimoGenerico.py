import random
from Cromossomo import Cromossomo
from Util import Util

class AlgoritmoGenetico:
    def __init__(self, tamanho_populacao, estado_final, taxa_selecao, taxa_mutacao, qtd_geracoes):
        self.populacao = []
        self.nova_populacao = []
        self.tamanho_populacao = tamanho_populacao
        self.estado_final = estado_final
        self.taxa_selecao = taxa_selecao
        self.taxa_reproducao = 100 - taxa_selecao
        self.taxa_mutacao = taxa_mutacao
        self.qtd_geracoes = qtd_geracoes

    def gerar_populacao_inicial(self):
        for _ in range(self.tamanho_populacao):
            self.populacao.append(Cromossomo(Util.gerar_palavra(len(self.estado_final)), self.estado_final))
        self.ordenar_populacao()

    def ordenar_populacao(self):
        self.populacao.sort()

    def exibir_populacao(self):
        for cromossomo in self.populacao:
            print(f"Cromossomo: {cromossomo.valor} - {cromossomo.aptidao}")

    def selecionar_por_torneio(self):
        self.nova_populacao.append(self.populacao[0])  # elitismo

        qtd_selecionados = int(self.taxa_selecao * len(self.populacao) / 100)
        i = 1
        while i <= qtd_selecionados:
            c1 = random.choice(self.populacao)
            c2 = random.choice(self.populacao)
            c3 = random.choice(self.populacao)

            torneio = sorted([c1, c2, c3], key=lambda x: x.aptidao, reverse=True)
            selecionado = torneio[0]

            if selecionado not in self.nova_populacao:
                self.nova_populacao.append(selecionado)
                i += 1

    def selecionar_por_roleta(self):
        aptidao_total = sum(c.aptidao for c in self.populacao)

        for cromossomo in self.populacao:
            cromossomo.porcentagem_aptidao = cromossomo.aptidao * 100 / aptidao_total
            if cromossomo.porcentagem_aptidao == 0:
                cromossomo.porcentagem_aptidao = 1

        sorteio = [c for c in self.populacao for _ in range(int(c.porcentagem_aptidao))]

        qtd_selecionados = int(self.taxa_selecao * len(self.populacao) / 100)
        self.nova_populacao.append(self.populacao[0])  # elitismo

        for _ in range(qtd_selecionados):
            selecionado = random.choice(sorteio)
            self.nova_populacao.append(selecionado)
            sorteio = [c for c in sorteio if c != selecionado]

    def reproduzir(self):
        qtd_reproduzidos = int(self.taxa_reproducao * len(self.populacao) / 100)

        i = 0
        while i < qtd_reproduzidos:
            pai = random.choice(self.populacao)
            mae = random.choice(self.populacao)

            s_pai = pai.valor
            s_mae = mae.valor

            s_filho1 = s_pai[:len(s_pai)//2] + s_mae[len(s_mae)//2:]
            s_filho2 = s_mae[:len(s_mae)//2] + s_pai[len(s_pai)//2:]

            self.nova_populacao.append(Cromossomo(s_filho1, self.estado_final))
            self.nova_populacao.append(Cromossomo(s_filho2, self.estado_final))
            i += 2

        while len(self.nova_populacao) > len(self.populacao):
            self.nova_populacao.pop()

    def mutar(self):
        qtd_mutantes = random.randint(0, len(self.populacao) // 5)

        for _ in range(qtd_mutantes):
            posicao_mutante = random.randint(0, len(self.populacao) - 1)
            mutante = self.populacao[posicao_mutante]

            valor_mutado = list(mutante.valor)
            caracter_mutante = random.choice(mutante.valor)
            caracter_sorteado = random.choice(Util.letras)

            valor_mutado[mutante.valor.index(caracter_mutante)] = caracter_sorteado
            mutante.valor = ''.join(valor_mutado)

            mutante.aptidao = mutante.calcular_aptidao(self.estado_final)

    def executar(self):
        self.gerar_populacao_inicial()
        self.ordenar_populacao()

        print("Geração 1")
        self.exibir_populacao()

        for i in range(1, self.qtd_geracoes):
            self.selecionar_por_torneio()
            self.reproduzir()

            if i % (len(self.populacao) // self.taxa_mutacao) == 0:
                self.mutar()

            self.populacao = sorted(self.nova_populacao, reverse=True)
            self.nova_populacao.clear()

            print(f"\n\nGeração {i + 1}")
            self.exibir_populacao()
