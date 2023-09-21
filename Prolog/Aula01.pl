disciplina(redes,sylvio).
disciplina(simulacao,mirkos).
disciplina(estruturas,zamberlan).
disciplina(so,ana).
disciplina(ia,mirkos).
disciplina(ia,zamberlan).
disciplina(dd,zamberlan).

area(redes,tecnologia).
area(simulacao,tecnologia).
area(estruturas,base).
area(so,base).
area(ia,tecnologia).
area(dd,complementar).

homem(sylvio).
homem(mirkos).
homem(zamberlan).

mulher(M) :- disciplina(_,M),
    		not(homem(M)).

qualArea(P,A) :- disciplina(D,P),
    			area(D,A).

arco(a,b).
arco(a,f).
arco(b,c).
arco(c,d).
arco(d,e).
arco(f,g).
arco(g,c).
arco(g,e).

caminho(O,D) :- arco(O,D).
caminho(O,D) :- arco(O,I),
    			caminho(I,D).

progenitor(fernando,pedro).
progenitor(pedro,jair).
progenitor(jair,matheus).

ansestal(B,BN) :- progenitor(B,BN).
ansestal(B,BN) :- progenitor(B,F),
    			ansestal(F,BN).

