import random

class Util:
    letras = "abcdefghijklmnopqrstuvxwyz"
    tamanho = len(letras)

    @staticmethod
    def gerar_palavra(n):
        return ''.join(random.choice(Util.letras) for _ in range(n))
