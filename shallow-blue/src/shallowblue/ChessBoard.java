package shallowblue;

public class ChessBoard {

  public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    System.out.println(board);
  }
  
  private byte[][] board = new byte[8][8];

  private static enum Pieces {
    KING(1, 0), QUEEN(2, 9), ROOK(3, 5), KNIGHT(4, 3), BISHOP(5, 3), PAWN(6, 1);
    
    private final int value;
    private final int worth;

    private Pieces(int value, int weight) {
      this.value = value;
      this.worth = weight;
    }

    public int getValue() {
      return value;
    }

    public int getWorth() {
      return worth;
    }
  }

  public ChessBoard() {
    for (int file = 0; file <= 7; file++) {
      board[1][file] = (byte) Pieces.PAWN.getValue();
      board[6][file] = (byte) -Pieces.PAWN.getValue();
    }
    
    for (int offset = 0, piece = 3; offset < 3; offset++, piece++) {
      board[0][offset] = (byte) piece;
      board[0][7 - offset] = (byte) piece;
      board[7][offset] = (byte) -piece;
      board[7][7 - offset] = (byte) -piece;
    }

    board[0][3] = (byte) Pieces.KING.getValue();
    board[7][3] = (byte) Pieces.KING.getValue();
    board[0][4] = (byte) Pieces.QUEEN.getValue();
    board[7][4] = (byte) Pieces.QUEEN.getValue();
  }

  @Override
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
