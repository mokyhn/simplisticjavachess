package com.simplisticchess.move;

public final class InvalidMoveException extends Exception
{

    public String err;

    public InvalidMoveException()
    {
    }

    public InvalidMoveException(String e)
    {
        err = e;
    }

}
