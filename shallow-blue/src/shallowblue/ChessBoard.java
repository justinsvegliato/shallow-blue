package shallowblue;

public class ChessBoard {

  public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    System.out.println(board);
  }

  public ChessBoard() {
    for (int i = 0; i < 8; i++) {
      board[8 + i] = pawn;
      board[48 + i] = -pawn;
    }

    board[0] = rook;
    board[1] = knight;
    board[2] = bishop;
    board[7] = rook;
    board[6] = knight;
    board[5] = bishop;
    board[3] = king;
    board[4] = queen;

    board[56] = -rook;
    board[57] = -knight;
    board[58] = -bishop;
    board[63] = -rook;
    board[62] = -knight;
    board[61] = -bishop;
    board[59] = -king;
    board[60] = -queen;
  }
  private static byte[] board = new byte[64];
  private static final byte king = 1;
  private static final byte queen = 2;
  private static final byte rook = 3;
  private static final byte knight = 4;
  private static final byte bishop = 5;
  private static final byte pawn = 6;

  public byte getWorth(byte piece) {
    switch (piece) {
      case 1:
      case -1:
        return 127;
      case 2:
      case -2:
        return 9;
      case 3:
      case -3:
        return 5;
      case 4:
      case 5:
      case -4:
      case -5:
        return 3;
      case 6:
      case -6:
        return 1;
    }
    return -1;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("-------------------------\n");
    for (int i = 0; i < board.length; i++) {
      builder.append("|");
      if (board[i] >= 0) {
        builder.append(" ");
      }
      builder.append(board[i]);
      if ((i + 1) % 8 == 0) {
        builder.append("|\n-------------------------\n");
      }
    }
    return builder.toString();
  }
}