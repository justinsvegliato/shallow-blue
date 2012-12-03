package shallowblue;

import java.util.HashMap;

public class Brain {

  private ChessBoard board;
  private int maxDepth;
  private static byte[] bestMove;
  public HashMap<Integer, Integer> positionWorths = new HashMap<Integer, Integer>();

  public static void main(String[] args) {
    ChessBoard board = new ChessBoard();
    Brain brain = new Brain(board, 7);
    int move = brain.chooseMove();
    printMoveset(bestMove);
  }

  public static void printMoveset(byte[] moveset) {
    for (byte move : moveset) {
      System.out.println("Move: " + move);
    }
  }

  public Brain(ChessBoard board, int maxDepth) {
    this.board = board;
    this.maxDepth = maxDepth;
    positionWorths.put(21, 1);
    positionWorths.put(22, 1);
    positionWorths.put(23, 1);
    positionWorths.put(24, 1);
    positionWorths.put(25, 1);
    positionWorths.put(26, 1);
    positionWorths.put(27, 1);
    positionWorths.put(28, 1);
    positionWorths.put(31, 1);
    positionWorths.put(32, 1);
    positionWorths.put(33, 2);
    positionWorths.put(34, 2);
    positionWorths.put(35, 2);
    positionWorths.put(36, 2);
    positionWorths.put(37, 1);
    positionWorths.put(38, 1);
    positionWorths.put(41, 2);
    positionWorths.put(42, 2);
    positionWorths.put(43, 3);
    positionWorths.put(44, 3);
    positionWorths.put(45, 3);
    positionWorths.put(46, 3);
    positionWorths.put(47, 2);
    positionWorths.put(48, 2);
    positionWorths.put(51, 3);
    positionWorths.put(52, 3);
    positionWorths.put(53, 4);
    positionWorths.put(54, 4);
    positionWorths.put(55, 4);
    positionWorths.put(56, 4);
    positionWorths.put(57, 3);
    positionWorths.put(58, 3);
    positionWorths.put(61, 3);
    positionWorths.put(62, 3);
    positionWorths.put(63, 4);
    positionWorths.put(64, 4);
    positionWorths.put(65, 4);
    positionWorths.put(66, 4);
    positionWorths.put(67, 3);
    positionWorths.put(68, 3);
    positionWorths.put(71, 2);
    positionWorths.put(72, 2);
    positionWorths.put(73, 3);
    positionWorths.put(74, 3);
    positionWorths.put(75, 3);
    positionWorths.put(76, 3);
    positionWorths.put(77, 2);
    positionWorths.put(78, 2);
    positionWorths.put(81, 1);
    positionWorths.put(82, 1);
    positionWorths.put(83, 2);
    positionWorths.put(84, 2);
    positionWorths.put(85, 2);
    positionWorths.put(86, 2);
    positionWorths.put(87, 1);
    positionWorths.put(88, 1);
    positionWorths.put(91, 1);
    positionWorths.put(92, 1);
    positionWorths.put(93, 1);
    positionWorths.put(94, 1);
    positionWorths.put(95, 1);
    positionWorths.put(96, 1);
    positionWorths.put(97, 1);
    positionWorths.put(98, 1);
  }

  public int chooseMove() {
    int v = maximize(-10000000, 1000000, 0);
    return v;
  }

  private int maximize(int alpha, int beta, int depth) {
    // Change condition to depth == 0
    if (depth == maxDepth) {
      //System.out.println("Eval: " + evaluate(board, 1));
      return evaluate(board);
    }
    depth++;

    int v = Integer.MIN_VALUE;
    int best = 0;
    for (byte from : board.getPiecePositions(1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        //System.out.println(board);
        v = Math.max(v, minimize(alpha, beta, depth));
        board.undo(from, to, removedPiece);
        if (v >= beta) {
          return v;
        }
        alpha = Math.max(alpha, v);
        //System.out.println("Alpha: " + alpha + " Best: " + best);
        if (depth == 1 && alpha > best) {
          //System.out.println("Assigned");
          bestMove = new byte[]{from, to};
          best = alpha;
        }
      }
    }
    return v;
  }

  private int minimize(int alpha, int beta, int depth) {
    // Change condition to depth == 0
    if (depth == maxDepth) {
      //ystem.out.println("Eval: " + evaluate(board, -1));
      return evaluate(board);
    }
    depth++;

    int v = Integer.MAX_VALUE;
    for (byte from : board.getPiecePositions(-1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        //System.out.println(board);
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
  
  public int evaluate(ChessBoard board) {
    return evaluate(board, 1) + evaluate(board, -1);
  }

  public int evaluate(ChessBoard board, int color) {
    int value = 0;
    for (byte from : board.getPiecePositions(color)) {
      value += (int) getWorth(board.getPiece(from)) + positionWorths.get((int) from);
    }
    return value;
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
}