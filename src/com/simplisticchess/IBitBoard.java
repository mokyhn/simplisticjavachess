package com.simplisticchess;

import com.simplisticchess.Piece;

/**
 *
 * @author Morten Kühnrich
 * @year 2014
 */
public interface IBitBoard extends Cloneable{
  public void insertPiece(Piece p);
  public boolean hasPiece(int x, int y, int color, int type);
  public Piece removePiece(int x, int y);
  @Override public String toString();
  @Override public boolean equals(Object obj);
  public IBitBoard clone();
}
