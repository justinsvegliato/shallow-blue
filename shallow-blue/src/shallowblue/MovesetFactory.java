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
    {-7, -6, -5, -4, -3, -2, -1},
    {1, 2, 3, 4, 5, 6, 7},
    {10, 20, 30, 40, 50, 60, 70}
  };
  private static final byte[][] queenMoveset = {
    {-10, -20, -30, -40, -50, -60, -70},
    {-7, -6, -5, -4, -3, -2, -1},
    {1, 2, 3, 4, 5, 6, 7},
    {10, 20, 30, 40, 50, 60, 70},
    {-9, -18, -27, -36, -45, -54, -63},
    {-11, -22, -33, -44, -55, -66, -77},
    {9, 18, 27, 36, 45, 54, 63},
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
      case 1:
      case -1:
        return parseMoveset(from, kingMoveset, board);
      case 2:
      case -2:
        return parseMoveset(from, queenMoveset, board);
      case 3:
      case -3:
        return parseMoveset(from, rookMoveset, board);
      case 4:
      case -4:
        return parseMoveset(from, knightMoveset, board);
      case 5:
      case -5:
        return parseMoveset(from, bishopMoveset, board);
      case 6:
      case -6:
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
      for (byte delta : slide) {
        byte move = (byte) (color * delta);
        if (board.isValidMove(from, move)) {
          bytes.write(from + move);
        }
      }
    }

    return bytes.toByteArray();
  }
}
