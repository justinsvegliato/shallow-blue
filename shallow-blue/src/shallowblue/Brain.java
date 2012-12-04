package shallowblue;

import java.util.HashMap;
import shallowblue.ChessBoard.Player;

public class Brain {

  private ChessBoard board;
  private int maxDepth;
  private byte[] bestMove;
  private Player currentPlayer;
  private int MAX = 100000;
  public HashMap<Integer, Integer> positionWorths = new HashMap<Integer, Integer>() {
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
      put(54, 4);
      put(55, 4);
      put(56, 4);
      put(57, 3);
      put(58, 3);
      put(61, 3);
      put(62, 3);
      put(63, 4);
      put(64, 4);
      put(65, 4);
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

  public static void main(String[] args) throws InterruptedException {
    ChessBoard board = new ChessBoard();
    Brain brain = new Brain(board, 5);
    brain.setCurrentPlayer(Player.BLACK);
    int id = 1;
    while (true) {
      int move = brain.chooseMove(brain.getCurrentPlayer());
      brain.getBoard().move((brain.getBestMove())[0], (brain.getBestMove())[1]);
      System.out.println("ID: " + id + " Attacking: " + brain.getAttackingPieces(1) + " Best Move:" + brain.getBestMove()[1]+ "\n" + board);
      if (brain.getCurrentPlayer() == Player.BLACK) {
        brain.setCurrentPlayer(Player.WHITE);
      } else {
        brain.setCurrentPlayer(Player.BLACK);
      }
      id++;
    }
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

  public int chooseMove(Player player) {
    int v;
    if (player == Player.BLACK) {
      v = maximize(-MAX, MAX, 0);
    } else {
      v = minimize(-MAX, MAX, 0);
    }
    return v;
  }

  private int maximize(int alpha, int beta, int depth) {
    // Change condition to depth == 0
    if (depth == maxDepth) {
      return evaluate(board);
    }
    depth++;

    int v = -MAX;
    int best = 0;
    for (byte from : board.getPiecePositions(1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        v = Math.max(v, minimize(alpha, beta, depth));
        board.undo(from, to, removedPiece);
        if (v >= beta) {
          return v;
        }
        alpha = Math.max(alpha, v);
        if (currentPlayer == Player.BLACK && depth == 1 && alpha > best) {
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
      return evaluate(board);
    }
    depth++;

    int v = MAX;
    int best = 0;
    for (byte from : board.getPiecePositions(-1)) {
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        byte removedPiece = board.move(from, to);
        v = Math.min(v, maximize(alpha, beta, depth));
        board.undo(from, to, removedPiece);
        if (v <= alpha) {
          return v;
        }
        beta = Math.min(beta, v);
        // Ask BEN!
        if (currentPlayer == Player.WHITE && depth == 1 && beta < best) {
          bestMove = new byte[]{from, to};
          best = beta;
        }
      }
    }
    return v;
  }

  public int evaluate(ChessBoard board) {
    return evaluate(board, 1) - evaluate(board, -1);
  }

  public int evaluate(ChessBoard board, int color) {
    int value = 0;
    for (byte from : board.getPiecePositions(color)) {
      value += (int) 5 * getWorth(board.getPiece(from)) + 2 * positionWorths.get((int) from);
    }
    value += 3 * (getAttackingPieces(color) - getAttackingPieces(-color));
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

  public ChessBoard getBoard() {
    return board;
  }

  public int getMaxDepth() {
    return maxDepth;
  }

  public byte[] getBestMove() {
    return bestMove;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }
}