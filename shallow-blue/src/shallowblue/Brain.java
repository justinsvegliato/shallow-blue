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

  public Brain(ChessBoard board, int depth) {
    this.board = board;
    this.maxDepth = depth;
  }

  public int chooseMove() {
    int v = maximize(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    return v;
  }
  
  public int maximize(int alpha, int beta, int depth) {
    if (depth == maxDepth) {
      return evaluate(board, 1);
    }
    System.out.println("Start maximizing");
    depth++;
    
    int v = Integer.MIN_VALUE;
    for (byte from : board.getPiecePositions(1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        v = Math.max(v, minimize(alpha, beta, depth));
        System.out.println(board);
        board.undo(from, to, removedPiece);
        if (v >= beta) {
          return v;
        }
        alpha = Math.max(alpha, v);
      }
    }
    return v;
  }

  public int minimize(int alpha, int beta, int depth) {
    if (depth == maxDepth) {
      return evaluate(board, -1);
    }
    System.out.println("Start minimizing");
    depth++;
    
    int v = Integer.MAX_VALUE;
    for (byte from : board.getPiecePositions(-1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        v = Math.min(v, maximize(alpha, beta, depth));
        System.out.println(board);
        board.undo(from, to, removedPiece);
        if (v <= alpha) {
          return v;
        }
        beta = Math.min(beta, v);
      }
    }
    return v;
  }

  public int evaluate(ChessBoard board, int color) {
    int value = 0;
    for (byte from : board.getPiecePositions(color)) {
      value += board.getWorth(board.getPiece(from));
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