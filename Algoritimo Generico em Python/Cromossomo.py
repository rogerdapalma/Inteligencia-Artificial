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
