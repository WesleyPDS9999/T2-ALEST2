import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EdgeWeightedGraph {
  protected static final String NEWLINE = System.getProperty("line.separator");

  protected Map<String, List<Edge>> graph;
  private Set<String> marked;
  private Map<String, String> edgeTo;

  public EdgeWeightedGraph() {
    graph = new HashMap<>();
    marked = new HashSet<>();
    edgeTo = new HashMap<>();
  }

  public EdgeWeightedGraph(String filename) {//algoritmo precisa ser por profundidade, pode ir calculando de 1 elemento por vez
    this();
    double valor = 0;
    In in = new In(filename);
    String line;
    while((line = in.readLine()) != null) {
      String[] edge = line.split(" ");
      for(int i = 1; i < edge.length-2;i=i+2){
        addEdge(edge[i], edge[edge.length-1], Double.parseDouble(edge[i-1]));
        //valor += getEdge(addEdge(edge[i], edge[edge.length-1], Double.parseDouble(edge[i-1])));
        
      }
    }

    System.out.println("teste : " + valor);
    in.close();
  }

  

  public void dfs1(EdgeWeightedGraph g){
    for(String v : g.getVerts()){
        if(!marked.contains(v)){
            //getEdge(Edge[i]);
            dfs(g,v,1);
        }
    }
}

  private double dfs(EdgeWeightedGraph g, String v, double d) {
    marked.add(v);
    for (String w : getVerts().stream().toList()) {
      for (Edge e: getAdj(v)) {
        if (!marked.contains(w)) {
            edgeTo.put(w, v);
            d = d * e.getWeight();
            dfs(g, w,d);
        }
      }
    }
    System.out.println(d);
    return d;
}

  public Edge addEdge(String v, String w, double weight) {
    Edge e = new Edge(v, w, weight);
    addToList(v, e);
    addToList(w, e);
    return e;
  }

  public Iterable<Edge> getAdj(String v) {
    return graph.get(v);
  }

  public Set<String> getVerts() {
    return graph.keySet();
  }

  //achar jeito de pegar o peso da ligação de dois vertices
  public double getEdge(Edge e){
    return e.getWeight();
  }

  public int getV(String v){
    List<Edge> list = graph.get(v);
    return 1;
  }

  public String toDot() {
    // Usa um conjunto de arestas para evitar duplicatas
    Set<String> edges = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    sb.append("graph {"+NEWLINE);
    sb.append("rankdir = LR;"+NEWLINE);
    sb.append("node [shape = circle];"+NEWLINE);
    for(String v: getVerts().stream().sorted().toList()) {
      for (Edge e: getAdj(v)) {
        String edge = e.toString();
        if(!edges.contains(edge)) {
          sb.append(String.format("%s -- %s [label=\"%.0f\"]", e.getV(), e.getW(), e.getWeight()) + NEWLINE);
          edges.add(edge);
        }
      }
    }
    sb.append("}" + NEWLINE);
    return sb.toString();
  }

  // Adiciona um vértice adjacente a outro, criando a lista
  // de adjacências caso ainda não exista no dicionário
  protected List<Edge> addToList(String v, Edge e) {
    List<Edge> list = graph.get(v);
    if (list == null)
      list = new LinkedList<>();
    list.add(e);
    graph.put(v, list);
    return list;
  }
}
