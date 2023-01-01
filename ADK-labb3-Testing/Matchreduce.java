import java.util.*;

/**
 * Löser matchningsproblemet för bipartita grafer.
 * 
 * @author Emilia Rieschel & Charlotta Johnsson
 * @date 2021-11-03
 */

public class MatchReduce {
    private Kattio io;
    private int vertices;
    private int xNodes;
    private int yNodes;
    private int maxFlow;
    private int sink;
    private int source;
    private LinkedList<Edge>[]edges;
    private int numberOfEdges;
    private Queue<Integer> queue;
    private int[] parent;

    void readBipartiteGraph() {

        // Läser in antal hörn och kanter
        int x = io.getInt();
        int y = io.getInt();
        int e = io.getInt();

        numberOfEdges = e + x + y;
        vertices = x + y + 2;
        edges = new LinkedList[vertices+1];
        xNodes = x;
        yNodes = y;
        source = 1;
        sink = vertices;

        //Initialisera linkedlist för alla index i edges
        for(int i = 1; i < edges.length; i++){
            edges[i] = new LinkedList<Edge>();
        }

        //Lägg till kanter från source till alla X-noder
        for(int i = 1; i <= x; i++){
            edges[1].add(new Edge((i+1),1,0,1));
        }

        //Lägg till kanter från alla Y-noder till sink
        for(int i = x+1; i < vertices-1; i++){
            edges[i+1].add(new Edge(vertices, 1, 0, 1));
        }

        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            edges[a+1].add(new Edge((b+1), 1, 0, 1));
        }
    }


    public int findIndex(int from, int to){
        int index = 0;
        for(Edge e: edges[from]){
            if(e.getY() == to){
                return index;
            }
            index++;
        }
        edges[from].add(new Edge(to, 0, 0, 0));
        return edges[from].size()-1;
    }


    public boolean bfs(int[] parent){

        boolean[] visited = new boolean[vertices+1];
        for (int i = 0; i < vertices + 1; ++i)
            visited[i] = false;
 
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;
 
        while (queue.size() != 0) {
            int u = queue.poll();
 
            for(Edge e: edges[u]){
                int v = e.getY();
                if(visited[v] == false && e.getRes() > 0){
                    if (v == sink) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return false;
    }


    public int fordFulkerson(){

        queue = new LinkedList<Integer>();
        parent = new int[vertices+1];
        int maxFlow = 0;

        while (bfs(parent)) {
       
            int pathFlow = Integer.MAX_VALUE;
            int v = sink;
            int u;
            int indexOfElement;
            LinkedList<Integer> indexList = new LinkedList<>();
            
            while(v != source){
                u = parent[v];
                indexOfElement = findIndex(u, v);
                pathFlow = Math.min(pathFlow, edges[u].get(indexOfElement).getRes());
                v = u;
                indexList.add(indexOfElement);
            }

            v = sink;
            int tempflow = 0;
            int indexOfElementU;
            
            while (v != source) {
                indexOfElement = indexList.poll();
                u = parent[v];
                indexOfElementU = findIndex(v, u);
                edges[u].get(indexOfElement).increaseFlow(pathFlow);
                tempflow = edges[u].get(indexOfElement).getFlow();
                edges[v].get(indexOfElementU).setFlow(-tempflow);
                edges[u].get(indexOfElement).changeRes(-tempflow);
                edges[v].get(indexOfElementU).changeRes(tempflow);
                v = u;
            }

            maxFlow += pathFlow;

            queue.clear();
        }
        return maxFlow;
        
    }

    MatchReduce() {

        io = new Kattio(System.in, System.out);

        readBipartiteGraph();
        
        int maxflow = fordFulkerson();

        // Skriv ut antal hörn i X och i Y
        io.println(xNodes + " " + yNodes);

        // Skriv ut antalet kanter funna i matchningen (samma som maxflödet i en bipartit graf)
        io.println(maxflow);

        // Skriv ut alla kanter som ingår i matchningen
        // Gå bara igenom kanterna från en X-nod till en Y-nod
        for(int i = 2; i < edges.length-1-yNodes; i++){
            for(Edge e: edges[i]){
                if(e.getFlow() == 1){
                    io.println((i-1) + " " + (e.getY()-1));
                }
            }
        }

        io.close();
    }

    public static void main(String args[]) {
        new MatchReduce();
    }
}
