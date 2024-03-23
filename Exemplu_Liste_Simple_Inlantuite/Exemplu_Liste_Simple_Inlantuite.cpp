#include <iostream> 
#include <conio.h> 
#include <stdio.h>

using namespace std;

// Structura de date
struct Nod
{
	int inf; // informatia utila, pe care dorim sa o memoram
	Nod* urm; //adresa urmatorului Nod (un pointer catre o variabila nod
};

// Functia care returneaza numarul de elemente din lista
int nrElemente(Nod* lista)
{
	int nr = 0;
	Nod* aux = lista;
	while (aux != NULL)
	{
		nr++;
		aux = aux->urm;
	}
	return nr;
}

/*
* Adaugare in pozitia N a unui element:
- daca N este 1, atunci adaugarea se va face la inceputul listei;
- daca N este mai mare decat numarul de elemente, atunci se face
  adaugare la sfarsit de lista.
Lista vida va fi simbolizata de valoarea NULL, pentru capul acesteia.
*/
void adauga(Nod*& lista, int val, int N)
{
	// adaugare la inceput => capul isi va schimba valoarea
	if (N == 1 || lista == NULL)
	{
		Nod* aux = new Nod; // se aloca memoria necesara memorarii unei variabile de tip Nod
		aux->inf = val; // se completeaza partea de informatie utila
		aux->urm = lista; // urmatorul element va fi chair vechiul cap al listei
		lista = aux; // noul cap al listei este noul element creat
	}
	else
	{
		int nr = nrElemente(lista); // preiau numarul de elemente din lista
		if (N > nr + 1) // daca pozitia primita ca parametru este mai mare decat
			// numarul de elemente + 1, autnci ii modific valoarea in
			// numar de elemente + 1
			N = nr + 1;
		Nod* tmp = new Nod; // creare nod nou - alocare de memorie
		tmp->inf = val; // completarea informatiei utile

		// se parcurge lista pana la al N-1 -lea element
		Nod* aux = lista;
		for (int i = 1; i < N - 1; i++)
			aux = aux->urm;

		// realizarea inlantuirii
		tmp->urm = aux->urm;
		aux->urm = tmp;
	}
}

// Functie de afisare a elementelor din lista
void afisare(Nod* lista)
{
	Nod* aux = lista; // afisarea incepe de la capul listei
	cout << "Lista este: ";
	while (aux != NULL)
	{
		cout << aux->inf << " ";
		aux = aux->urm;
	}
	if (lista != NULL) cout << "vida." << endl;
	else cout << endl;
}

// Functie pentru eliberarea memoriei
void eliberareMemorie(Nod*& lista)
{
	Nod* aux = lista;
	while (aux != NULL)
	{
		lista = lista->urm; // mutam capul listei pe urmatorul element
		delete aux; // stergem elementul curent
		aux = lista; // primul element va deveni elementul curent
	}
}

/* 
Functia elimin elibereaza memoria alocata elementului de pe pozitia poz
- daca poz este 1, atunci se va elibera de la capul listei;
- daca poz este egal sau mai mare decat nr. de elem., atunci se elimina
  de la sfarsitul listei.
*/
void elimin(Nod* lista, int poz)
{
	Nod* aux = lista;
	if (lista == NULL) return;
	// daca poz este mai mare decat nr. de elem. din lista, atunci
	// lui poz i se va da valoarea pozitiei ultimului element
	if (poz > nrElemente(lista))
		poz = nrElemente(lista);
	if (poz == 1)
	{
		lista = lista->urm; // se muta capul liatei pe elementul urmator
		delete aux; // se sterge capul listei ce e tinut acum de variabila aux
	}
	else
	{
		// pozitionare pe elementul dinaintea pozitiei poz
		for (int i = 1; i < poz - 1; i++)
			aux = aux->urm;
		Nod* tmp = aux->urm;
		aux->urm = tmp->urm;
		delete tmp;
	}
}

// Functie pentru eliminarea elementelor negative din lista
void eliminNegative(Nod* lista)
{
	Nod* aux = lista;
	if (lista == NULL) return;
	for (int i = 1; i < nrElemente(aux) - 1; i++)
		if (aux[i].inf < 0) elimin(aux, i);

}

void main()
{
	Nod* L = NULL; // initial lista L este vida
	int opt = 0;
	do
	{
		// se afiseaza "meniul" programului
		cout << "Operatii" << endl << "------" << endl;
		cout << "1 - Adaugare element" << endl;
		cout << "2 - Eliminare element" << endl;
		cout << "3 - Afisare lista" << endl;
		cout << "4 - Eliminare negative" << endl;
		cout << "0 - Iesire" << endl << "------" << endl;

		// se citeste codul operatiei pe care utilizatorul vrea sa
		// o execute
		cout << "? >: ";
		cin >> opt;

		switch (opt)
		{
			case 1: // Adaugare
			{
				int v, p;
				cout << endl << "Introduceti valoarea: ";
				cin >> v;
				cout << "Introduceti pozitia (lista are " << nrElemente(L) << " elemente): ";
				cin >> p;
				adauga(L, v, p);
				afisare(L);
				break;
			}
			case 2: // Eliminare
			{
				int p;
				cout << endl << "Pozitia (lista are " << nrElemente(L) << " elemente): ";
				cin >> p;
				elimin(L, p);
				afisare(L);
				break;
			}
			case 3: // Afisare
			{
				cout << endl;
				afisare(L);
				break;
			}
			case 4: // Eliminare negative
			{
				eliminNegative(L);
				afisare(L);
				break;
			}
		}
		cout << endl;
		fflush(stdin); // pt. eliminarea bufferului tastaturii
		getch();
	} while (opt != 0);
	eliberareMemorie(L); // se elibereaza memoria alocata la crearea listei
}