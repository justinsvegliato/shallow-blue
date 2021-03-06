package shallowblue;

import java.io.ByteArrayOutputStream;

public class Chessboard {
  
  enum Player {

    WHITE(-1), BLACK(1);
    public final int value;

    private Player(int value) {
      this.value = value;
    }
  }
  private byte[] board = new byte[120];
  static final byte king = 1;
  static final byte pawn = 2;
  static final byte knight = 3;
  static final byte queen = 5;
  static final byte rook = 6;
  static final byte bishop = 7;
  static final byte border = 9;
  static final byte empty = 0;

  public Chessboard() {
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
    board[24] = queen;
    board[25] = king;

    board[91] = -rook;
    board[92] = -knight;
    board[93] = -bishop;
    board[98] = -rook;
    board[97] = -knight;
    board[96] = -bishop;
    board[94] = -queen;
    board[95] = -king;
  }

  public byte move(byte from, byte to) {
    byte removedPiece = board[to];
    board[to] = board[from];
    board[from] = 0;
    return removedPiece;
  }

  public void undo(byte from, byte to, byte piece) {
    board[from] = board[to];
    board[to] = piece;
  }

  public byte getPiece(byte piece) {
    return board[piece];
  }

  public byte[] getPiecePositions(int color) {
    ByteArrayOutputStream positions = new ByteArrayOutputStream();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        int position = i * 10 + 20 + j + 1;
        if (board[position] != 0 && color * board[position] > 0) {
          positions.write(position);
        }
      }
    }
    return positions.toByteArray();
  }

  public boolean isValidMove(byte from, byte move, byte lastMove) {
    boolean withinBounds = board[from + move] != border;
    boolean isInArmy = board[from] * board[from + move] > 0;
    boolean hasClearPath = true;
    if (Math.abs(board[from]) / 4 == 1 && lastMove != 0) {
      hasClearPath = board[from + lastMove] == 0;
    }

    boolean satisfiesFringeCase = true;
    byte piece = (byte) Math.abs(getPiece(from));
    if (piece == pawn) {
      if (Math.abs(move) == 10 && board[from + move] != 0) {
        satisfiesFringeCase = false;
      } else if (move == 20 && (!(30 < from && from < 39) || board[from + move] != 0 || board[from + 10] != 0)) {
        satisfiesFringeCase = false;
      } else if (move == -20 && (!(80 < from && from < 89) || board[from + move] != 0 || board[from - 10] != 0)) {
        satisfiesFringeCase = false;
      } else if ((Math.abs(move) == 9 || Math.abs(move) == 11) && !((board[from] * board[from + move]) < 0)) {
        satisfiesFringeCase = false;
      }
    }
    return withinBounds && !isInArmy && hasClearPath && satisfiesFringeCase;
  }

  public char getPieceSymbol(byte piece) {
    switch (piece) {
      case king:
      case -king:
        return 'K';
      case queen:
      case -queen:
        return 'Q';
      case rook:
      case -rook:
        return 'R';
      case knight:
      case -knight:
        return 'N';
      case bishop:
      case -bishop:
        return 'B';
      case pawn:
      case -pawn:
        return 'P';
      case empty:
        return 'O';
      case border:
        return 'X';
      default:
        throw new IllegalArgumentException("An invalid piece was specified.");
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("-------------------------------\n");
    for (int i = 0; i < board.length; i++) {
      builder.append("|");
      if (board[i] >= 0) {
        builder.append(" ");
      } else {
        builder.append("-");
      }
      builder.append(getPieceSymbol(board[i]));
      if ((i + 1) % 10 == 0) {
        builder.append("|\n");
      }
    }
    builder.append("-------------------------------\n");
    return builder.toString();
  }

  public byte promote(byte from, byte to) {
    byte removedPiece = board[to];
    board[to] = (byte) (getPiece(from) / pawn * queen);
    board[from] = 0;
    return removedPiece;
  }

  public void castle(byte from, byte to) {
    boolean isBlack = getPiece(from) > 0;
    boolean isLeft = from - to > 0;

    if (isBlack) {
      if (isLeft) {
        board[24] = board[21];
        board[21] = 0;
      } else {
        board[26] = board[28];
        board[28] = 0;
      }
    } else {
      if (isLeft) {
        board[94] = board[91];
        board[91] = 0;
      } else {
        board[96] = board[98];
        board[98] = 0;
      }
    }
    
    board[to] = board[from];
    board[from] = 0;
  }
  
  public void croissant(byte from, byte to) {
    board[to] = board[from];
    board[from] = 0;
    
    if (getPiece(from) > 0) {
      board[to - 10] = 0;
    } else {
      board[to + 10] = 0;
    }
  }
}