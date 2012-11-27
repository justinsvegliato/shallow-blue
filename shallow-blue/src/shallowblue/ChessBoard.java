package shallowblue;

public class ChessBoard {

  public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    System.out.println(board);
  }
  
  private byte[][] board = new byte[8][8];

  private static enum Pieces {
    KING(1, 0), QUEEN(2, 9), ROOK(3, 5), BISHOP(4, 3), KNIGHT(5, 3), PAWN(6, 1);
    
    private final int value;
    private final int weight;

    private Pieces(int value, int weight) {
      this.value = value;
      this.weight = weight;
    }

    public int getValue() {
      return value;
    }

    public int getWeight() {
      return weight;
    }
  }

  public ChessBoard() {
    for (int file = 0; file < 8; file++) {
      board[1][file] = (byte) Pieces.PAWN.getValue();
    }

    for (int file = 0; file < 8; file++) {
      board[6][file] = (byte) -Pieces.PAWN.getValue();
    }
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("-------------------------\n");
    for (int rank = 0; rank < 8; rank++) {
      builder.append("|");
      for (int file = 0; file < 8; file++) {
        byte current = board[rank][file];
        if (current >= 0) {
          builder.append(" ");
        }
        builder.append(current);
        builder.append("|");
      }
      builder.append("\n-------------------------\n");
    }
    return builder.toString();
  }
}
