Iulius Curt 343C3
Radu Pasea 343C3

Proiect IDP Etapa I

0. Source Control
	URL-ul proiectului pentru GitHub:
	https://github.com/rpasea/IDP.git

1. Compilare si Rulare

	O comanda ant in radacina arhivei ar trebui sa fie suficienta.
	
	Date acces intr-un rol de cumparator:
		username: gicu
		rol: Cumparator
		password: password
	Date acces intr-un rol de vanzator:
		username: Lulache
		rol: Vanzator
		password: password
		
	Daca se utilizeaza contul de cumparator ( al lui gicu), porneste un
simulator minimal al GUI-ului.
	Nu am realizat un simulator din perspectiva vanzatorului si trebuie testata
manual. Oricum, fiind etapa dedicata GUI-ului, elementele se comporta asemanator
in ambele cazuri, asa ca speram sa nu conteze atat de mult.

2. Arhitectura generala

	Am realizat o arhitectura arborescenta.
	Mediatorul central reprezinta radacina. Acesta primeste mesaje de la 
mediatorii modulelor.
Ex GUI:
		Mediator central
				|
		Mediator controllere
		|					|
Controller fereastra	Controller fereastra principala
		login							|
		|								|
View fereastra login			View fereastra principala

	Daca vrem sa trimitem input de la utilizator, controller-ul asociat ferestrei
va trimite un mesaj catre mediatorul controllerelor care il va forwarda 
mediatorului central.

	In general, elementele specializate trimit mesaje spre mediatorul central.
	Acesta le traduce in comenzi si le executa. Comenzile se considera ca 
facand parte din mediatorul central.

	Am folosit acest tip de arhitectura datorita flexibilitatii. Astfel, pentru
a adauga functionalitate trebuie create clase noi care incapsuleaza atat aceasta
noua functionalitate, cat si informatia primita de la componente ce este necesara
implementarii sale. De asemenea se evita supraincarcarea clasei Mediator cu 
metode ( blob class - no, no! ).
	De asemenea, putem face foarte usor ca niste tipuri de comenzi sa fie rulate
pe alte thread-uri.


3. Proiectarea partilor componente

	1. View-ul principal:
		Un Jtable de servicii, fiecare serviciu avand intr-o celula un alt JTable
		de ofertanti/interesanti. JTable-urile imbricate declanseaza dataChanged 
		event-ul modelului tabelului principal. Cele doua tabele folosesc aceeasi
		clasa pt model (AHTableModel).
		
	2. Meniurile contextuale/servicii:
		Am utilizat State pattern: in stare sunt retinute informatii despre tipul
		de meniu, despre informatiile serviciului/ofertei/cererii selectate.
		
	3. Tranzactiile:
		Tranzactiile in desfasurare sunt detinute de mediator. Acestea au 4 stari
		(se considera o tranzactie inceputa in starea de Offer Accepted).
		Am folosit pattern-ul Observer pentru a propaga modificarile starii
		tranzactiei in GUI.
		
	4. Datele:
		Pentru a simula baza de date am folosit un fisier XML obtinut cu ajutorul
		unei biblioteci externe de serializare/deserializare a obiectelor in XML
		(bineinteles, biblioteca este inclusa). Am folosit aceasta solutie 
		deoarece usureaza trecerea la o baza de date reala. Conectorul la baza
		de date/serviciul web trebuie sa implementeze metodele interfetei DataManager.
		
Speram sa se ia in considerare mai mult arhitectura proiectului si realizarea
functionalitatii pentru aceasta etapa. Talentul nostru la a realiza interfete
Swing este limitat.

		
Deoarece am intrat in criza de timp, nu am comentat corespunzator sursele, asa
ca mai multe detalii vom putea da doar la prezentarea temei.