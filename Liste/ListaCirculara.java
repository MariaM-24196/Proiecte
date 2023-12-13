package Liste;

public class ListaCirculara extends Exception implements Lista
{
    private int capacitate;
    private int[] lista;
    private int nrElemente;

    public ListaCirculara(int capacitate)
    {
        this.capacitate = capacitate;
        this.lista = new int[capacitate];
        this.nrElemente = 0;
    }

    public ListaCirculara()
    {
        this.capacitate = 10;
    }

    @Override
    public int elem(int poz) throws Exception
    {
        if (poz < 1 || poz > nrElemente)
            throw new Exception("Pozitie invalida");
        int index = (poz - 1) % capacitate;
        return lista[index];
    }

    @Override
    public int nrElemente()
    {
        return nrElemente;
    }

    @Override
    public boolean esteVida()
    {
        return nrElemente == 0;
    }

    @Override
    public boolean estePlina()
    {
        return nrElemente == capacitate;
    }

    @Override
    public boolean adauga(int elem)
    {
        if (estePlina()) return false;
        lista[nrElemente % capacitate] = elem;
        nrElemente++;
        return true;
    }

    @Override
    public boolean adauga(int elem, int poz)
    {
        if(poz <0 || poz >nrElemente || estePlina())
            return false;
        int index = (poz - 1) % capacitate;
        for (int i = nrElemente; i > index; i--)
            lista[i % capacitate] = lista[(i - 1) % capacitate];
        lista[index] = elem;
        nrElemente++;
        return true;
    }

    @Override
    public boolean elimina(int poz)
    {
        if(poz <0 || poz >nrElemente || esteVida())
            return false;
        int index = (poz - 1) % capacitate;
        for (int i = index; i < nrElemente - 1; i++)
            lista[i % capacitate] = lista[(i + 1) % capacitate];
        nrElemente--;
        return true;
    }

    @Override
    public int schimbaElem(int elem, int poz) throws Exception
    {
        if (poz < 1 || poz > nrElemente)
            throw new Exception("Pozitie invalida");
        int index = (poz - 1) % capacitate;
        int elemVechi = lista[index];
        lista[index] = elem;
        return elemVechi;
    }

    public static void main(String[] args)
    {
        ListaCirculara lista = new ListaCirculara(5);

        System.out.println("Adauga elemente:");
        for (int i = 1; i <= 7; i++)
        {
            lista.adauga(i);
            System.out.println("Elementul adaugat: " + i);
        }

        System.out.println("\nAfiseaza elementele:");
        for (int i = 1; i <= lista.nrElemente(); i++)
        {
            try
            {
                System.out.println("Elementul de pe pozitia " + i + ": " + lista.elem(i));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("\nElimina element de pe pozitia 3:");
        lista.elimina(3);
        for (int i = 1; i <= lista.nrElemente(); i++)
        {
            try
            {
                System.out.println("Elementul de pe pozitia " + i + ": " + lista.elem(i));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("\nSchimba element de pe pozitia 2 cu 10:");
        try
        {
            int elemVechi = lista.schimbaElem(10, 2);
            System.out.println("Elementul vechi: " + elemVechi);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("\nAfiseaza elementele dupa schimbare:");
        for (int i = 1; i <= lista.nrElemente(); i++)
        {
            try
            {
                System.out.println("Elementul de pe pozitia " + i + ": " + lista.elem(i));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
