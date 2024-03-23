package Algoritmi;

public class Fibonacci
{
    // Fibonacci Recursiv
    public static int Recursiv(int n)
    {
        if (n < 2) return n;
        else return Recursiv(n-1) + Recursiv(n-2);
    }

    // Fibonacci Iterativ
    public static int Iterativ(int n)
    {
        int i = 0, j = 1, s = 1;
        for (int k = 1; k <= n; k++)
        {
            i = j;
            j = s;
            s = i + j;
        }
        return i;
    }

    public static void main(String[] args)
    {
        int n = 7;
        System.out.println("Al " + n + "-lea termen al sirului lui Fibonacci este:");
        System.out.println("- Recursiv: " + Recursiv(n));
        System.out.println("- Iterativ: " + Iterativ(n));
    }
}
