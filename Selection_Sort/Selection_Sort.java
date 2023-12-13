import java.util.*;

class Selection_Sort
{
    public static void main(String[] args)
    {
        // Declaratii
        Scanner tastatura = new Scanner(System.in);
        int n;
        double []v = new double[1000];

        // Citire dimensiune vector
        System.out.print("Introdu dimansiunea vectorului: ");
        n = tastatura.nextInt();
        System.out.println();

        // Citire elemente vector
        System.out.println("Introdu elementele vectorului:");
        for (int i = 0; i < n; i++)
        {
            System.out.print("v[" + (i + 1) + "]: ");
            v[i] = tastatura.nextDouble();
        }
        System.out.println();

        // Sortare vector cu Selection Sort
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (v[i] > v[j])
                {
                    double aux = v[i];
                    v[i] = v[j];
                    v[j] = aux;
                }

        // Afisare vector sortat
        System.out.println("Vectorul sortat este:");
        for (int i = 0; i < n; i++)
            System.out.print(v[i] + "; ");
        System.out.println();
    }
}
