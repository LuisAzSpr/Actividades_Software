Feature: Ver movimientos


  Scenario Outline: Ver movimientos de captura
    Given el tablero en juego
    And es el turno de <jugador>
    And el jugador tiene <ficha> en <inicio>
    And el rival tiene una ficha en <rival>
    When el jugador tiene movimientos de captura
    Then se muestran los movimientos de captura

  Examples:
    |jugador|ficha|inicio|rival|
    |blanco |peon |F4    |E5   |

  Scenario Outline: Ver movimientos simples
    Given el tablero en juego
    And es el turno de <jugador>
    And el jugador tiene <ficha> en <inicio>
    And el rival tiene una ficha en <rival>
    When el jugador no tiene movimientos de captura
    Then se muestran los movimientos simples

  Examples:
    |jugador|ficha|inicio|rival|
    |negro |peon |D4    |A7   |