public class Test{


    public void printChess(char[][] chess){
        for (int i =0; i<chess.length; i++){
            for (int j = 0; j<chess.length; j++){
                System.out.print(chess[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean testChess(int size, int iMoves, int kMoves){
        ChessboardGenerator chess = new ChessboardGenerator(iMoves, kMoves, size);
        char[][] chessboard = chess.createChessboard();

        for (int i = 0; i <size; i++){
            for (int j = 0; j<size; j++){
                int [][] possibleMoves = chess.possibleMoves(i, j);
                for (int k = 0; k<8; k++){
                    if (possibleMoves[k][0] >= 0 && possibleMoves[k][0] < size && possibleMoves[k][1] >= 0 && possibleMoves[k][1] < size){
                        if (chessboard[possibleMoves[k][0]][possibleMoves[k][1]] == chessboard[i][j]){
                            System.out.println("x = " + i + " y = " + j );
                            System.out.println();
                            printChess(chessboard);
                            return false;
                        }
                    }
                }

            }
        }
        return true;

    }
    

    public static void main(String[] args) {
        Test test = new Test();

        int n = 8;
        for (int i = 2; i <= n; i++) {
            for (int j=1; j<i-1; j++ ){
                for (int k = 1; k<i-1; k++){
                    if (!test.testChess(i, j, k)){
                        System.out.println("size = " + n + " iMoves = " + j + " kMoves = " + k);
                    }
                }
            }
        
        }
    }
}

