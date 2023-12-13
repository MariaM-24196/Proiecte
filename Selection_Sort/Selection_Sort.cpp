#include <bits/stdc++.h>

using namespace std;

int main()
{
	// Declaratii
	int n;
	double v[1000];

	// Citire dimensiune vector
	cout << "Introdu numarul de elemente din vector: ";
	cin >> n;
	cout << endl;

	// Citire elemente vector
	cout << "Introdu elementele vectorului:" << endl;
	for (int i = 0; i < n; i++)
	{
		cout << "v[" << (i + 1) << "]: ";
		cin >> v[i];
	}
	cout << endl;

	// Sortare vector cu Selection Sort
	for(int i = 0; i < n - 1; i++)
		for(int j = i + 1; j < n; j++)
			if (v[i] > v[j])
			{
				double aux = v[i];
				v[i] = v[j];
				v[j] = aux;
			}

	// Afisare vector sortat
	cout << "Vectorul sortat este:\n";
	for (int i = 0; i < n; i++)
		cout << v[i] << "; ";
	cout << endl;
	return 0;
}