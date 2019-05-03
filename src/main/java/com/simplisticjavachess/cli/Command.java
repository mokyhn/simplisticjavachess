package com.simplisticjavachess.cli;

/**
 *
 * @author Morten Kühnrich
 */
public interface Command
{
    boolean isApplicable(String str);
    void execute(String[] arguments);
    String helpCommand();
    String helpExplanation();
}
