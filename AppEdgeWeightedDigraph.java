public class AppEdgeWeightedDigraph
{
  public static void main(String[] args) {
    /* 
    Graph g = new Graph();
    g.addEdge("0", "1");
    g.addEdge("0", "2");
    g.addEdge("2", "1");
    */
    EdgeWeightedDigraph g = new EdgeWeightedDigraph("casoa320.txt");

    for (String v : g.getVerts()) {
      System.out.print(v + ": ");
      for (Edge e : g.getAdj(v))
        System.out.print(e + " ");
      System.out.println();
    }

    System.out.println();
    System.out.println(g.toDot());
    g.dfs1(g);
    System.out.println("O valor é: " + g.valortotal("ouro"));
    //System.out.println(g.tabela()); //valor de hidrogenios necessarios para cada elemento
  }
}
