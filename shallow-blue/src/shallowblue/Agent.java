package shallowblue;

import java.util.HashMap;

public class Agent {

  private ChessBoard board;
  private int maxDepth;
  private byte[] bestMove;
  private boolean gameOver = false;
  private final int color;
  private static int INFINITY = 10001;
  private HashMap<Integer, Integer> positionWorths = new HashMap<Integer, Integer>() {
    {
      put(21, 1);
      put(22, 1);
      put(23, 1);
      put(24, 1);
      put(25, 1);
      put(26, 1);
      put(27, 1);
      put(28, 1);
      put(31, 1);
      put(32, 1);
      put(33, 2);
      put(34, 2);
      put(35, 2);
      put(36, 2);
      put(37, 1);
      put(38, 1);
      put(41, 2);
      put(42, 2);
      put(43, 3);
      put(44, 3);
      put(45, 3);
      put(46, 3);
      put(47, 2);
      put(48, 2);
      put(51, 3);
      put(52, 3);
      put(53, 4);
      put(54, 6);
      put(55, 6);
      put(56, 4);
      put(57, 3);
      put(58, 3);
      put(61, 3);
      put(62, 3);
      put(63, 4);
      put(64, 6);
      put(65, 6);
      put(66, 4);
      put(67, 3);
      put(68, 3);
      put(71, 2);
      put(72, 2);
      put(73, 3);
      put(74, 3);
      put(75, 3);
      put(76, 3);
      put(77, 2);
      put(78, 2);
      put(81, 1);
      put(82, 1);
      put(83, 2);
      put(84, 2);
      put(85, 2);
      put(86, 2);
      put(87, 1);
      put(88, 1);
      put(91, 1);
      put(92, 1);
      put(93, 1);
      put(94, 1);
      put(95, 1);
      put(96, 1);
      put(97, 1);
      put(98, 1);
    }
  };
//
//  public static void main(String[] args) {
//    ChessBoard board = new ChessBoard();
//    Agent black = new Agent(board, Player.BLACK.value, 4);
//    Agent white = new Agent(board, Player.WHITE.value, 1);
//    int currentPlayer = 1;
//    int move = 1;
//    
//    System.out.println(board);
//    
//    while (true) {
//      if (currentPlayer == 1) {
//        black.selectBestMove();
//      } else {
//        white.selectBestMove();
//      }
//      int removedPiece = board.move(bestMove[0], bestMove[1]);
//      System.out.println("Turn: " + currentPlayer + " Move: " + move + "\n" + board);
//      if (Math.abs(removedPiece) == ChessBoard.king) {
//        break;
//      }
//      currentPlayer = -currentPlayer;
//      move++;
//    }
//    System.out.println("gg bra");
//  }

  public Agent(ChessBoard board, int color, int maxDepth) {
    this.board = board;
    this.color = color;
    this.maxDepth = maxDepth;
  }

  public int selectBestMove() {
    return alphaBeta(board, maxDepth, -INFINITY, INFINITY, color);
  }

  private int alphaBeta(ChessBoard board, int depth, int alpha, int beta, int color) {
    if (depth == 0 || gameOver) {
      return getUtilityValue(board, color);
    }

    int score = -INFINITY;
    for (byte from : board.getPiecePositions(color)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        int current = -alphaBeta(board, depth - 1, -beta, -alpha, -color);
        if (current > score) {
          score = current;
        }
        if (score > alpha) {
          alpha = score;
          if (depth == maxDepth) {
            bestMove = new byte[]{from, to};
          }
        }
        board.undo(from, to, removedPiece);
        if (alpha >= beta) {
          return alpha;
        }
      }
    }
    return score;
  }

  private int getUtilityValue(ChessBoard board, int color) {
    int value = evaluate(board, color) - evaluate(board, -color);
    return (int) (value + Math.random() + .5);
  }

  private int evaluate(ChessBoard board, int color) {
    int value = 0;
    for (byte from : board.getPiecePositions(color)) {
      value += (int) 5 * getWorth(board.getPiece(from)) + 2 * positionWorths.get((int) from);
    }
    value += 2 * getAttackingPieces(color);
    return value;
  }

  public int getAttackingPieces(int color) {
    int attackingPieces = 0;
    for (byte from : board.getPiecePositions(color)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        if (board.getPiece(from) * board.getPiece((byte) (to)) < 0) {
          attackingPieces++;
        }
      }
    }
    return attackingPieces;
  }

  private int getWorth(byte piece) {
    switch (piece) {
      case ChessBoard.king:
      case -ChessBoard.king:
        return 10000;
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

  public byte[] getBestMove() {
    return bestMove;
  }
}