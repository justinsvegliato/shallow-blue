package shallowblue;

import java.io.ByteArrayOutputStream;

public class MovesetFactory {

  private static final byte[][] pawnMoveset = {
    {9}, {10}, {11}, {20}
  };
  private static final byte[][] kingMoveset = {
    {-11}, {-10}, {-9}, {-1}, {1}, {9}, {10}, {11}
  };
  private static final byte[][] knightMoveset = {
    {-21}, {-19}, {-12}, {-8}, {8}, {12}, {19}, {21}
  };
  private static final byte[][] rookMoveset = {
    {-10, -20, -30, -40, -50, -60, -70},
    {-1, -2, -3, -4, -5, -6, -7},
    {1, 2, 3, 4, 5, 6, 7},
    {10, 20, 30, 40, 50, 60, 70}
  };
  private static final byte[][] queenMoveset = {
    {-11, -22, -33, -44, -55, -66, -77},
    {-10, -20, -30, -40, -50, -60, -70},
    {-9, -18, -27, -36, -45, -54, -63},
    {-1, -2, -3, -4, -5, -6, -7},
    {1, 2, 3, 4, 5, 6, 7},
    {9, 18, 27, 36, 45, 54, 63},
    {10, 20, 30, 40, 50, 60, 70},
    {11, 22, 33, 44, 55, 66, 77}
  };
  private static final byte[][] bishopMoveset = {
    {-9, -18, -27, -36, -45, -54, -63},
    {-11, -22, -33, -44, -55, -66, -77},
    {9, 18, 27, 36, 45, 54, 63},
    {11, 22, 33, 44, 55, 66, 77}
  };

  private MovesetFactory() {}

  public static byte[] getValidMoveset(byte from, ChessBoard board) {
    switch (board.getPiece(from)) {
      case ChessBoard.king:
      case -ChessBoard.king:
        return generateMoveset(from, kingMoveset, board);
      case ChessBoard.queen:
      case -ChessBoard.queen:
        return generateMoveset(from, queenMoveset, board);
      case ChessBoard.rook:
      case -ChessBoard.rook:
        return generateMoveset(from, rookMoveset, board);
      case ChessBoard.knight:
      case -ChessBoard.knight:
        return generateMoveset(from, knightMoveset, board);
      case ChessBoard.bishop:
      case -ChessBoard.bishop:
        return generateMoveset(from, bishopMoveset, board);
      case ChessBoard.pawn:
      case -ChessBoard.pawn:
        return generateMoveset(from, pawnMoveset, board);
      default:
        String message = "An invalid piece is at the specified location";
        throw new IllegalArgumentException(message);
    }
  }

  private static byte[] generateMoveset(byte from, byte[][] moveset, ChessBoard board) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();

    byte color = 1;
    if (board.getPiece(from) < 0) {
      color = (byte) -1;
    }

    for (byte[] slide : moveset) {
      byte lastMove = 0;
      byte i = 0;
      boolean checkNext = true;
      while (checkNext && i < slide.length) {
        byte delta = slide[i++];
        byte move = (byte) (color * delta);
        if (board.isValidMove(from, move, lastMove)) {
          bytes.write(from + move);
        } else {
          checkNext = false;
        }
        lastMove = move;
      }
    }
    return bytes.toByteArray();
  }
}