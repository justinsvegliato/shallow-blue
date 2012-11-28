package shallowblue;

public class ChessBoard {

  public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    System.out.println(board);
  }

  public ChessBoard() {
    for (int i = 0; i < 10; i++) {
      board[0 + i] = border;
      board[10 + i] = border;
      board[100 + i] = border;
      board[110 + i] = border;
    }
    
    
    for (int i = 0; i < 8; i++) {
      board[20 + i * 10] = border;
      board[29 + i * 10] = border;
      board[31 + i] = pawn;
      board[81 + i] = -pawn;
    }    

    board[21] = rook;
    board[22] = knight;
    board[23] = bishop;
    board[28] = rook;
    board[27] = knight;
    board[26] = bishop;
    board[24] = king;
    board[25] = queen;

    board[91] = -rook;
    board[92] = -knight;
    board[93] = -bishop;
    board[98] = -rook;
    board[97] = -knight;
    board[96] = -bishop;
    board[94] = -king;
    board[95] = -queen;
  }
  
  private static byte[] board = new byte[120];
  
  private static final byte king = 1;
  private static final byte queen = 2;
  private static final byte rook = 3;
  private static final byte knight = 4;
  private static final byte bishop = 5;
  private static final byte pawn = 6;
  private static final byte border = 7;

  public byte getWorth(byte piece) {
    switch (piece) {
      case king:
      case -king:
        return Byte.MAX_VALUE;
      case queen:
      case -queen:
        return 9;
      case rook:
      case -rook:
        return 5;
      case knight:
      case bishop:
      case -knight:
      case -bishop:
        return 3;
      case pawn:
      case -pawn:
        return 1;
    }
    return -1;
  }
  
  public byte getPiece(byte piece) {
    return board[piece];
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("-------------------------------\n");
    for (int i = 0; i < board.length; i++) {
      builder.append("|");
      if (board[i] >= 0) {
        builder.append(" ");
      }
      builder.append(board[i]);
      if ((i + 1) % 10 == 0) {
        builder.append("|\n-------------------------------\n");
      }
    }
    return builder.toString();
  }
}