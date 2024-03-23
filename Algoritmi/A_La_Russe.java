package Algoritmi;

public class A_La_Russe
{
    public static int alr(int a, int b)
    {
        int[] X = new int [10000];
        int[] Y = new int [10000];
        X[1] = a;
        Y[1] = b;
        int i = 1;
        while (X[i] > 1)
        {
            X[i + 1] = X[i] / 2;
            Y[i + 1] = Y[i] + Y[i];
            i++;
        }
        int prod = 0;
        while (i > 0)
        {
            if (X[i] % 2 == 1) prod += Y[i];
            i--;
        }
        return prod;
    }

    public static void main(String[] args)
    {
        int a = 52;
        int b = 15;
        System.out.println("Rezultatul inmultirii a la Russe este: " + alr(a, b));
    }
}
