package com.simplisticjavachess.telnet;

public interface ResponseHandler {
    boolean isApplicable(String str);
    void apply(String line);
}
