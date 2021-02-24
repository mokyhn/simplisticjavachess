Feature: Knight capture moves
  We like the knight to capture as expected

  Scenario Outline: Black is to move, and he may capture a pawn with his knight
    Given the current position is "k7/4n3/8/5P2/8/8/8/K7 b"
    And engine is alphabeta
    And search depth is 4
    When I search
    Then I should find the move "e7xf5"

  Scenario Outline: Black is to move, and he may capture a pawn with his knight
    Given the current position is "k7/4n3/8/5P2/8/8/8/K7 b"
    And engine is minmax
    And search depth is 4
    When I search
    Then I should find the move "e7xf5"

  Scenario Outline: Black is to move, and he may capture a pawn with his knight
    Given the current position is "k7/4n3/8/3P4/8/8/8/K7 b - - 0 1"
    And engine is alphabeta
    And search depth is 4
    When I search
    Then I should find the move "e7xd5"

  Scenario Outline: Black is to move, and he may capture a pawn with his knight
    Given the current position is "k7/4n3/8/3P4/8/8/8/K7 b - - 0 1"
    And engine is minmax
    And search depth is 4
    When I search
    Then I should find the move "e7xd5"

  Scenario Outline: Black is to move, and he may capture a queen or a rook with his knight, he should take the queen
    Given the current position is "k7/4R3/8/3n4/8/2Q5/8/K7 b"
    And engine is alphabeta
    And search depth is 4
    When I search
    Then I should find the move "d5xc3"

  Scenario Outline: Black is to move, and he may capture a queen or a rook with his knight, he should take the queen
    Given the current position is "k7/4R3/8/3n4/8/2Q5/8/K7 b"
    And engine is minmax
    And search depth is 4
    When I search
    Then I should find the move "d5xc3"

