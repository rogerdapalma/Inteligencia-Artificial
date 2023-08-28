class Sudoku:
    def __init__(self):
        self.matriz = None
        self.dimensao = 0
        self.total_chamadas_nao_recursivas = 0

    def get_matriz(self):
        return self.matriz

    def set_matriz(self, matriz):
        self.matriz = matriz

    def get_dimensao(self):
        return self.dimensao

    def set_dimensao(self, dimensao):
        self.dimensao = dimensao

    def get_total_chamadas_nao_recursivas(self):
        return self.total_chamadas_nao_recursivas

    def set_total_chamadas_nao_recursivas(self, total_chamadas_nao_recursivas):
        self.total_chamadas_nao_recursivas = total_chamadas_nao_recursivas

    def popular_do_arquivo(self, nome_do_arquivo_sudoku):
        try:
            with open(nome_do_arquivo_sudoku, 'r') as arquivo:
                linhas = arquivo.readlines()
                self.dimensao = len(linhas)
                self.matriz = [[0 for _ in range(self.dimensao)] for _ in range(self.dimensao)]
                self.inicializar_matriz()
                for i in range(self.dimensao):
                    numeros = linhas[i].split()
                    for j in range(self.dimensao):
                        self.matriz[i][j] = int(numeros[j])
        except IOError as e:
            print(e)
            return False
        return True

    def inicializar_matriz(self):
        for i in range(self.dimensao):
            for j in range(self.dimensao):
                self.matriz[i][j] = 0

    def exibir_sudoku(self, frase):
        print(frase)
        for i in range(self.dimensao):
            if i % 3 == 0 and i != 0:
                print("-------------------")
            for j in range(self.dimensao):
                if j % (self.dimensao // 3) == 0 and j != 0:
                    print("|", end=" ")
                print(self.matriz[i][j], end=" ")
            print("")

    def numero_esta_na_linha(self, numero, linha):
        return numero in self.matriz[linha]

    def numero_esta_na_coluna(self, numero, coluna):
        return numero in [self.matriz[i][coluna] for i in range(self.dimensao)]

    def numero_esta_no_box(self, numero, linha, coluna):
        linha_box_local = linha - linha % 3
        coluna_box_local = coluna - coluna % (self.dimensao // 3)
        for i in range(linha_box_local, linha_box_local + 3):
            for j in range(coluna_box_local, coluna_box_local + (self.dimensao // 3)):
                if self.matriz[i][j] == numero:
                    return True
        return False

    def numero_esta_no_lugar_certo(self, numero, linha, coluna):
        return not self.numero_esta_na_linha(numero, linha) and not self.numero_esta_na_coluna(numero, coluna) and not self.numero_esta_no_box(numero, linha, coluna)

    def resolve_sudoku(self, qtd_chamadas):
        for linha in range(self.dimensao):
            for coluna in range(self.dimensao):
                if self.matriz[linha][coluna] == 0:
                    for tentando_numero in range(1, self.dimensao + 1):
                        if self.numero_esta_no_lugar_certo(tentando_numero, linha, coluna):
                            self.matriz[linha][coluna] = tentando_numero
                            self.exibir_sudoku("Chamada " + str(qtd_chamadas))
                            self.total_chamadas_nao_recursivas += 1
                            if self.resolve_sudoku(qtd_chamadas + 1):
                                return True
                            else:
                                self.matriz[linha][coluna] = 0
                    return False
        return True
