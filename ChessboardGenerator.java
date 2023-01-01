import java.util.*;

public class ChessboardGenerator { //Rename from C

  private int IMoves;
  private int KMoves;
  private int size;
  boolean[][] visited;
  char chessboard[][];

  static class ChessboardSquare {

    int row, col;

    public ChessboardSquare(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public ChessboardGenerator(int IMoves, int KMoves, int size) {
    this.IMoves = IMoves;
    this.KMoves = KMoves;
    this.size = size;
    this.visited = new boolean[size][size];
    this.chessboard = new char[size][size];
  }

  public boolean isLegalMove(int positionX, int positionY) {
    return (
      positionX < size && positionY < size && positionX >= 0 && positionY >= 0
    );
  }

  public void colorPath(int startColor, Stack<ChessboardSquare> path) {
    int colorindex = startColor;

    while (!path.empty()) {
      ChessboardSquare currentSquare = path.pop();
      int row = currentSquare.row;
      int col = currentSquare.col;

      if (colorindex % 2 == 0) {
        chessboard[row][col] = 'X';
      } else {
        chessboard[row][col] = 'O';
      }
      colorindex++;
    }
  }

  public boolean DFS(int row, int col) {
    Stack<ChessboardSquare> stack = new Stack<ChessboardSquare>();
    Stack<ChessboardSquare> pathToBeColored = new Stack<ChessboardSquare>();
    stack.push(new ChessboardSquare(row, col));

    while (!stack.empty()) {
      ChessboardSquare currentSquare = stack.pop();

      row = currentSquare.row;
      col = currentSquare.col;

      if (visited[row][col] == true) {
        continue;
      }

      visited[row][col] = true;

      pathToBeColored.push(currentSquare);

      boolean reachedEnd = true;
      ChessboardSquare possibleMoves[] = possibleMoves(row, col);

      for (int i = 0; i < possibleMoves.length; i++) {
        int possibleRow = possibleMoves[i].row;
        int possibleCol = possibleMoves[i].col;

        if (isLegalMove(possibleRow, possibleCol)) {
          //unvisited square
          if (visited[possibleRow][possibleCol] == false) {
            stack.push(possibleMoves[i]);
            reachedEnd = false;
          }
          //visited square
          else {
            //if uncolored square do nothing
            char color = chessboard[possibleRow][possibleCol];

            //if colored square
            if (color == 'X' || color == 'O') {
              if (chessboard[row][col] == color) {
                return false;
              }
              colorPath(color == 'X' ? 1 : 0, pathToBeColored);
              pathToBeColored.clear();
              reachedEnd = false;
            }
          }
        }
      }
      if (reachedEnd) { //if no legal moves we have reached an end point, color the path
        colorPath(0, pathToBeColored);
        pathToBeColored.clear();
      }
    }
    return true;
  }

  public ChessboardSquare[] possibleMoves(int positionX, int positionY) {
    ChessboardSquare possibleMoves[] = new ChessboardSquare[8];
    possibleMoves[0] =  new ChessboardSquare(positionX + IMoves, positionY + KMoves);
    possibleMoves[1] =  new ChessboardSquare(positionX + IMoves, positionY - KMoves);
    possibleMoves[2] =  new ChessboardSquare(positionX - IMoves, positionY + KMoves);
    possibleMoves[3] =  new ChessboardSquare(positionX - IMoves, positionY - KMoves);
    possibleMoves[4] =  new ChessboardSquare(positionX + KMoves, positionY + IMoves);
    possibleMoves[5] =  new ChessboardSquare(positionX + KMoves, positionY - IMoves);
    possibleMoves[6] =  new ChessboardSquare(positionX - KMoves, positionY + IMoves);
    possibleMoves[7] =  new ChessboardSquare(positionX - KMoves, positionY - IMoves);

    return possibleMoves;
  }

  public char[][] createChessboard() {
    // start from every square and run DFS
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (!DFS(i, j)) { //Rename
          System.out.println("No solution");
          return null;
        }
      }
    }
    return chessboard;
  }

  public static void main(String[] args) {
    int IMoves = 2;
    int KMoves = 5;   
    int size = 9;
    ChessboardGenerator chessboardGenerator = new ChessboardGenerator(IMoves, KMoves, size);
    char[][] chessboard = chessboardGenerator.createChessboard();

    if (chessboard == null) {
      System.out.println("No solution");
    } else {
      System.out.println("Solution found");
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          System.out.print(chessboard[i][j] + " ");
        }
        System.out.println();
      }
    }
  }
}
