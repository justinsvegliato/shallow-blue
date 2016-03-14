package shallowblue;

public class Agent {

  private Chessboard board;
  private int maxDepth;
  private byte[] bestMove;
  private boolean gameOver = false;
  private final int color;
  private static int INFINITY = 500000;

  public Agent(Chessboard board, int color, int maxDepth) {
    this.board = board;
    this.color = color;
    this.maxDepth = maxDepth;
  }

  public int selectBestMove() {
    return alphaBeta(board, maxDepth, -INFINITY, INFINITY, color);
  }

  private int alphaBeta(Chessboard board, int depth, int alpha, int beta, int color) {
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

  private int getUtilityValue(Chessboard board, int color) {
    int value = evaluate(board, color) - evaluate(board, -color);
    return (int) value;
  }

  private int evaluate(Chessboard board, int color) {
    int value = 0;
    int attackingPieces = 0;
    for (byte from : board.getPiecePositions(color)) {
      value += 5 * getPieceWorth(board.getPiece(from)) + 2 * getPositionWorth(from);
      for (byte to : MovesetFactory.getValidMoveset(from, board)) {
        if (board.getPiece(from) * board.getPiece((byte) (to)) < 0) {
          attackingPieces++;
        }
      }
    }
    value += 2 * attackingPieces;
    return value;
  }

  private int getPieceWorth(byte piece) {
    if(piece == Chessboard.king * color){
      return 30000;
    }
    
    switch (piece) {
      case Chessboard.king:
      case -Chessboard.king:
        return 10000;
      case Chessboard.queen:
      case -Chessboard.queen:
        return 900;
      case Chessboard.rook:
      case -Chessboard.rook:
        return 500;
      case Chessboard.bishop:
      case -Chessboard.bishop:
        return 330;
      case Chessboard.knight:
      case -Chessboard.knight:
        return 320;
      case Chessboard.pawn:
      case -Chessboard.pawn:
        return 100;
      default:
        throw new IllegalArgumentException("An invalid piece was specified");
    }
  }

  private int getPositionWorth(byte from) {
    byte piece = board.getPiece(from);
    int adjustedFrom = (from - 21) - (((from / 10) - 2) * 2);
    switch (piece) {
      case Chessboard.king:
        return blackKingPieceSquareTable[adjustedFrom];
      case -Chessboard.king:
        return whiteKingPieceSquareTable[adjustedFrom];
      case Chessboard.queen:
        return blackQueenPieceSquareTable[adjustedFrom];
      case -Chessboard.queen:
        return whiteQueenPieceSquareTable[adjustedFrom];
      case Chessboard.rook:
        return blackRookPieceSquareTable[adjustedFrom];
      case -Chessboard.rook:
        return whiteRookPieceSquareTable[adjustedFrom];
      case Chessboard.bishop:
        return blackBishopPieceSquareTable[adjustedFrom];
      case -Chessboard.bishop:
        return whiteBishopPieceSquareTable[adjustedFrom];
      case Chessboard.knight:
        return blackKnightPieceSquareTable[adjustedFrom];
      case -Chessboard.knight:
        return whiteKnightPieceSquareTable[adjustedFrom];
      case Chessboard.pawn:
        return blackPawnPieceSquareTable[adjustedFrom];
      case -Chessboard.pawn:
        return whitePawnPieceSquareTable[adjustedFrom];
      default:
        throw new IllegalArgumentException("An invalid piece was specified");
    }
  }

  public byte[] getBestMove() {
    return bestMove;
  }
  // Pawn piece-square table
  private final static int[] whitePawnPieceSquareTable = {
    0, 0, 0, 0, 0, 0, 0, 0,
    20, 20, 20, 30, 30, 20, 20, 20,
    10, 10, 20, 30, 30, 20, 10, 10,
    5, 5, 10, 25, 25, 10, 5, 5,
    0, 0, 0, 20, 20, 0, 0, 0,
    5, -5, -10, 0, 0, -10, -5, 5,
    5, 10, 10, -20, -20, 10, 10, 5,
    0, 0, 0, 0, 0, 0, 0, 0
  };
  private final static int[] blackPawnPieceSquareTable = {
    0, 0, 0, 0, 0, 0, 0, 0,
    5, 10, 10, -20, -20, 10, 10, 5,
    5, -5, -10, 0, 0, -10, -5, 5,
    0, 0, 0, 20, 20, 0, 0, 0,
    5, 5, 10, 25, 25, 10, 5, 5,
    10, 10, 20, 30, 30, 20, 10, 10,
    20, 20, 20, 30, 30, 20, 20, 20,
    0, 0, 0, 0, 0, 0, 0, 0
  };
  // Knight piece-square table
  private final static int[] whiteKnightPieceSquareTable = {
    -50, -40, -30, -30, -30, -30, -40, -50,
    -40, -20, 0, 0, 0, 0, -20, -40,
    -30, 0, 10, 15, 15, 10, 0, -30,
    -30, 5, 15, 20, 20, 15, 5, -30,
    -30, 0, 15, 20, 20, 15, 0, -30,
    -30, 5, 10, 15, 15, 10, 5, -30,
    -40, -20, 0, 5, 5, 0, -20, -40,
    -50, -40, -30, -30, -30, -30, -40, -50
  };
  private final static int[] blackKnightPieceSquareTable = {
    -50, -40, -30, -30, -30, -30, -40, -50,
    -40, -20, 0, 5, 5, 0, -20, -40,
    -30, 5, 10, 15, 15, 10, 5, -30,
    -30, 0, 15, 20, 20, 15, 0, -30,
    -30, 5, 15, 20, 20, 15, 5, -30,
    -30, 0, 10, 15, 15, 10, 0, -30,
    -40, -20, 0, 0, 0, 0, -20, -40,
    -50, -40, -30, -30, -30, -30, -40, -50
  };
  // Bishop piece-square table
  private final static int[] whiteBishopPieceSquareTable = {
    -20, -10, -10, -10, -10, -10, -10, -20,
    -10, 0, 0, 0, 0, 0, 0, -10,
    -10, 0, 5, 10, 10, 5, 0, -10,
    -10, 5, 5, 10, 10, 5, 5, -10,
    -10, 0, 10, 10, 10, 10, 0, -10,
    -10, 10, 10, 10, 10, 10, 10, -10,
    -10, 5, 0, 0, 0, 0, 5, -10,
    -20, -10, -10, -10, -10, -10, -10, -20
  };
  private final static int[] blackBishopPieceSquareTable = {
    -20, -10, -10, -10, -10, -10, -10, -20,
    -10, 5, 0, 0, 0, 0, 5, -10,
    -10, 10, 10, 10, 10, 10, 10, -10,
    -10, 0, 10, 10, 10, 10, 0, -10,
    -10, 5, 5, 10, 10, 5, 5, -10,
    -10, 0, 5, 10, 10, 5, 0, -10,
    -10, 0, 0, 0, 0, 0, 0, -10,
    -20, -10, -10, -10, -10, -10, -10, -20,};
  // Rook piece-square table
  private final static int[] whiteRookPieceSquareTable = {
    0, 0, 0, 0, 0, 0, 0, 0,
    5, 10, 10, 10, 10, 10, 10, 5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    0, 0, 0, 5, 5, 0, 0, 0
  };
  private final static int[] blackRookPieceSquareTable = {
    0, 0, 0, 5, 5, 0, 0, 0,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    -5, 0, 0, 0, 0, 0, 0, -5,
    5, 10, 10, 10, 10, 10, 10, 5,
    0, 0, 0, 0, 0, 0, 0, 0
  };
  // Queen piece-square tables
  private final static int[] whiteQueenPieceSquareTable = {
    -20, -10, -10, -5, -5, -10, -10, -20,
    -10, 0, 0, 0, 0, 0, 0, -10,
    -10, 0, 5, 5, 5, 5, 0, -10,
    -5, 0, 5, 5, 5, 5, 0, -5,
    0, 0, 5, 5, 5, 5, 0, -5,
    -10, 5, 5, 5, 5, 5, 0, -10,
    -10, 0, 5, 0, 0, 0, 0, -10,
    -20, -10, -10, -5, -5, -10, -10, -20
  };
  private final static int[] blackQueenPieceSquareTable = {
    -20, -10, -10, -5, -5, -10, -10, -20,
    -10, 0, 5, 0, 0, 0, 0, -10,
    -10, 5, 5, 5, 5, 5, 0, -10,
    0, 0, 5, 5, 5, 5, 0, -5,
    -5, 0, 5, 5, 5, 5, 0, -5,
    -10, 0, 5, 5, 5, 5, 0, -10,
    -10, 0, 0, 0, 0, 0, 0, -10,
    -20, -10, -10, -5, -5, -10, -10, -20
  };
  // King piece-square tables
  private final static int[] whiteKingPieceSquareTable = {
    -30, -40, -40, -50, -50, -40, -40, -30,
    -30, -40, -40, -50, -50, -40, -40, -30,
    -30, -40, -40, -50, -50, -40, -40, -30,
    -30, -40, -40, -50, -50, -40, -40, -30,
    -20, -30, -30, -40, -40, -30, -30, -20,
    -10, -20, -20, -20, -20, -20, -20, -10,
    20, 20, 0, 0, 0, 0, 20, 20,
    10, 10, 10, 0, 0, 10, 10, 10
  };
  private final static int[] blackKingPieceSquareTable = {
    10, 10, 10, 0, 0, 10, 10, 10,
    20, 20, 0, 0, 0, 0, 20, 20,
    -10, -20, -20, -20, -20, -20, -20, -10,
    -20, -30, -30, -40, -40, -30, -30, -20,
    -30, -40, -40, -50, -50, -40, -40, -30,
    -30, -40, -40, -50, -50, -40, -40, -30,
    -30, -40, -40, -50, -50, -40, -40, -30,
    -30, -40, -40, -50, -50, -40, -40, -30
  };
}