package Algoritmi;

public class Euclid
{
    public static int euclid(int m, int n)
    {
        while (n != 0)
        {
            int temp = n;
            n = m % n;
            m = temp;
        }
        return m;
    }

    public static void main(String[] args)
    {
        int n = 102;
        int m = 108;
        System.out.println("Cel mai mare divizor comun dintre " + n + " si " + m
        + " este " + euclid(n, m));
    }
}
