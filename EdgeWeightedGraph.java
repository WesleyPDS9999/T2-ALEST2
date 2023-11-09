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
  private Map<String, String> edgeTo;
  private ArrayList<Double> resultados;

  public EdgeWeightedGraph() {
    graph = new HashMap<>();
    marked = new HashSet<>();
    edgeTo = new HashMap<>();
    resultados = new ArrayList<>();
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
    
        if(!marked.contains("hidrogenio")){
            //getEdge(Edge[i]);
            dfs(g,"hidrogenio",1);
        }
    
}

//i = 1 ; i <= getC()-1;i++
//if i== getc-1

  private void dfs(EdgeWeightedGraph g, String v, double d) {
    if(g.getAdj2("hidrogrenio")){
      marked.add(v);
    }
    for (Edge e : g.getAdj(v)) {
      String w = e.getW();
        if (!marked.contains(w)) {
            edgeTo.put(w, v);
            d = d * e.getWeight();
            System.out.println(d);
            System.out.println(w);
            System.out.println();
            dfs(g, w, d);
        }
    }
    resultados.add(d);
    d = 1;
  }

  public double valortotal(){
    double v = 0;
    for(int i = 0; i < resultados.size();i++){
      v += resultados.get(i);
    }
    return v;
  }

  public Edge addEdge(String v, String w, double weight) {
    Edge e = new Edge(v, w, weight);
    addToList(v, e);
    addToList(w, e);
    return e;
  }

  public List<Edge> getAdj(String v) {
    return graph.get(v);
  }

  public boolean getAdj2(String v) {
    return graph.containsKey(v);
  }

  public Set<String> getVerts() {
    return graph.keySet();
  }

  //achar jeito de pegar o peso da ligação de dois vertices
  public double getEdge(Edge e){
    return e.getWeight();
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
