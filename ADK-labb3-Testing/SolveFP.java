import java.util.LinkedList;
import java.util.Queue;

/**
 * Programmet löser flödesproblemet. 
 * 
 * @author Emilia Rieschel & Charlotta Johnsson
 * @date 2021-11-03
 */


public class SolveFP{
    private Kattio io;
    private int sink;
    private int source;
    private int a;
    private int b;
    private int c;
    private int e;
    private int vertices;
    private int[] parent;
    private LinkedList<Edge>[]edges;
    private Queue<Integer> queue;
    
    
    SolveFP() {
        io = new Kattio(System.in, System.out);
        
        // Läser in antalet noder och kanter samt source och sink.
        vertices = io.getInt();
        source = io.getInt();
        sink = io.getInt();
        e = io.getInt();

        // Skapar arrayen med alla LinkedList som kommer att innehålla alla kanter
        edges = new LinkedList[vertices+1];
        for(int i = 1; i < edges.length; i++){
            edges[i] = new LinkedList<Edge>();
        }

        // Läser in alla kanter och dess kapacitet, samt skapar en Edge för den kanten
        for(int i = 0; i < e; i++){
            a = io.getInt();
            b = io.getInt();
            c = io.getInt();
            edges[a].add(new Edge(b, c, 0, c));
        }

        // Räknar ut det maximala flödet för den givna grafen
        int flow = fordFulkerson();

        // Skriver ut antalet kanter, source, sink, samt totala flödet från source till sink
        io.println(vertices);
        io.println(source + " " + sink + " " + flow);

        // Räknar och skriver ut hur många kanter som ingår i flödet
        int count = 0;
        for(int i = 1; i < edges.length; i++){
            for(Edge e: edges[i]){
                if(e.getFlow() > 0){
                    count++;
                }
            }
        }
        io.println(count);

        // Skriver ut alla kanter som ingår i maximala flödet, samt kanternas flöde
        for(int i = 1; i < edges.length; i++){
            for(Edge e: edges[i]){
                if(e.getFlow() > 0){
                    io.println(i + " " + e.getY() + " " + e.getFlow());
                }
            }
        }
        
        io.flush();
        io.close(); 
    }


    /**
     * Metod för att hitta vilket index i edges[from] som har y-värdet to. Om det inte tidigare finns någon kant
     * mellan from och to, så skapas en ny kant mellan dem.
     * @param from Noden som kanten går från.
     * @param to Noden som kanten går till.
     * @return Indexet för kanten i edges[from]
     */
    public int findIndex(int from, int to){
        int index = 0;

        // Gå igenom alla kanter tills man kommer till rätt, och returnera då indexet.
        for(Edge e: edges[from]){
            if(e.getY() == to){
                return index;
            }
            index++;
        }

        // Om kanten inte fanns, skapa en ny kant och returnera indexet för den kanten.
        edges[from].add(new Edge(to, 0, 0, 0));
        return edges[from].size()-1;
    }

    /**
     * Hittar den kortaste vägen med breddenförstsökning, och sparar den hittade vägen i parent.
     * @param parent Här kommer den hittade vägen att komma.
     * @return True om det hittas en väg, falskt annars.
     */
    public boolean bfs(int[] parent){

        // Skapa en array visited för att hålla koll på vilka noder man redan har besökt.
        boolean[] visited = new boolean[vertices+1];
        for (int i = 0; i < vertices + 1; ++i)
            visited[i] = false;
 
        // Lägg till första noden, det vill säga source, till kön.
        queue.add(source);
        visited[source] = true;
        parent[source] = -1; // Vi var inte på någon annan nod innan.
 
        // Breddenförstsökning, forsätt med loopen tills det inte finns något element kvar i kön.
        while (queue.size() != 0) {
            int u = queue.poll();
 
            // Gå igenom varje kant, det vill säga varje granne till u
            for(Edge e: edges[u]){
                int v = e.getY();

                // Om vi inte tidigare har varit på noden och residualgrafens flöde är större än 0, lägg till noden i kön.
                if(visited[v] == false && e.getRes() > 0){

                    // Om vi har kommit till sink, har vi hittat en stig och vi är färdiga.
                    if (v == sink) {
                        parent[v] = u;
                        return true;
                    }

                    // Annars lägger vi till noden i kön och fortsätter.
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
 
        // Det fanns ingen väg från source till sink, och det returneras därför false. 
        return false;
    }


    public int fordFulkerson(){

        // Vi skapar kön som kommer att användes i breddenförstsökningen.
        queue = new LinkedList<Integer>();

        // parent kommer att spara vägen som hittas i breddenförstsökningen
        parent = new int[vertices+1];
 
        // flödet är 0 från början
        int maxFlow = 0;

        while (bfs(parent)) {
       
            int pathFlow = Integer.MAX_VALUE;
            int v = sink;
            int u;

            // För att vi inte ska behöva räkna ut samma index flera gånger.
            int indexOfElement;
            LinkedList<Integer> indexList = new LinkedList<>();
            
            // Hitta högsta möjliga flödet genom vägen given av parent.
            while(v != source){
                u = parent[v];
                indexOfElement = findIndex(u, v);
                pathFlow = Math.min(pathFlow, edges[u].get(indexOfElement).getRes());
                v = u;
                indexList.add(indexOfElement);
            }

            v = sink;
            int tempflow;
            int indexOfElementU;
            
            // Ändra flödet och residualgrafen enligt givna algoritmen för Ford-Fulkerson
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

            // Lägg till flödet på totala flödet
            maxFlow += pathFlow;

            // Rensa kön som används i breddenförstsökningen
            queue.clear();
        }
 
        // Returnera det totala flödet
        return maxFlow;
    }

    public static void main(String args[]) {
        new SolveFP();
    }
}
