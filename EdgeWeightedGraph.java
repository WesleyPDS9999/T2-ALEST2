import java.util.ArrayList;
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
  protected Set<String> vertices;
  private ArrayList<Double> resultados;
  protected int totalVertices;
  protected int totalEdges;

  public EdgeWeightedGraph() {
    graph = new HashMap<>();
    vertices = new HashSet<>();
    marked = new HashSet<>();
    resultados = new ArrayList<>();
    totalVertices = totalEdges = 0;
  }

  public EdgeWeightedGraph(String filename) {//algoritmo precisa ser por profundidade, pode ir calculando de 1 elemento por vez
    this();
    In in = new In(filename);
    String line;
    while((line = in.readLine()) != null) {
      String[] edge = line.split(" ");
      for(int i = 1; i < edge.length-2;i=i+2){
        addEdge(edge[i], edge[edge.length-1], Double.parseDouble(edge[i-1]));
        //valor += getEdge(addEdge(edge[i], edge[edge.length-1], Double.parseDouble(edge[i-1])));
      }
    }
  }

  public void addEdge(String v, String w, double weight) {
    Edge e = new Edge(v, w, weight);
    addToList(v, e);
    addToList(w, e);
    if(!vertices.contains(v)) {
      vertices.add(v);
      totalVertices++;
    }
    if(!vertices.contains(w)) {
      vertices.add(w);
      totalVertices++;
    }
    totalEdges += 2;
  }

  public void dfs1(EdgeWeightedGraph g){

    if(!marked.contains("hidrogenio")){
        //getEdge(Edge[i]);
        dfs(g,"hidrogenio",1);
    }

}

//i = 1 ; i <= getC()-1;i++
//if i== getc-1

private void dfs(EdgeWeightedGraph g, String v, double d) {
  if(((List<Edge>) g.getAdj("hidrogenio")).contains(v)){
    marked.add(v);
  }
for (Edge e : g.getAdj(v)) {
  String w = e.getW();
    if (!marked.contains(w)) {
        d = d * e.getWeight();
        System.out.println(d);
        System.out.println(w);
        System.out.println();
        dfs(g, w, d);
        if(w.equals("ouro")){
        resultados.add(d);
        }
        d = 1;
    }
}
}

public double valortotal(){
  double v = 0;
  for(int i = 0; i < resultados.size();i++){
    v += resultados.get(i);
  }
  return v;
}

  public Iterable<Edge> getAdj(String v) {
    List<Edge> res = graph.get(v);
    if (res == null) res = new LinkedList<>();
    return res;
  }

  public int getTotalVerts() { return totalVertices; }

  public int getTotalEdges() { return totalEdges; }

  public Set<String> getVerts() {
    return vertices;
  }

  public Iterable<Edge> getEdges() {
    Set<Edge> ed = new HashSet<>();
    for (String v : getVerts().stream().sorted().toList()) {
      for (Edge e : getAdj(v)) {
        if (!ed.contains(e)) {
          ed.add(e);
        }
      }
    }
    return ed;
  }

  public String toDot() {
    StringBuilder sb = new StringBuilder();
    sb.append("graph {" + NEWLINE);
    sb.append("rankdir = LR;" + NEWLINE);
    sb.append("node [shape = circle];" + NEWLINE);
    for (Edge e : getEdges())
      sb.append(String.format("%s -- %s [label=\"%.3f\"]", e.getV(), e.getW(), e.getWeight()) + NEWLINE);
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
