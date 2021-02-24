package com.simplisticjavachess.end2end;


import com.simplisticjavachess.engine.AlphaBetaEngine;
import com.simplisticjavachess.engine.Engine;
import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.engine.SearchResult;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;
import com.simplisticjavachess.position.ChessMover;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.PositionIO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {
    private Engine engine;
    private int searchDepth;
    private Position position;
    private SearchResult searchResult;

    @Given("the current position is {string}")
    public void position_is(String fenString) {
        this.position = PositionIO.FEN(fenString);
        System.out.println(position.toString());
    }

    @Given("engine is alphabeta")
    public void engine_is_alphabeta() {
        this.engine = new AlphaBetaEngine();
    }

    @Given("engine is minmax")
    public void engine_is_minmax() {
        this.engine = new MinMaxEngine();
    }

    @Given("search depth is {int}")
    public void searchdepth_is(Integer searchDepth) {
        this.searchDepth = searchDepth;
    }

    @When("I search")
    public void i_search() {
        searchResult = engine.search(position, new ChessMover(), new MainMoveGenerator(), new IntegerEvaluator(), searchDepth);
    }


    @Then("I should find the move {string}")
    public void i_should_find_the_move(String moveString) {
        Move foundMove = searchResult.getMoveSequence().getFirst();
        assertEquals(moveString, foundMove.toString());
    }
}