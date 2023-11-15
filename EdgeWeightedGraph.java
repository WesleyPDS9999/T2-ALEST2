import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EdgeWeightedGraph {
  protected static final String NEWLINE = System.getProperty("line.separator");

  protected Map<String, List<Edge>> graph;
  private Set<String> marked;//pode ser utilizado na otmização
  protected Set<String> vertices;
  private Map<String, BigInteger> resultados;
  protected int totalVertices;
  protected int totalEdges;

  public EdgeWeightedGraph() {
    graph = new HashMap<>();
    vertices = new HashSet<>();
    marked = new HashSet<>();
    resultados = new HashMap<>();
    totalVertices = totalEdges = 0;
  }

  public EdgeWeightedGraph(String filename) {
    this();
    In in = new In(filename);
    String line;
    while((line = in.readLine()) != null) {
      String[] edge = line.split(" ");
      for(int i = 1; i < edge.length-2;i=i+2){
        addEdge(edge[i], edge[edge.length-1], Integer.parseInt(edge[i-1]));
      }
    }
  }

  public void addEdge(String v, String w, int weight) {
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
    String v = "hidrogenio";
    resultados.put(v, BigInteger.valueOf(1));
    dfs(g,v,BigInteger.valueOf(1));
  }

  private void dfs(EdgeWeightedGraph g, String v, BigInteger d) {
  for (Edge e : g.getAdj(v)) {
    String w = e.getW();
    //String pai = e.getV();    
    BigInteger aux = d.multiply(BigInteger.valueOf(e.getWeight()));
    //System.out.println(d);
    //System.out.println(w);
    //System.out.println();
    if(resultados.containsKey(w)){// se ja apresenta um valor associado com tal elemento
      BigInteger novoValor = aux.add(resultados.get(w));//soma com o valor que ja esta mapeado com o elemento
      resultados.put(w,novoValor);//add no map com novo valor
    }else{
      resultados.put(w,aux);
    }
    dfs(g, w, aux);
    //d = resultados.get(pai);
    }
  }

  public Map tabela(){
    return resultados;
  }
  public BigInteger valortotal(String elemento){
    return resultados.get(elemento);
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
      sb.append(String.format("%s -- %s [label=\"%d\"]", e.getV(), e.getW(), e.getWeight()) + NEWLINE);
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
