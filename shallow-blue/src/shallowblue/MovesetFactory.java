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

  public byte[] getValidMoveset(byte from, ChessBoard board) {
    switch (board.getPiece(from)) {
      case ChessBoard.king:
      case -ChessBoard.king:
        return parseMoveset(from, kingMoveset, board);
      case ChessBoard.queen:
      case -ChessBoard.queen:
        return parseMoveset(from, queenMoveset, board);
      case ChessBoard.rook:
      case -ChessBoard.rook:
        return parseMoveset(from, rookMoveset, board);
      case ChessBoard.knight:
      case -ChessBoard.knight:
        return parseMoveset(from, knightMoveset, board);
      case ChessBoard.bishop:
      case -ChessBoard.bishop:
        return parseMoveset(from, bishopMoveset, board);
      case ChessBoard.pawn:
      case -ChessBoard.pawn:
        return parseMoveset(from, pawnMoveset, board);
    }
    return null;
  }

  private byte[] parseMoveset(byte from, byte[][] moveset, ChessBoard board) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();

    byte color = 1;
    if (from < 0) {
      color = (byte) -color;
    }

    for (byte[] slide : moveset) {
      byte lastMove = 0;
      for (byte delta : slide) {
        byte move = (byte) (color * delta);
        if (board.isValidMove(from, move, lastMove)) {
          bytes.write(from + move);
        } else {
          break;
        }
        lastMove = move;
      }
    }

    return bytes.toByteArray();
  }
}
