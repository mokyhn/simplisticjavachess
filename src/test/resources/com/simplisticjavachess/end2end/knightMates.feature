Feature: Knight mating moves
  We like the knight to mate the opponent if possible

  Scenario Outline: White is to move, and can mate black with one of his knights
    Given the current position is "q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w"
    And engine is alphabeta
    And search depth is 4
    When I search
    Then I should find the move "d4-e6"

  Scenario Outline: White is to move, and can mate black with one of his knights
    Given the current position is "q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w"
    And engine is minmax
    And search depth is 4
    When I search
    Then I should find the move "d4-e6"

  Scenario Outline: Perform a suffocated mate on the opponent, through a sacrifice
    Given the current position is "r3r2k/6pp/8/6N1/2Q5/1B6/1PPP4/2K5 w"
    And engine is minmax
    And search depth is 4
    When I search
    Then I should find the move "c4-g8"

  Scenario Outline: Perform a suffocated mate on the opponent, through a sacrifice
    Given the current position is "r3r2k/6pp/8/6N1/2Q5/1B6/1PPP4/2K5 w"
    And engine is alphabeta
    And search depth is 4
    When I search
    Then I should find the move "c4-g8"

