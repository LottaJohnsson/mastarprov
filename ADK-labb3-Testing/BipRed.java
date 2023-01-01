import java.util.*;

/**
 * Programmet löser matchningsproblemet med hjälp av en svart låda som löser flödesproblemet. 
 * 
 * @author Emilia Rieschel & Charlotta Johnsson
 * @date 2021-11-03
 */

 
public class BipRed {
    private Kattio io;
    private int vertices;
    private int xNodes;
    private int yNodes;
    private int maxFlow;
    private int sink;
    private int source;
    private LinkedList<Edge>[]edges;
    private int numberOfEdges;
    private LinkedList<SimpleEdge> result;

    void readBipartiteGraph() {

        // Läs antal hörn och kanter
        int x = io.getInt();
        int y = io.getInt();
        int e = io.getInt();

        // Spara globala variabler
        numberOfEdges = e + x + y;
        vertices = x + y + 2; //För vi lägger till s och t
        edges = new LinkedList[x+y+2]; //en array av LinkedList med plats för alla noder förutom sink
        xNodes = x;
        yNodes = y;
        source = 1;
        sink = vertices;

        // Skapar en LinkedList för varje nod, där kommer kanter till noden att vara
        for(int i = 1; i < edges.length; i++){
            edges[i] = new LinkedList<Edge>();
        }

        // Skapar en kant mellan source och alla X-noder
        for(int i = 1; i <= x; i++){
            edges[1].add(new Edge((i+1),1,0,0));
        }

        // Skapar en kant mellan alla Y-noder och sink
        for(int i = x+1; i < vertices-1; i++){
            edges[i+1].add(new Edge(vertices, 1, 0, 0));
        }

        // Läs in kanterna och skapa en Edge för kanten
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            edges[a+1].add(new Edge((b+1), 1, 0, 0));
        }
    }


    void writeFlowGraph() {

        // Skriv ut antal hörn och kanter samt källa och sänka
        io.println(vertices);
        io.println((source) + " " + (sink));
        io.println(numberOfEdges);

        // Skriv ut alla kanter och dess kapacitet
        for (int i = 1; i < edges.length; i++){
            for(Edge e: edges[i]){
                io.println(i + " " + e.getY() + " " +  e.getCapacity());
            }
        }

        io.flush();
    }


    void readMaxFlowSolution() {

        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int totalflow = io.getInt();
        int e = io.getInt();

        // Spara global variabel
        maxFlow = totalflow;

        // Skapa lista för resultatet
        result = new LinkedList<>();

        // Läs in alla kanter och flödet
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();

            // Om kanten inte är från source eller till sink och flöder är 1, lägg till i resultatlistan
            if(f == 1 && a != source && b != sink){
                result.add(new SimpleEdge((a-1),(b-1)));
            }
        }
    }
    

    void writeBipMatchSolution() {
        
        // Skriv ut antal hörn och storleken på matchningen
        io.println(xNodes + " " + yNodes);
        io.println(maxFlow);

        // Skriv ut kanterna i matchningen
        for(SimpleEdge e: result){
            io.println(e.getFrom() + " " + e.getTo());
        }
    }


    BipRed() {

        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        writeFlowGraph();
        
        readMaxFlowSolution();

        writeBipMatchSolution(); 

        io.close();    
    }

    public static void main(String args[]) {
        new BipRed();
    }
}
