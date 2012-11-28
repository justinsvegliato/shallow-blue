package shallowblue;

public class ChessBoard {

  public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    MovesetFactory factory = new MovesetFactory();
    printMoveset(factory.getValidMoveset((byte) 32, board));
    System.out.println(board);
  }
  static byte[] board = new byte[120];
  static final byte king = 1;
  static final byte pawn = 2;
  static final byte knight = 3;
  static final byte queen = 5;
  static final byte rook = 6;
  static final byte bishop = 7;
  static final byte border = 9;

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
  
  

  public void move(byte from, byte to) {
  }

  public void undo() {
  }

  public byte getPiece(byte piece) {
    return board[piece];
  }

  public boolean isValidMove(byte from, byte move, byte lastMove) {
    boolean withinBounds = board[from + move] != border;
    boolean isInArmy = board[from] * board[from + move] > 0;
    boolean hasClearPath = true;
    if ((board[from] / 4) == 1 && lastMove != 0) {
      hasClearPath = board[from + lastMove] != 0;
    }

    boolean satisfiesFringe = true;
    if (getPiece(from) == pawn) {
      if (move == 20 && !(30 < from && from < 39)) {
        satisfiesFringe = false;
      } else if (move == -20 && !(80 < from && from < 89)) {
        satisfiesFringe = false;
      }

      if ((move == 9 || move == 11) && !((board[from] * board[from + move]) < 0)) {
        satisfiesFringe = false;
      }

      if (move == 10 && (board[from] * board[from + move]) < 0) {
        satisfiesFringe = false;
      }

      if (move == 20 && ((board[from] * board[from + move]) < 0) || ((board[from] * board[from + 10]) < 0)) {
        satisfiesFringe = false;
      }
    }

    return withinBounds && !isInArmy && hasClearPath && satisfiesFringe;
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

  public static void printMoveset(byte[] moveset) {
    for (byte move : moveset) {
      System.out.println(move);
    }

  }
}