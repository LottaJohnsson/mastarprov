/**
 * @author Emilia Rieschel & Charlotta Johnsson
 * @date 2021-11-03
 */


public class Edge {
    private int capacity;
    private int flow;
    private int y;
    private int res;

    Edge(int y, int c, int flow, int res){
        this.y = y;
        this.capacity = c;
        this.flow = flow;
        this.res = res;
    }

    public int getY(){
        return y;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public int getRes(){
        return res;
    }

    public void setCapacity(int c) {
        capacity = c;
    }

    public void changeRes(int f) {
        res = capacity + f;
    }

    public void setFlow(int f) {
        flow = f;
    }

    public void increaseFlow(int f) {
        flow += f;
    }
}
