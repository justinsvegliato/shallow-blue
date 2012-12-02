package shallowblue;

public class Brain {

  private ChessBoard board;
  private int maxDepth;

  public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    Brain brain = new Brain(board, 2);
    int move = brain.chooseMove();
  }

  public static void printMoveset(byte[] moveset) {
    for (byte move : moveset) {
      System.out.println("Move: " + move);
    }
  }

  public Brain(ChessBoard board, int maxDepth) {
    this.board = board;
    this.maxDepth = maxDepth;
  }

  public int chooseMove() {
    int v = maximize(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    return v;
  }

  private int maximize(int alpha, int beta, int depth) {
    if (depth == maxDepth) {
      return evaluate(board, 1);
    }
    depth++;
    System.out.println("Maximizing...");

    int v = Integer.MIN_VALUE;
    for (byte from : board.getPiecePositions(1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        System.out.println(board);
        v = Math.max(v, minimize(alpha, beta, depth));
        board.undo(from, to, removedPiece);
        if (v >= beta) {
          return v;
        }
        alpha = Math.max(alpha, v);
      }
    }
    return v;
  }

  private int minimize(int alpha, int beta, int depth) {
    if (depth == maxDepth) {
      return evaluate(board, -1);
    }
    depth++;
    System.out.println("Minimizing...");

    int v = Integer.MAX_VALUE;
    for (byte from : board.getPiecePositions(-1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        System.out.println(board);
        v = Math.min(v, maximize(alpha, beta, depth));
        board.undo(from, to, removedPiece);
        if (v <= alpha) {
          return v;
        }
        beta = Math.min(beta, v);
      }
    }
    return v;
  }

  private byte getWorth(byte piece) {
    switch (piece) {
      case ChessBoard.king:
      case -ChessBoard.king:
        return Byte.MAX_VALUE;
      case ChessBoard.queen:
      case -ChessBoard.queen:
        return 9;
      case ChessBoard.rook:
      case -ChessBoard.rook:
        return 5;
      case ChessBoard.knight:
      case ChessBoard.bishop:
      case -ChessBoard.knight:
      case -ChessBoard.bishop:
        return 3;
      case ChessBoard.pawn:
      case -ChessBoard.pawn:
        return 1;
      default:
        throw new IllegalArgumentException("An invalid piece was specified");
    }
  }

  private int evaluate(ChessBoard board, int color) {
    int value = 0;
    for (byte from : board.getPiecePositions(color)) {
      value += getWorth(board.getPiece(from));
    }
    return value;
  }

  private static class Node {

    private final Node parent;
    private final byte from;
    private final byte to;
    private final byte piece;

    public Node(Node parent, byte from, byte to, byte piece) {
      this.parent = parent;
      this.from = from;
      this.to = to;
      this.piece = piece;
    }

    public Node getParent() {
      return parent;
    }

    public byte getFrom() {
      return from;
    }

    public byte getTo() {
      return to;
    }

    public byte getPiece() {
      return piece;
    }
  }
}