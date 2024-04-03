package Algoritmi;

import java.util.*;

public class Prim
{
    private int V;
    private ArrayList<Edge>[] graph;
    private int[] key;
    private int[] parent;
    private boolean[] mstSet;

    public Prim(int V)
    {
        this.V = V;
        this.graph = new ArrayList[V];
        for (int i = 0; i < V; i++)
            this.graph[i] = new ArrayList<>();
        this.key = new int[V];
        this.parent = new int[V];
        this.mstSet = new boolean[V];
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
    }

    public void addEdge(int u, int v, int weight)
    {
        this.graph[u].add(new Edge(u, v, weight));
        this.graph[v].add(new Edge(v, u, weight));
    }

    public void run(int source)
    {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        key[source] = 0;
        pq.add(new Edge(source, 0, 0));
        while (!pq.isEmpty())
        {
            int u = pq.poll().node;
            if (mstSet[u]) continue;
            mstSet[u] = true;
            for (Edge edge : graph[u])
            {
                int v = edge.dest;
                if (!mstSet[v] && edge.weight < key[v])
                {
                    parent[v] = u;
                    key[v] = edge.weight;
                    pq.add(new Edge(v, u, edge.weight));
                }
            }
        }
    }

    public void printMST()
    {
        for (int i = 1; i < V; i++)
        {
            if (parent[i] == -1) continue;
            System.out.println(parent[i] + " - " + i + " : " + key[i]);
        }
    }

    private static class Edge
    {
        int node;
        int dest;
        int weight;

        public Edge(int node, int dest, int weight)
        {
            this.node = node;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public static void main(String[] args)
    {
        Prim prim = new Prim(9);
        prim.addEdge(1, 2, 1);
        prim.addEdge(1, 4, 4);
        prim.addEdge(2, 3, 2);
        prim.addEdge(2, 4, 6);
        prim.addEdge(2, 5, 4);
        prim.addEdge(3, 5, 5);
        prim.addEdge(3, 6, 6);
        prim.addEdge(4, 5, 3);
        prim.addEdge(4, 7, 4);
        prim.addEdge(5, 6, 8);
        prim.addEdge(5, 7, 7);
        prim.addEdge(6, 7, 3);
        prim.run(1);
        prim.printMST();
    }
}