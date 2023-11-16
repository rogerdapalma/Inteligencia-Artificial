import random

# Classe utilitária com métodos estáticos para geração de palavras aleatórias
class Util:
    letras = "abcdefghijklmnopqrstuvxwyz"  # Alfabeto utilizado para gerar palavras aleatórias
    tamanho = len(letras)  # Tamanho do alfabeto

    @staticmethod
    def gerar_palavra(n):
        # Gera uma palavra aleatória de tamanho n, utilizando o alfabeto definido
        return ''.join(random.choice(Util.letras) for _ in range(n))
