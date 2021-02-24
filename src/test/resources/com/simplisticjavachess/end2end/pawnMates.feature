Feature: Knight mating moves
  We like the pawns to mate the opponent if possible

  Scenario Outline: White is to move, and can mate black with a pawn move
    Given the current position is "k7/P7/KP6/8/7p/8/8/8 w"
    And engine is alphabeta
    And search depth is 4
    When I search
    Then I should find the move "b6-b7"

  Scenario Outline: White is to move, and can mate black with a pawn move
    Given the current position is "k7/P7/KP6/8/7p/8/8/8 w"
    And engine is minmax
    And search depth is 4
    When I search
    Then I should find the move "b6-b7"

