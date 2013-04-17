Networking


1. Infrastructura

Folosim un model multi-threaded.

Clasa in care este implementat modulul de networking este NetworkCommunicator.

Threadul principal = selectorul.
	Are 4 roluri:
		- select
		- tine o coada prin care alte thread-uri pot cere modificarea operatiilor
		permise ale une chei
		- realizeaza functiile de conectare la alt server socket si de acceptare
		a conexiunilor catre server socket-ul sau.
		- trimite deleaga functiile de read si de write pe chei unor alte 
		thread-uri dintr-un threadpool ce apartine clasei.

Worker threads - numar configurabil (momentan hardcodat)
	2 roluri:
		- realizeaza functiile de read si write pe chei
		- odata ce primesc mesaje complete, le deserializeaza si le trateaza.

2. Mesajele

Mesajele de networking sunt foarte asemanatoare cu "mesajele" interne ale
aplicatiei (de fapt niste clase care incapsuleaza informatie - asa ca mesaje).

Pentru serializare, primii 4 octeti din array-ul serializat specifica lungimea
mesajului, apoi urmeaza atributele. Daca acestea nu au o dimensiune standard,
se utilizeaza aceeasi metoda (mai intai lungimea, apoi mesajul).

Pentru a nu fi diferente intre mesaje propriu-zise si fisiere, trimiterea se 
face din stream-uri (nu e necesara citirea intregului fisier in memorie).
Folosim un stream derivat din FileInputStream care actualizeaza progresul
tranzactiei reprezentate de fisier.

Pentru receptie se foloseste o clasa ce buffereaza astfel de mesaje si tine
o coada cu mesajele formate complet si deserializate(clasa MessageBuffer).
In aceasta clasa este si hook-ul pentru a actualiza tranzactia la primire
(destul de greu de facut altfel, modulul de networking nu face absolut nicio
diferenta intre mesaje, fisiere, rolul utilizatorului. Se ocupa doar de net).

