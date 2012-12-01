package shallowblue;

public class Brain {
  private ChessBoard board;

   public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    Brain brain = new Brain(board);
    byte[] move = brain.chooseMove();
    
  }
   
   public static void printMoveset(byte[] moveset) {
    for (byte move : moveset) {
      System.out.println(move);
    }
  }
  
  public Brain(ChessBoard board) {
    this.board = board;
  }
  
  public byte[] chooseMove() {
    return null;
  }
          
  public int maximize(int alpha, int beta) {
    for (byte move : board.getPiecePositions()){
      
    }
    
    return 0;
  }

  public int minimize(int alpha, int beta) {
    return 0;
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