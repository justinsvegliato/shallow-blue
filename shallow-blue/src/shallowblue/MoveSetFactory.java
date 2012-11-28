package shallowblue;

import java.io.ByteArrayOutputStream;

public class MoveSetFactory {
  
  public byte[] getValidMoveSet(byte from, ChessBoard board){
    switch(board.getPiece(from)){
      case 1:
      case -1:
        return getValidKingMoveSet(from, board);
      case 2:
      case -2:
        return getValidQueenMoveSet(from, board);
      case 3:
      case -3:
        return getValidRookMoveSet(from, board);
      case 4:
      case -4:
        return getValidBishopMoveSet(from, board);
      case 5:
      case -5:
        return getValidKnightMoveSet(from, board);
      case 6:
      case -6:
        return getValidPawnMoveSet(from, board);
    }
    
    return null;
  }

  private byte[] getValidKingMoveSet(byte from, ChessBoard board) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private byte[] getValidQueenMoveSet(byte from, ChessBoard board) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private byte[] getValidRookMoveSet(byte from, ChessBoard board) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private byte[] getValidBishopMoveSet(byte from, ChessBoard board) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private byte[] getValidKnightMoveSet(byte from, ChessBoard board) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private byte[] getValidPawnMoveSet(byte from, ChessBoard board) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
