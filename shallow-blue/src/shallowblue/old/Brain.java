package shallowblue.old;

import java.util.HashMap;
import shallowblue.Chessboard;
//import shallowblue.ChessBoard.Player;
import shallowblue.MovesetFactory;

public class Brain {

//  private ChessBoard board;
//  private int maxDepth;
//  private byte[] bestMove;
//  private Player currentPlayer;
//  private int MAX = 10000;
//  private static boolean gameOver = false;
//  public HashMap<Integer, Integer> positionWorths = new HashMap<Integer, Integer>() {
//    {
//      put(21, 1);
//      put(22, 1);
//      put(23, 1);
//      put(24, 1);
//      put(25, 1);
//      put(26, 1);
//      put(27, 1);
//      put(28, 1);
//      put(31, 1);
//      put(32, 1);
//      put(33, 2);
//      put(34, 2);
//      put(35, 2);
//      put(36, 2);
//      put(37, 1);
//      put(38, 1);
//      put(41, 2);
//      put(42, 2);
//      put(43, 3);
//      put(44, 3);
//      put(45, 3);
//      put(46, 3);
//      put(47, 2);
//      put(48, 2);
//      put(51, 3);
//      put(52, 3);
//      put(53, 4);
//      put(54, 6);
//      put(55, 6);
//      put(56, 4);
//      put(57, 3);
//      put(58, 3);
//      put(61, 3);
//      put(62, 3);
//      put(63, 4);
//      put(64, 6);
//      put(65, 6);
//      put(66, 4);
//      put(67, 3);
//      put(68, 3);
//      put(71, 2);
//      put(72, 2);
//      put(73, 3);
//      put(74, 3);
//      put(75, 3);
//      put(76, 3);
//      put(77, 2);
//      put(78, 2);
//      put(81, 1);
//      put(82, 1);
//      put(83, 2);
//      put(84, 2);
//      put(85, 2);
//      put(86, 2);
//      put(87, 1);
//      put(88, 1);
//      put(91, 1);
//      put(92, 1);
//      put(93, 1);
//      put(94, 1);
//      put(95, 1);
//      put(96, 1);
//      put(97, 1);
//      put(98, 1);
//    }
//  };
//
//  public static void main(String[] args) {
//    ChessBoard testBoard = new ChessBoard();
//    Brain brain1 = new Brain(testBoard, 1);
//    testBoard.move((byte) 38, (byte) 58);
//    System.out.println(brain1.evaluate(testBoard));
//
//    ChessBoard board = new ChessBoard();
//    Brain brain = new Brain(board, 4);
//    brain.setCurrentPlayer(Player.BLACK);
//    int id = 1;
//    while (!gameOver) {
//      long startTime = System.currentTimeMillis();
//      int move = brain.chooseMove(brain.getCurrentPlayer());
//      brain.getBoard().move((brain.getBestMove())[0], (brain.getBestMove())[1]);
//      //byte[] move = brain.chooseMove(brain.getCurrentPlayer());
//      //brain.getBoard().move(move[0], move[1]);
//      long endTime = System.currentTimeMillis();
//      System.out.println("Total execution time: " + (endTime - startTime));
//      System.out.println("ID: " + id + " Attacking: " + brain.getAttackingPieces(1) + "\n" + board);
//      if (brain.getCurrentPlayer() == Player.BLACK) {
//        brain.setCurrentPlayer(Player.WHITE);
//      } else {
//        brain.setCurrentPlayer(Player.BLACK);
//      }
//      id++;
//    }
//    System.out.println(board);
//  }
//
//  public static void printByteArray(byte[] moveset) {
//    for (byte move : moveset) {
//      System.out.print(move + " ");
//    }
//    System.out.println();
//  }
//
//  public Brain(ChessBoard board, int maxDepth) {
//    this.board = board;
//    this.maxDepth = maxDepth;
//  }
//
//  public int chooseMove(Player player) {
//    int v;
//    if (player == Player.BLACK) {
//      v = maximize(-MAX, MAX, 0);
//    } else {
//      v = minimize(-MAX, MAX, 0);
//    }
//    System.out.println(v);
//    return v;
//  }
//
//  private int maximize(int alpha, int beta, int depth) {
//    // Change condition to depth == 0
//    if (depth == maxDepth || gameOver) {
//      return evaluate(board);
//    }
//    depth++;
//
//    int v = -MAX;
//    int best = -MAX;
//    for (byte from : board.getPiecePositions(1)) {
//      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
//        byte removedPiece = board.move(from, to);
//        if (depth == 1 && removedPiece == -ChessBoard.king) {
//          gameOver = true;
//        }
//        v = Math.max(v, minimize(alpha, beta, depth));
//        board.undo(from, to, removedPiece);
//        if (v >= beta) {
//          return v;
//        }
//        alpha = Math.max(alpha, v);
//        if (currentPlayer == Player.BLACK && depth == 1 && alpha > best) {
//          bestMove = new byte[]{from, to};
//          best = alpha;
//        }
//      }
//    }
//    return v;
//  }
//
//  private int minimize(int alpha, int beta, int depth) {
//    // Change condition to depth == 0
//    if (depth == maxDepth || gameOver) {
//      return evaluate(board);
//    }
//    depth++;
//
//    int v = MAX;
//    int best = MAX;
//    for (byte from : board.getPiecePositions(-1)) {
//      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
//        byte removedPiece = board.move(from, to);
//        if (depth == 1 && removedPiece == ChessBoard.king) {
//          gameOver = true;
//        }
//        v = Math.min(v, maximize(alpha, beta, depth));
//        board.undo(from, to, removedPiece);
//        if (v <= alpha) {
//          return v;
//        }
//        beta = Math.min(beta, v);
//        // Ask BEN!
//        if (currentPlayer == Player.WHITE && depth == 1 && beta < best) {
//          bestMove = new byte[]{from, to};
//          best = beta;
//        }
//      }
//    }
//    return v;
//  }
////  public byte[] chooseMove(Player player) {;
////    Node best;
////    if (player == Player.BLACK) {
////      best = maximize(-MAX, MAX, new Node(null, 0, (byte) 0, (byte) 0, 0));
////    } else {
////      best = minimize(-MAX, MAX, new Node(null, 0, (byte) 0, (byte) 0, 0));
////    }
////    printByteArray(new byte[]{best.getFrom(), best.getTo()});
////    return new byte[]{best.getFrom(), best.getTo()};
////  }
////  
////  private Node maximize(int alpha, int beta, Node node) {
////    if(isTerminalState() || node.getDepth() == maxDepth){
////      node.setUtility(evaluate(board));
////      return node; 
////    }
////    
////    
////    int local = -Integer.MAX_VALUE; 
////    int depth = node.getDepth() + 1; 
////    
////    for (byte from : board.getPiecePositions(Player.BLACK.value * ChessBoard.king)) {
////      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
////        byte removed = board.move(from, to);
////        if (removed == Player.BLACK.value * ChessBoard.king) {
////          gameOver = true;
////        }
////        if(node.getUtility() < minimize(alpha, beta, new Node(from, to, depth, node)).getUtility()){
////          
////        }
////        board.undo(from, to, removed);
////      }
////    }
////    
////    return null;
////  }
////
////  private Node maximize(int alpha, int beta, Node node) {
////    if (node.getDepth() == maxDepth || gameOver) {
////      //node.setUtility(evaluate(board));
////      return node;
////    }
////
////    System.out.println("MAXIMIZE with a depth of " + node.getDepth());
////    Node nextNode = new Node(node, -MAX, (byte) 0, (byte) 0, node.getDepth() + 1);
////    for (byte from : board.getPiecePositions(1)) {
////      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
////        byte removedPiece = board.move(from, to);
////        if (nextNode.getDepth() == 1 && removedPiece == -ChessBoard.king) {
////          gameOver = true;
////        }
////        nextNode.setUtility(Math.max(nextNode.getUtility(), minimize(alpha, beta, nextNode).getUtility()));
////        board.undo(from, to, removedPiece);
////        if (nextNode.getUtility() >= beta) {
////          return  ;
////        }
////        System.out.println("The utility is " + nextNode.getUtility() + " for the move from " + nextNode.getFrom() + " to " + nextNode.getTo());
////        alpha = Math.max(alpha, nextNode.getUtility());
////        if (nextNode.getDepth() == 1 && alpha > nextNode.getUtility()) {
////          nextNode.setFrom(from);
////          nextNode.setTo(to);
////        }
////      }
////    }
////    return nextNode;
////  }
////
////  private Node minimize(int alpha, int beta, Node node) {
////    if (node.getDepth() == maxDepth || gameOver) {
////      node.setUtility(evaluate(board));
////      return node;
////    }
////
////    System.out.println("MINIMIZE with a depth of " + node.getDepth());
////    Node nextNode = new Node(MAX, (byte) 0, (byte) 0, node.getDepth() + 1);
////    for (byte from : board.getPiecePositions(-1)) {
////      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
////        byte removedPiece = board.move(from, to);
////        if (nextNode.getDepth() == 1 && removedPiece == ChessBoard.king) {
////          gameOver = true;
////        }
////        nextNode.setUtility(Math.min(nextNode.getUtility(), maximize(alpha, beta, nextNode).getUtility()));
////        System.out.println("The utility is " + nextNode.getUtility() + " for the move from " + from + " to " + to);
////        board.undo(from, to, removedPiece);
////        if (nextNode.getUtility() <= alpha) {
////          return nextNode;
////        }
////        beta = Math.min(beta, nextNode.getUtility());
////        if (nextNode.getDepth() == 1 && beta < nextNode.getUtility()) {
////          nextNode.setFrom(from);
////          nextNode.setTo(to);
////        }
////      }
////    }
////    return nextNode;
////  }
//  
//  
//
//  public int evaluate(ChessBoard board) {
//    return evaluate(board, 1) - evaluate(board, -1);
//  }
//
//  public int evaluate(ChessBoard board, int color) {
//    int value = 0;
//    for (byte from : board.getPiecePositions(color)) {
//      value += (int) 5 * getWorth(board.getPiece(from)) + 2 * positionWorths.get((int) from);
//    }
//    value += 2 * (getAttackingPieces(color) - getAttackingPieces(-color));
//    return value;
//  }
//
//  public int getAttackingPieces(int color) {
//    int attackingPieces = 0;
//    for (byte from : board.getPiecePositions(color)) {
//      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
//        if (board.getPiece(from) * board.getPiece((byte) (to)) < 0) {
//          attackingPieces++;
//        }
//      }
//    }
//    return attackingPieces;
//  }
//
//  private int getWorth(byte piece) {
//    switch (piece) {
//      case ChessBoard.king:
//      case -ChessBoard.king:
//        return 10000;
//      case ChessBoard.queen:
//      case -ChessBoard.queen:
//        return 9;
//      case ChessBoard.rook:
//      case -ChessBoard.rook:
//        return 5;
//      case ChessBoard.knight:
//      case ChessBoard.bishop:
//      case -ChessBoard.knight:
//      case -ChessBoard.bishop:
//        return 3;
//      case ChessBoard.pawn:
//      case -ChessBoard.pawn:
//        return 1;
//      default:
//        throw new IllegalArgumentException("An invalid piece was specified");
//    }
//  }
//
//  public ChessBoard getBoard() {
//    return board;
//  }
//
//  public int getMaxDepth() {
//    return maxDepth;
//  }
//
//  public byte[] getBestMove() {
//    return bestMove;
//  }
//
//  public Player getCurrentPlayer() {
//    return currentPlayer;
//  }
//
//  public void setCurrentPlayer(Player currentPlayer) {
//    this.currentPlayer = currentPlayer;
//  }
//
//  private boolean isTerminalState() {
//    return gameOver;
//  }
//
//  class Node {
//
//    private Node parent;
//    private int utility;
//    private byte from;
//    private byte to;
//    private int depth;
//
//    public Node(byte from, byte to, int depth, Node parent) {
//      this.from = from;
//      this.to = to;
//      this.depth = depth;
//      this.parent = parent;
//    }
//
//    public void setUtility(int utility) {
//      this.utility = utility;
//    }
//
//    public int getUtility() {
//      return utility;
//    }
//
//    public byte getFrom() {
//      return from;
//    }
//
//    public byte getTo() {
//      return to;
//    }
//
//    public int getDepth() {
//      return depth;
//    }
//
//    public Node getParent() {
//      return parent;
//    }
//
//    public String toString() {
//      return "Depth: " + depth + " Utility: " + utility + " From: " + from + " To: " + to;
//    }
//  }
}