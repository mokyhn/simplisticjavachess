package com.simplisticjavachess.position;

public class IllegalMoveException extends Exception
{
    public IllegalMoveException()
    {
    }

    public IllegalMoveException(String s)
    {
        super(s);
    }
}
