import java.util.*;

public class ChessboardGenerator {
    private int verticalMoves;
    private int horizontalMoves;
    private int size;
    boolean[][] visited; 
    char chessboard[][];

    static class pair{
        int first, second;
        
        public pair(int first, int second) 
        {
            this.first = first;
            this.second = second;
        }   
    }

    public ChessboardGenerator(int verticalMoves, int horizontalMoves, int size) {
        this.verticalMoves = verticalMoves;
        this.horizontalMoves = horizontalMoves;
        this.size = size;
        this.visited = new boolean[size][size];
        this.chessboard = new char[size][size];
    }

    public boolean isLegalMove(int positionX, int positionY) {
        return (positionX < size && positionY < size && positionX >= 0 && positionY >= 0);

    }

    public void colorPath(int startColor, Stack<pair> path){
        int colorindex = startColor;
        while (!path.empty()){
            pair curr = path.pop();
            int row = curr.first;
            int col = curr.second;
            //System.out.println("row " + row + " col " + col);

            if (colorindex % 2 == 0){
                chessboard[row][col] = 'X';
                //System.out.println("X");

            }
            else{
                chessboard[row][col] = 'O';
                //System.out.println("O");
            }
            colorindex++;
        }
        /* for ( int i = 0; i<size; i++){
            for ( int  j= 0; j<size; j++){
                System.out.print(chessboard[i][j] + " ");
            }
            System.out.println();
        } */
        
        /* System.out.println();
        System.out.println(); */

    }

    public boolean DFS(int row, int col){

        //System.out.println("DFS starting at " + row + " " + col + "");
        
        // Initialize a stack of pairs and
        // push the starting cell into it
        Stack<pair> st = new Stack<pair>();
        Stack <pair> path = new Stack<pair>();
        st.push(new pair(row, col));
 

        while (!st.empty()) {
            // Pop the top pair
            pair curr = st.pop();
            
            row = curr.first;
            col = curr.second;

            int[][] moves = possibleMoves(row, col);

        /*  //print possible moves
            System.out.println("current square " + row + " "+ col);
            for (int i = 0; i <moves.length; i++) {
                /* System.out.println("possible move " + moves[i][0] + " " + moves[i][1] + " " + isLegalMove(moves[i][0], moves[i][1]) ); // + " visited " + visited[moves[i][0]][moves[i][1]]);
                if (isLegalMove(moves[i][0], moves[i][1])){
                    System.out.println("possible move " + moves[i][0] + " " + moves[i][1] + " visited " + visited[moves[i][0]][moves[i][1]]);
                }
            }
            System.out.println(); */

            //if no legal moves we have reached an end point, 
            boolean reachedEnd = true;
            

            if (!visited[row][col] ){
                visited[row][col] = true;
                path.push(new pair(curr.first, curr.second));
                //System.out.println("pushing " + row + " " + col + "\n");
                System.out.println("current square " + row + " "+ col);

                for (int i = 0; i <moves.length; i++) {
                    if (isLegalMove(moves[i][0], moves[i][1])) {
                        //System.out.println("current square " + row + col + "\n" +   "possible move " + moves[i][0] + " " + moves[i][1] + "\n");

                        if (visited[moves[i][0]][moves[i][1]] == false){ //unvisited square
                            //System.out.println("pushing " + moves[i][0] + " " + moves[i][1] + "\n");
                            st.push(new pair(moves[i][0], moves[i][1]));
                            reachedEnd = false;
                        }

                        else{ //visited square
                            //System.out.println("The move is already visited and move is " + moves[i][0] + " " + moves[i][1] + "\n");
                            if (chessboard[moves[i][0]][moves[i][1]] == 'X') {
                                if (chessboard[row][col] == 'X'){
                                    System.out.println("Ahaaaaaa X");
                                // return false;
                                }
                                //System.out.print("path empty " + path.empty() + "\n");
                            /*  if (path.empty()){
                                    System.out.println("X: " + chessboard[row][col]);
                                    if (chessboard[row][col] == 'X'){
                                        System.out.println("error");
                                        return false;
                                    }
                                } */
                                colorPath(1, path);
                                path = new Stack<pair>();
                                reachedEnd = false;
                            }
                            else if (chessboard[moves[i][0]][moves[i][1]] == '0'){
                                if (chessboard[row][col] == '0'){
                                    System.out.println("Ahaaaaaa O");
                                    //return false;
                                }
                                /* if (path.empty()){
                                    System.out.println("0: " + chessboard[row][col]);
                                    if (chessboard[row][col] == '0'){
                                        System.out.println("error");
                                        return false;
                                    }
                                } */
                                colorPath(0, path);
                                path = new Stack<pair>();
                                reachedEnd = false;
                            
                            }         
                        }                
                        if (reachedEnd){ //if no legal moves we have reached an end point,
                            //System.out.println("reached end");
                            colorPath(0, path);
                            //print stack
                            path = new Stack<pair>();
                        }    
                    }   
                }
            }
        }
        return true;
    }

    /* public void BFS(int row, int col){
     
        // Stores indices of the matrix cells
        Queue<move > q = new LinkedList<>();
    
        q.add(new move(row, col));
        //System.out.println("The first move is: " + row + " " + col);
        //System.out.println("visited length " + visited.length);
        visited[row][col] = true;
        chessboard[row][col] = 'X';
    
        // Iterate while the queue
        // is not empty
        while (!q.isEmpty())
        {
            move square = q.peek();
            int x = square.first;
            int y = square.second;
    
            q.remove();

            int[][] moves = possibleMoves(x, y);

            for (int i = 0; i <moves.length; i++) {
                if (isLegalMove(moves[i][0], moves[i][1])) {
                    if (visited[moves[i][0]][moves[i][1]] == false){
                        q.add(new move(moves[i][0], moves[i][1]));
                        visited[moves[i][0]][moves[i][1]] = true;
                        chessboard[row][col] = 'X';
                    }
                    else {
                        chessboard[row][col] = 'O';
                        //if the move is already visited, then we need to check if the move is a parent of the current move
                        //System.out.println("The move is already visited");
                    }
                    
                }
            }
    
        }
    } */

    public int[][] possibleMoves(int positionX, int positionY) {
        int possibleMoves[][] = new int[8][2];
        possibleMoves[0][0] = positionX + verticalMoves;
        possibleMoves[0][1] = positionY + horizontalMoves;

        possibleMoves[1][0] = positionX + verticalMoves;
        possibleMoves[1][1] = positionY - horizontalMoves;

        possibleMoves[2][0] = positionX - verticalMoves;
        possibleMoves[2][1] = positionY + horizontalMoves;

        possibleMoves[3][0] = positionX - verticalMoves;
        possibleMoves[3][1] = positionY - horizontalMoves;

        possibleMoves[4][0] = positionX + horizontalMoves;
        possibleMoves[4][1] = positionY + verticalMoves;

        possibleMoves[5][0] = positionX + horizontalMoves;
        possibleMoves[5][1] = positionY - verticalMoves;

        possibleMoves[6][0] = positionX - horizontalMoves;
        possibleMoves[6][1] = positionY + verticalMoves;

        possibleMoves[7][0] = positionX - horizontalMoves;
        possibleMoves[7][1] = positionY - verticalMoves;
        
        return possibleMoves;
  
    }
    
    public char[][] createChessboard(){
        
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if (!DFS(i, j)){
                    System.out.println("No solution");
                    return null;
                }               
            }
        }
        return chessboard;
    }    

    public static void main(String[] args){
        int KMoves = 1;
        int IMoves = 1;
        int size= 4 ;
        ChessboardGenerator chessboardGenerator = new ChessboardGenerator(KMoves, IMoves, size);
        char[][] chessboard = chessboardGenerator.createChessboard();
        if (chessboard == null){
            System.out.println("No solution");
        }
        else{
            System.out.println("Solution found");
            for ( int i = 0; i<size; i++){
                for ( int  j= 0; j<size; j++){
                    System.out.print(chessboard[i][j] + " ");
                }
                System.out.println();
            }
        }

        //print chessboard
        
    }
}





