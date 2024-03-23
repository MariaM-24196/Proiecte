package Algoritmi;

public class Sortare
{
    // Sortare prin selectie
    public static void selectionSort(int[] A)
    {
        for(int i=0; i<A.length-1 ;i++)
        {
            int minj = i;
            int minx = A[i];
            for(int j=i+1; j<A.length; j++)
            {
                if(A[j]<minx)
                {
                    minj = j;
                    minx = A[j];
                }
            }
            A[minj] = A[i];
            A[i] = minx;

        }
    }

    // Sortare prin insertie
    public static void insertionSort(int[] A)
    {
        int n = A.length;
        for (int j = 2; j < n; j++)
        {
            int key = A[j];
            int i = j - 1;
            while (i > 0 && A[i] > key)
            {
                A[i + 1] = A[i];
                i--;
            }
            A[i + 1] = key;
        }
    }

    public static void main(String[] args)
    {
        int []A = new int[]{5, 3, 6, 2, 1, 4};
        System.out.println("Sirul sortat este:");
        System.out.print("- prin sortare: ");
        selectionSort(A);
        for (int i = 0; i < A.length; i++)
            System.out.print(A[i] + " ");
        System.out.println();
        System.out.print("- prin insertie: ");
        insertionSort(A);
        for (int i = 0; i < A.length; i++)
            System.out.print(A[i] + " ");
    }
}