package Algoritmi;

import java.util.*;
class Edge
{
    int start, end, weight;

    public Edge(int start, int end, int weight)
    {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}

public class Kruskal
{
    private static class EdgeComparator implements Comparator<Edge>
    {
        @Override
        public int compare(Edge e1, Edge e2)
        {
            return Integer.compare(e1.weight, e2.weight);
        }
    }

    public static List<Edge> findMST(List<Edge> graph)
    {
        List<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(new EdgeComparator());
        graph.forEach(pq::add);
        DisjointSet ds = new DisjointSet(graph.size());
        while (!pq.isEmpty())
        {
            Edge e = pq.poll();
            if (ds.find(e.start) != ds.find(e.end))
            {
                mst.add(e);
                ds.union(e.start, e.end);
            }
        }
        return mst;
    }

    public static void main(String[] args)
    {
        List<Edge> graph = new ArrayList<>();
        graph.add(new Edge(1,2,1));
        graph.add(new Edge(1,4,4));
        graph.add(new Edge(2,3,2));
        graph.add(new Edge(2,5,4));
        graph.add(new Edge(3,5,5));
        graph.add(new Edge(3,6,6));
        graph.add(new Edge(4,2,6));
        graph.add(new Edge(4,5,3));
        graph.add(new Edge(4,7,4));
        graph.add(new Edge(5,6,8));
        graph.add(new Edge(6,7,3));
        List<Edge> mst = findMST(graph);
        for (Edge e : mst)
            System.out.println("(" + e.start + ", " + e.end + ", " + e.weight + ")");
    }
}

class DisjointSet
{
    private int[] parent, rank;

    public DisjointSet(int size)
    {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++)
        {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x)
    {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y)
    {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;
        if (rank[rootX] > rank[rootY])
        {
            parent[rootY] = rootX;
            rank[rootX] += rank[rootY];
        }
        else
        {
            parent[rootX] = rootY;
            rank[rootY] += rank[rootX];
        }
    }
}