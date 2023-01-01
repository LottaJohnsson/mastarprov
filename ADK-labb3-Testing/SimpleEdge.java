/**
 * @author Emilia Rieschel & Charlotta Johnsson
 * @date 2021-11-03
 */

 
public class SimpleEdge {
    private int from;
    private int to;

    SimpleEdge(int from, int to){
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}