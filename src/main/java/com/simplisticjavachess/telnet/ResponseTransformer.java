package com.simplisticjavachess.telnet;

public interface ResponseTransformer {
    boolean isApplicable(String str);
    Command apply(String line);
}
