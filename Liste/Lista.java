package Liste;

public interface Lista
{
    public int elem(int poz) throws Exception;
    public int nrElemente();
    public boolean esteVida();
    public boolean estePlina();
    public boolean adauga(int elem);
    public boolean adauga(int elem, int poz);
    public boolean elimina(int poz);
    public int schimbaElem(int elem, int poz) throws Exception;
}
