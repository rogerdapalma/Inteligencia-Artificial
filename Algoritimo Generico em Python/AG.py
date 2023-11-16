import random

# Classe que representa um Cromossomo no contexto do Algoritmo Genético
class Cromossomo:
    def __init__(self, valor, estado_final):
        # Inicializa um objeto Cromossomo com um valor e calcula sua aptidão
        self.valor = valor  # Representação do cromossomo
        self.aptidao = self.calcular_aptidao(estado_final)  # Valor de aptidão do cromossomo

    def calcular_aptidao(self, estado_final):
        # Calcula a aptidão do cromossomo com base no estado final desejado
        nota = 0  # Inicializa a pontuação do cromossomo

        # Itera sobre cada posição no estado final e compara com o valor do cromossomo
        for i in range(len(estado_final)):
            # Atribui pontos se o caractere na posição for parte do valor do cromossomo
            if estado_final[i] in self.valor:
                nota += 5
            # Atribui pontos adicionais se o caractere na posição for igual ao do estado final
            if self.valor[i] == estado_final[i]:
                nota += 50

        return nota  # Retorna a pontuação total do cromossomo

    def __lt__(self, other):
        # Sobrecarga do operador de comparação menor que (<) para ordenação decrescente por aptidão
        return self.aptidao > other.aptidao

    def __eq__(self, other):
        # Sobrecarga do operador de igualdade (==) para comparar se dois cromossomos têm o mesmo valor
        if isinstance(other, Cromossomo):
            return self.valor == other.valor
        return False  # Retorna False se o objeto comparado não for do tipo Cromossomo

# Classe utilitária com métodos estáticos para geração de palavras aleatórias
class Util:
    letras = "abcdefghijklmnopqrstuvxwyz"  # Alfabeto utilizado para gerar palavras aleatórias
    tamanho = len(letras)  # Tamanho do alfabeto

    @staticmethod
    def gerar_palavra(n):
        # Gera uma palavra aleatória de tamanho n, utilizando o alfabeto definido
        return ''.join(random.choice(Util.letras) for _ in range(n))

# Classe que representa o Algoritmo Genético (AG)
class AG:
    def __init__(self, tamanho_populacao, estado_final, taxa_selecao, taxa_mutacao, qtd_geracoes):
        # Inicializa o AG com parâmetros específicos
        self.populacao = []  # População atual de cromossomos
        self.nova_populacao = []  # Nova população gerada durante o processo evolutivo
        self.tamanho_populacao = tamanho_populacao  # Tamanho da população
        self.estado_final = estado_final  # Estado final desejado
        self.taxa_selecao = taxa_selecao  # Taxa de seleção (porcentagem)
        self.taxa_reproducao = 100 - taxa_selecao  # Taxa de reprodução (porcentagem)
        self.taxa_mutacao = taxa_mutacao  # Taxa de mutação (porcentagem)
        self.qtd_geracoes = qtd_geracoes  # Quantidade de gerações

    def gerar_populacao_inicial(self):
        # Gera a população inicial de cromossomos com valores aleatórios
        for _ in range(self.tamanho_populacao):
            self.populacao.append(Cromossomo(Util.gerar_palavra(len(self.estado_final)), self.estado_final))
        self.ordenar_populacao()

    def ordenar_populacao(self):
        # Ordena a população atual em ordem decrescente de aptidão
        self.populacao.sort()

    def exibir_populacao(self):
        # Exibe os cromossomos e suas aptidões na população atual
        for cromossomo in self.populacao:
            print(f"Cromossomo: {cromossomo.valor} - {cromossomo.aptidao}")

    def selecionar_por_torneio(self):
        # Realiza a seleção por torneio, adicionando os escolhidos à nova população
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
        # Realiza a seleção por roleta, adicionando os escolhidos à nova população
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
        # Realiza o processo de reprodução, gerando novos cromossomos na nova população
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
        # Realiza o processo de mutação, alterando aleatoriamente o valor de alguns cromossomos
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
        # Executa o Algoritmo Genético, gerando gerações sucessivas e exibindo a evolução
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

def main():
    # Função principal para configurar e executar o Algoritmo Genético
    tamanho_populacao = int(input("Tamanho da população: "))
    estado_final = input("Palavra desejada: ")
    taxa_selecao = int(input("Taxa de seleção (entre 20 a 40%): "))
    taxa_mutacao = int(input("Taxa de mutação (entre 5 a 10%): "))
    qtd_geracoes = int(input("Quantidade de gerações: "))

    ag = AG(tamanho_populacao, estado_final, taxa_selecao, taxa_mutacao, qtd_geracoes)
    ag.executar()

if __name__ == "__main__":
    main()
