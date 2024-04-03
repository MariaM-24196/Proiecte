package Algoritmi;

public class Dijkstra
{
    int nrVarfuri;
    public Dijkstra(int nrVarfuri)
    {
        this.nrVarfuri = nrVarfuri;
    }

    int minDistance(int distanta[], boolean vizitat[])
    {
        int min = Integer.MAX_VALUE;
        int min_index = -1;
        for (int i = 0; i < nrVarfuri; i++)
            if (!vizitat[i] && distanta[i] < min )
            {
                min = distanta[i];
                min_index = i;
            }
        return min_index;
    }

    void dijkstra(int graf[][], int start)
    {
        int distanta[] = new int[nrVarfuri];
        boolean vizitat[] = new boolean[nrVarfuri];
        for (int i = 0; i < nrVarfuri; i++)
        {
            distanta[i] = Integer.MAX_VALUE;
            vizitat[i] = false;
        }
        distanta[start] = 0;
        for(int i = 0; i < nrVarfuri - 1; i++)
        {
            int varf = minDistance(distanta, vizitat);
            vizitat[varf] = true;
            for(int j = 0; j < nrVarfuri; j++)
            {
                if(!vizitat[j] && graf[varf][j] != 0 && distanta[varf] != Integer.MAX_VALUE && distanta[varf] + graf[varf][j] < distanta[j])
                    distanta[j] = distanta[varf] + graf[varf][j];
            }
        }
        printSolution(distanta);
    }

    private void printSolution(int[] distanta)
    {
        for(int i = 0; i < nrVarfuri; i++)
            System.out.println("Cel mai scurt drum de la 1 la " + (i + 1) + " este: " + distanta[i]);
    }

    public static void main(String[] args)
    {
        int graf[][] = {
                {0, 50, 30, 100, 10},
                {0, 0, 0, 0, 0},
                {0, 5, 0, 50, 0},
                {0, 20, 0, 0, 0},
                {0, 0, 0, 10, 0}
        };
        Dijkstra d = new Dijkstra(5);
        d.dijkstra(graf, 0);
    }
}
