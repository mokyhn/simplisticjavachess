Feature: Knight mating moves
  We like the queen to mate the opponent if possible

  Scenario Outline: White is to move, and can mate black with a queen move
    Given the current position is "r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1/RN2KBN1 w Q - 0 1"
    And engine is minmax
    And search depth is 4
    When I search
    Then I should find the move "f6-e7"

  Scenario Outline: White is to move, and can mate black with a queen move
    Given the current position is "r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1/RN2KBN1 w Q - 0 1"
    And engine is alphabeta
    And search depth is 4
    When I search
    Then I should find the move "f6-e7"
