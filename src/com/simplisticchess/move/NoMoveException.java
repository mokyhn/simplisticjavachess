package com.simplisticchess.move;

public final class NoMoveException extends Exception
{

    public String err;

    public NoMoveException()
    {
    }

    public NoMoveException(String e)
    {
        err = e;
    }

}
