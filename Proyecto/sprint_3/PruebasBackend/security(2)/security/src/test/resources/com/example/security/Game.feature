Feature: Game


  Scenario Outline:Movimiento basico de peon exitoso
    Given hay un tablero en juego
    And es turno de <jugador>
    And una <ficha> del jugador en la casilla <inicial>
    And la casilla <final> esta vacia
    When el jugador mueve su ficha a la casilla vacia
    Then la ficha del jugador se mueve a la nueva posicion en el tablero
    And el turno cambia

    Examples:
      |jugador|ficha|inicial|final|
      |blanco|peon|B2|C3|
      |blanco|peon|B2|A3|
      |negro |peon|C5|D4|
      |negro |peon|C5|B4|

  Scenario Outline: Movimiento basico de peon fallido por una casilla ocupada
    Given hay un tablero en juego
    And es turno de <jugador>
    And una <ficha> del jugador en la casilla <inicial>
    And una ficha cualquiera en la casilla <final>
    When el jugador mueve su ficha a la casilla ocupada
    Then la ficha del jugador no se mueve a la nueva posicion en el tablero
    And el turno no cambia

    Examples:
      |jugador|ficha|inicial|final|
      |blanco|peon|B2|C3|
      |blanco|peon|B2|A3|
      |negro|peon|C5|C4|
      |negro|peon|C5|B4|

  Scenario Outline:Movimiento basico de dama fallido por una casilla ocupada
    Given hay un tablero en juego
    And es turno de <jugador>
    And una <ficha> del jugador en la casilla <inicial>
    And una ficha cualquiera en la casilla <final>
    When el jugador mueve su ficha a la casilla ocupada
    Then la ficha del jugador no se mueve a la nueva posicion en el tablero
    And el turno no cambia

    Examples:
      |jugador|ficha|inicial|final|
      |blanco |dama |D4     |G7   |
      |blanco |dama |G3     |D6   |
      |negro  |dama |E3     |B6   |
      |negro  |dama |A1     |G7   |